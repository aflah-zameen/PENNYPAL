import { Component, EventEmitter, Input, Output } from '@angular/core';
import { PaginationInfo } from '../../../models/transaction-management.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-admin-redemption-pagination',
  imports: [CommonModule],
  templateUrl: './admin-redemption-pagination.component.html',
  styleUrl: './admin-redemption-pagination.component.css'
})
export class AdminRedemptionPaginationComponent {
  @Input() pagination: PaginationInfo | null = null
  @Output() pageChange = new EventEmitter<number>()

  onPageChange(page: number) {
    if (this.pagination && page >= 1 && page <= this.pagination.totalPages) {
      this.pageChange.emit(page)
    }
  }

  getStartItem(): number {
    if (!this.pagination) return 0
    return (this.pagination.currentPage - 1) * this.pagination.itemsPerPage + 1
  }

  getEndItem(): number {
    if (!this.pagination) return 0
    return Math.min(this.pagination.currentPage * this.pagination.itemsPerPage, this.pagination.totalItems)
  }

  getVisiblePages(): number[] {
    if (!this.pagination) return []

    const totalPages = this.pagination.totalPages
    const currentPage = this.pagination.currentPage
    const pages: number[] = []

    if (totalPages <= 7) {
      for (let i = 1; i <= totalPages; i++) {
        pages.push(i)
      }
    } else {
      if (currentPage <= 4) {
        for (let i = 1; i <= 5; i++) {
          pages.push(i)
        }
        pages.push(-1) // Ellipsis
        pages.push(totalPages)
      } else if (currentPage >= totalPages - 3) {
        pages.push(1)
        pages.push(-1) // Ellipsis
        for (let i = totalPages - 4; i <= totalPages; i++) {
          pages.push(i)
        }
      } else {
        pages.push(1)
        pages.push(-1) // Ellipsis
        for (let i = currentPage - 1; i <= currentPage + 1; i++) {
          pages.push(i)
        }
        pages.push(-1) // Ellipsis
        pages.push(totalPages)
      }
    }

    return pages
  }

  getPageButtonClass(page: number): string {
    if (page === -1) {
      return "relative inline-flex items-center px-4 py-2 border border-gray-300 bg-white text-sm font-medium text-gray-700 cursor-default"
    }

    const isActive = this.pagination?.currentPage === page
    const baseClass = "relative inline-flex items-center px-4 py-2 border text-sm font-medium"

    if (isActive) {
      return `${baseClass} border-blue-500 bg-blue-50 text-blue-600`
    } else {
      return `${baseClass} border-gray-300 bg-white text-gray-500 hover:bg-gray-50`
    }
  }
}
