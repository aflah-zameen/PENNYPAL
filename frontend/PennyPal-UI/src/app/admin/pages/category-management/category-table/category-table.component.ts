import { Component, EventEmitter, Input, Output } from '@angular/core';
import { AdminCategory } from '../../../models/category-management.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-category-table',
  imports: [CommonModule],
  templateUrl: './category-table.component.html',
  styleUrl: './category-table.component.css'
})
export class CategoryTableComponent {
  @Input() categories: AdminCategory[] = []
  @Output() edit = new EventEmitter<AdminCategory>()
  @Output() delete = new EventEmitter<AdminCategory>()
  @Output() toggleStatus = new EventEmitter<AdminCategory>()
  @Output() bulkAction = new EventEmitter<{ action: string; categoryIds: number[] }>()

  selectedCategories: number[] = []

  trackByCategory(index: number, category: AdminCategory): number {
    return category.id!
  }

  toggleSelect(categoryId: number): void {
    const index = this.selectedCategories.indexOf(categoryId)
    if (index > -1) {
      this.selectedCategories.splice(index, 1)
    } else {
      this.selectedCategories.push(categoryId)
    }
  }

  toggleSelectAll(): void {
    const selectableCategories = this.categories.filter((cat) => !cat.default)
    if (this.isAllSelected()) {
      this.selectedCategories = []
    } else {
      this.selectedCategories = selectableCategories.map((cat) => cat.id).filter((id) => typeof id === 'number')
    }
  }

  isAllSelected(): boolean {
    const selectableCategories = this.categories.filter((cat) => !cat.default)
    return selectableCategories.length > 0 && this.selectedCategories.length === selectableCategories.length
  }

  isIndeterminate(): boolean {
    return this.selectedCategories.length > 0 && !this.isAllSelected()
  }

  onEdit(category: AdminCategory): void {
    this.edit.emit(category)
  }

  onDelete(category: AdminCategory): void {
    this.delete.emit(category)
  }

  onToggleStatus(category: AdminCategory): void {
    this.toggleStatus.emit(category)
  }

  onBulkAction(action: string): void {
    if (this.selectedCategories.length > 0) {
      this.bulkAction.emit({ action, categoryIds: [...this.selectedCategories] })
      this.selectedCategories = []
    }
  }

  getUsageTypeClass(type: string): string {
    const classes = {
      goal: "bg-blue-100 text-blue-800",
      income: "bg-green-100 text-green-800",
      expense: "bg-red-100 text-red-800",
      shared: "bg-purple-100 text-purple-800",
    }
    return classes[type as keyof typeof classes] || "bg-gray-100 text-gray-800"
  }

  formatDate(dateString: string): string {
    return new Date(dateString).toLocaleDateString("en-US", {
      month: "short",
      day: "numeric",
      year: "numeric",
    })
  }
}
