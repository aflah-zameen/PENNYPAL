import { Component } from '@angular/core';
import { Subscription } from 'rxjs';
import { AdminCategory, CategoryFilter, CategoryFormData, CategoryStats } from '../../models/category-management.model';
import { AdminCategoryService } from '../../services/category-management.service';
import { CategoryStatsComponent } from "./category-stats/category-stats.component";
import { CategoryFilterComponent } from "./category-filter/category-filter.component";
import { CategoryTableComponent } from "./category-table/category-table.component";
import { CategoryFormComponent } from "./category-form/category-form.component";
import { CommonModule } from '@angular/common';
import { PageHeaderComponent } from "../../components/page-header/page-header.component";
import { SpendCategoryChartComponent } from "../../../user/pages/spend-activity/spend-category-chart/spend-category-chart.component";
import { ToastrService } from 'ngx-toastr';
import { NgxSpinnerService } from 'ngx-spinner';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmDialogComponent } from '../../../shared/components/confirm-dialog/confirm-dialog.component';

@Component({
  selector: 'app-category-management',
  imports: [PageHeaderComponent, CategoryFilterComponent, CategoryTableComponent, CategoryFormComponent, CategoryStatsComponent,CommonModule],
  templateUrl: './category-management.component.html',
  styleUrl: './category-management.component.css'
})
export class CategoryManagementComponent {
   private subscription = new Subscription()

  categories: AdminCategory[] = []
  filteredCategories: AdminCategory[] = []
  stats: CategoryStats = {
    total: 0,
    active: 0,
    inactive: 0,
    byUsageType: { GOAL: 0, INCOME: 0, EXPENSE: 0, SHARED: 0 },
  }

  breadcrumbItems = [{ label: "System Management", route: "/admin/settings" }, { label: "Category Management" }]

  currentFilter: CategoryFilter = {
    usageTypes: "all",
    status: "all",
    search: "",
  }

  isFormOpen = false
  selectedCategory: AdminCategory | null = null

  errorMessage = ""

  constructor(private categoryService: AdminCategoryService,private toastr: ToastrService,
    private spinner : NgxSpinnerService,private dialog : MatDialog
  ) {}

  ngOnInit() {
    this.subscription.add(
      this.categoryService.categories$.subscribe((categories) => {
        this.categories = categories
        this.updateFilteredCategories(this.currentFilter)
        this.updateStats()
      }),
    )
  }

  ngOnDestroy() {
    this.subscription.unsubscribe()
  }

  onFilterChange(filter: CategoryFilter): void {    
    this.currentFilter = filter
    this.updateFilteredCategories(this.currentFilter)
  }

  private updateFilteredCategories(filter : CategoryFilter): void {
    this.filteredCategories = this.categoryService.getFilteredCategories(filter)
  }

  private updateStats(): void {
    this.stats = this.categoryService.getCategoryStats()
  }

  openCreateForm(): void {
    this.selectedCategory = null
    this.isFormOpen = true
  }

  openEditForm(category: AdminCategory): void {
    this.selectedCategory = category
    this.isFormOpen = true
  }

  closeForm(): void {
    this.isFormOpen = false
    this.selectedCategory = null
  }

  saveCategory(formData: CategoryFormData): void {
    try {
      if (this.selectedCategory) {
        // Edit existing category
        this.categoryService.updateCategory(this.selectedCategory.id!, formData).subscribe({
          next: (updatedCategory) => {
            this.toastr.success("Category updated successfully");
            this.selectedCategory = updatedCategory
          },
          error: (err) => {
            this.toastr.error(err.message || "Failed to update category")
          }
        })
      } else {
        this.spinner.show();
        // Create new category
        this.categoryService.createCategory(formData).subscribe({
          next: (newCategory) => {
            this.spinner.hide();
            this.toastr.success("Category created successfully")
            this.selectedCategory = newCategory
          },
          error: (err) => {
            this.spinner.hide();
            this.toastr.error(err.message || "Failed to create category")
          }
        });

      }
      this.closeForm()
    } catch (error) {
      this.toastr.error("An error occurred while saving the category")
    }
  }

  deleteCategory(category: AdminCategory): void {
    if (category.default) {
      this.toastr.error("Cannot delete default categories")
      return
    }
    this.dialog.open(ConfirmDialogComponent,{
      width: '400px',
        data: {
          title: 'Confirm Delete',
          message: `Are you sure you want to delete the category "${category.name}"?`,
          confirmText: `Delete Category`,
          cancelText: 'Cancel'
        }
    }).afterClosed().subscribe({
      next: (result) => {
        if( result) {
          this.spinner.show();
          this.categoryService.deleteCategory(category.id!).subscribe({
            next: () => {
              this.spinner.hide();
              this.toastr.success(`Category "${category.name}" deleted successfully`)
              this.selectedCategory = null
            },
            error: (err) => {
              this.spinner.hide();
              this.toastr.error(err.message || "Failed to delete category")
            }
          });
        }
      }
    })
  }

  toggleCategoryStatus(category: AdminCategory): void {
    this.spinner.show();
    this.categoryService.toggleCategoryStatus(category.id!).subscribe({
      next: (updatedCategory) => {
        this.spinner.hide();
        this.toastr.success(`Category "${updatedCategory.name}" status updated successfully`)
        this.selectedCategory = updatedCategory
      },
      error: (err) => {
        this.spinner.hide();
        this.toastr.error(err.message || "Failed to update category status")
      }
    });
    
  }

  handleBulkAction(event: { action: string; categoryIds: number[] }): void {
    const { action, categoryIds } = event

    if (categoryIds.length === 0) return

    const actionText = action === "activate" ? "activate" : "deactivate"
    const confirmMessage = `Are you sure you want to ${actionText} ${categoryIds.length} categories?`

    if (confirm(confirmMessage)) {
      this.categoryService.bulkUpdateStatus(categoryIds, action === "activate")
      this.toastr.success(`${categoryIds.length} categories ${actionText}d successfully`)
    }
  }

  // exportCategories(): void {
  //   try {
  //     const data = this.categoryService.exportCategories()
  //     const blob = new Blob([data], { type: "application/json" })
  //     const url = window.URL.createObjectURL(blob)
  //     const link = document.createElement("a")
  //     link.href = url
  //     link.download = `categories-export-${new Date().toISOString().split("T")[0]}.json`
  //     link.click()
  //     window.URL.revokeObjectURL(url)
  //     this.toastr.success("Categories exported successfully")
  //   } catch (error) {
  //     this.toastr.error("Failed to export categories")
  //   }
  // }

  // onFileSelected(event: Event): void {
  //   const target = event.target as HTMLInputElement
  //   const file = target.files?.[0]

  //   if (!file) return

  //   const reader = new FileReader()
  //   reader.onload = (e) => {
  //     try {
  //       const content = e.target?.result as string
  //       const success = this.categoryService.importCategories(content)

  //       if (success) {
  //         this.toastr.success("Categories imported successfully")
  //       } else {
  //         this.toastr.error("Invalid file format or data")
  //       }
  //     } catch (error) {
  //       this.toastr.error("Failed to import categories")
  //     }
  //   }
  //   reader.readAsText(file)

  //   // Reset file input
  //   target.value = ""
  // }


}
