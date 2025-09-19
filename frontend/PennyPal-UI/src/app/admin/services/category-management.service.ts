import { Injectable } from "@angular/core"
import { BehaviorSubject, catchError, map, Observable, of, tap, throwError } from "rxjs"
import type {
  AdminCategory,
  CategoryFormData,
  CategoryFilter,
  CategoryStats,
  CategoryUsageType,
} from "../models/category-management.model"
import { HttpClient, HttpErrorResponse } from "@angular/common/http"
import { ApiResponse } from "../../models/ApiResponse"
import { environment } from "../../../environments/environment"

@Injectable({
  providedIn: "root",
})
export class AdminCategoryService {

  private apiURL = environment.apiBaseUrl+"/api/private/admin";


  private categoriesSubject = new BehaviorSubject<AdminCategory[]>([])
  public categories$ = this.categoriesSubject.asObservable()

  constructor(private https : HttpClient) {
    this.loadCategories()
  }

  private loadCategories(): void {
    this.https.get<ApiResponse<AdminCategory[]>>(`${this.apiURL}/get-categories`, {withCredentials: true}).pipe(
      tap((response) => {
        this.categoriesSubject.next(response.data)
      }),
      catchError(this.handleError)
    ).subscribe();
  }

  private saveCategories(categories : AdminCategory[]): void {
    this.https.post<ApiResponse<AdminCategory[]>>(`${this.apiURL}/save-categories`, categories, {withCredentials: true}).pipe(
      tap((response) => {
        this.categoriesSubject.next(response.data)
      }),
      catchError(this.handleError)
    ).subscribe()
  }

  getCategories(): AdminCategory[] {
    return this.categoriesSubject.value
  }

  getCategoryById(id: number): AdminCategory | undefined {
    return this.categoriesSubject.value.find((cat) => cat.categoryId === id)
  }

  createCategory(formData: CategoryFormData): Observable<AdminCategory> {
    const newCategory: AdminCategory = {
      ...formData,
      default: false,
      usageCount: 0,
    }
    return this.https.post<ApiResponse<AdminCategory>>(`${this.apiURL}/add-category`, newCategory,{withCredentials: true  }).pipe(
        map(response => response.data),
        tap((category) => {
          const categories = [...this.categoriesSubject.value, category];
          this.categoriesSubject.next(categories);
        }),
        catchError(this.handleError)
    );
  }

  updateCategory(id: number, formData: CategoryFormData): Observable<AdminCategory | null> {
    const categories = this.categoriesSubject.value
    const index = categories.findIndex((cat) => cat.categoryId === id)

    if (index === -1) return of(null)

    const updatedCategory: AdminCategory = {
      ...categories[index],
      ...formData,
    }

    updatedCategory.usageTypes = formData.usageTypes;

    return this.https.put<ApiResponse<AdminCategory>>(`${this.apiURL}/update-category/${id}`, updatedCategory, {withCredentials: true}).pipe(
      tap((response) => {
        const updatedCategories = [...categories]
        updatedCategories[index] = response.data
        this.categoriesSubject.next(updatedCategories)
      }),
      map(response => response.data),
      catchError(this.handleError)
    );
  }

  deleteCategory(id: number): Observable<boolean> {
    const categories = this.categoriesSubject.value
    const category = categories.find((cat) => cat.categoryId === id)
    return this.https.delete<ApiResponse<null>>(`${this.apiURL}/delete-category/${id}`, {withCredentials: true}).pipe(
      tap((response) => {
        if (response.success) {
          const updatedCategories = categories.filter((cat) => cat.categoryId !== id)
          this.categoriesSubject.next(updatedCategories)
        }
      }),
      map(response => response.success),
      catchError(this.handleError));
  }

  toggleCategoryStatus(id: number): Observable<AdminCategory> {
    return this.https.patch<ApiResponse<AdminCategory>>(`${this.apiURL}/toggle-category-status/${id}`, {}, {withCredentials: true}).pipe(
      tap((response) => {
        const categories = this.categoriesSubject.value
        const index = categories.findIndex((cat) => cat.categoryId === id)
        if (index !== -1) {
          const updatedCategory = { ...categories[index], ...response.data }
          const updatedCategories = [...categories]
          updatedCategories[index] = updatedCategory
          this.categoriesSubject.next(updatedCategories)
        }
      }),
      map(response => response.data),
      catchError(this.handleError)
    );
  }

  reorderCategories(categoryIds: number[]): void {
    const categories = this.categoriesSubject.value
    const reorderedCategories = categoryIds
      .map((id, index) => {
        const category = categories.find((cat) => cat.categoryId === id)
        return category ? { ...category, sortOrder: index + 1, updatedAt: new Date().toISOString() } : null
      })
      .filter(Boolean) as AdminCategory[]
    this.saveCategories(reorderedCategories);
  }

  getFilteredCategories(filter: CategoryFilter): AdminCategory[] {
    
    let categories = this.categoriesSubject.value
    

    // Filter by usage type
    if (filter.usageTypes !== "all") {
      categories = categories.filter(cat => cat.usageTypes.includes(filter.usageTypes.toUpperCase() as CategoryUsageType))  
    }

    // Filter by status
    if (filter.status !== "all") {
      categories = categories.filter((cat) => cat.active === (filter.status === "active"))
    }

    // Filter by search
    if (filter.search) {
      const search = filter.search.toLowerCase()
      categories = categories.filter(
        (cat) =>
          cat.name.toLowerCase().includes(search) ||
          cat.description?.toLowerCase().includes(search) ||
          cat.usageTypes.some((type) => type.toLowerCase().includes(search)),
      )
    }

    return categories.sort((a, b) => a.sortOrder - b.sortOrder)
  }

  getCategoryStats(): CategoryStats {
    const categories = this.categoriesSubject.value

    return {
      total: categories.length,
      active: categories.filter((cat) => cat.active).length,
      inactive: categories.filter((cat) => !cat.active).length,
      byUsageType: {
        GOAL: categories.filter((cat) => cat.usageTypes.includes("GOAL")).length,
        INCOME: categories.filter((cat) => cat.usageTypes.includes("INCOME")).length,
        EXPENSE: categories.filter((cat) => cat.usageTypes.includes("EXPENSE")).length,
        SHARED: categories.filter((cat) => cat.usageTypes.includes("SHARED")).length,
      },
    }
  }

  bulkUpdateStatus(categoryIds: number[], active: boolean): void {
    const categories = this.categoriesSubject.value.map((cat) => {
      if (categoryIds.includes(cat.categoryId!) && !cat.default) {
        return { ...cat, active, updatedAt: new Date().toISOString() }
      }
      return cat
    })
    this.saveCategories(categories);
  }

  exportCategories(): string {
    return JSON.stringify(this.categoriesSubject.value, null, 2)
  }

  

  //handle error
  private handleError(error: HttpErrorResponse): Observable<never> {
    let errorMessage = 'An unknown error occurred!';
    if (error.error instanceof ErrorEvent) {
      errorMessage = `Error: ${error.error.message}`;
    } else if (error.error && error.error.message) {
      errorMessage = error.error.message;
    } else {
      errorMessage = `Error code: ${error.status}\nMessage: ${error.message}`;
    }
    return throwError(() => new Error(errorMessage));
  }
}
