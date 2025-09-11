import { Injectable } from "@angular/core"
import { BehaviorSubject, type Observable, of } from "rxjs"
import { catchError, delay, finalize, map, switchMap, tap } from "rxjs/operators"
import type { LoanCase, AdminAction } from "../models/admin-lending-model"
import { environment } from "../../../environments/environment"
import { HttpClient, HttpParams } from "@angular/common/http"
import { ApiResponse } from "../../models/ApiResponse"
interface CaseFilters {
  keyword: string
  status: string
}

@Injectable({
  providedIn: "root",
})
export class CaseService {
  
  private apiUrl = environment.apiBaseUrl + '/api/private/admin/lending'

  // --- Reactive State ---
  private casesSubject = new BehaviorSubject<LoanCase[]>([])
  private filtersSubject = new BehaviorSubject<CaseFilters>({
    keyword: '',
    status: '',
  })
  private isLoadingSubject = new BehaviorSubject<boolean>(false)

  // --- Public Observables ---
  public readonly cases$ = this.casesSubject.asObservable()
  public readonly filters$ = this.filtersSubject.asObservable()
  public readonly isLoading$ = this.isLoadingSubject.asObservable()

  constructor(private http: HttpClient) {
    this.listenForFilterChanges().subscribe()
  }

  // --- Reactive Fetch ---
  private listenForFilterChanges(): Observable<LoanCase[]> {
    return this.filters$.pipe(
      switchMap((filters) => {
        this.isLoadingSubject.next(true)
        const params = this.buildHttpParams(filters)

        return this.http.get<ApiResponse<LoanCase[]>>(`${this.apiUrl}/cases`, {
          withCredentials: true,
          params,
        }).pipe(
          map((res) => res.data),
          tap((cases) => this.casesSubject.next(cases)),
          finalize(() => this.isLoadingSubject.next(false)),
          catchError((err) => {
            console.error('Failed to fetch cases:', err)
            this.casesSubject.next([])
            return of([])
          })
        )
      })
    )
  }

  // --- Action Methods ---
  cancelCase(caseId: string): Observable<boolean> {
  return this.http.post<void>(
    `${this.apiUrl}/${caseId}/cancel`,
    {},
    { withCredentials: true }
  ).pipe(
    map(() => {
      // Get current cases
      const currentCases = this.casesSubject.getValue();

      // Update the matching case to RESOLVED
      const updatedCases = currentCases.map(c =>
        c.id === caseId ? { ...c, status: 'RESOLVED' as 'RESOLVED' } : c
      );

      // Emit updated list
      this.casesSubject.next(updatedCases);

      return true;
    }),
    catchError(() => of(false))
  );
}


  addAdminNote(caseId: string, note: string): Observable<boolean> {
    return this.http.post<void>(`${this.apiUrl}/${caseId}/notes`, { note }, { withCredentials: true }).pipe(
      tap(() => this.refreshData()),
      map(() => true),
      catchError(() => of(false))
    )
  }

  sendReminder(loanId: string) :Observable<any>{
    return this.http.post<void>(`${this.apiUrl}/${loanId}/remind}`,{},{withCredentials:true});
  }

 suspendUser(userId: string, caseId: string): Observable<boolean> {
  return this.http.post<void>(
    `${this.apiUrl}/users/${userId}/suspend`,
    {},
    { withCredentials: true, params: { caseId } }
  ).pipe(
    map(() => {
      // Get current cases
      const currentCases = this.casesSubject.getValue();

      // Update the matching case
      const updatedCases = currentCases.map(c =>
        c.id === caseId ? { ...c, status: 'RESOLVED' as 'RESOLVED' } : c
      );

      // Emit updated list
      this.casesSubject.next(updatedCases);

      return true;
    }),
    catchError(() => of(false))
  );
}


  blockUser(userId: string, reason: string): Observable<boolean> {
    return this.http.post<void>(`${this.apiUrl}/users/${userId}/block`, {
      reason,
    }, { withCredentials: true }).pipe(
      map(() => true),
      catchError(() => of(false))
    )
  }

  // --- Filter Management ---
  updateFilters(filters: Partial<CaseFilters>): void {
    const current = this.filtersSubject.value
    this.filtersSubject.next({ ...current, ...filters })
  }

  refreshData(): void {
    this.filtersSubject.next(this.filtersSubject.value)
  }

  // --- Helpers ---
  private buildHttpParams(filters: CaseFilters): HttpParams {
    let params = new HttpParams()
    if (filters.keyword) params = params.set('keyword', filters.keyword)
    if (filters.status) params = params.set('status', filters.status)
    return params
  }

  formatCurrency(amount: number): string {
    return new Intl.NumberFormat('en-US', {
      style: 'currency',
      currency: 'USD',
    }).format(amount)
  }

  formatDateTime(dateInput: string | Date): string {
  const date = typeof dateInput === 'string' ? new Date(dateInput) : dateInput;

  return new Intl.DateTimeFormat('en-US', {
    year: 'numeric',
    month: 'short',
    day: 'numeric',
    hour: '2-digit',
    minute: '2-digit',
  }).format(date);
}

  
}
