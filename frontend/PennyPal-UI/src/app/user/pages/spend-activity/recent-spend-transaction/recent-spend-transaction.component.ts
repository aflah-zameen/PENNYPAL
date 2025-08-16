import { Component, Input } from '@angular/core';
import { SpendingCategory } from '../../../models/spend-activity';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Transaction } from '../../../models/transaction.model';
import { UserCategoryResponse } from '../../../models/user-category.model';

@Component({
  selector: 'app-recent-spend-transaction',
  imports: [CommonModule,FormsModule],
  templateUrl: './recent-spend-transaction.component.html',
  styleUrl: './recent-spend-transaction.component.css'
})
export class RecentSpendTransactionComponent {
  @Input() transactions: Transaction[] = []
  @Input() categories: UserCategoryResponse[] = []

  searchQuery = ""
  selectedCategory = ""
  sortBy = "date"
  currentPage = 1
  pageSize = 10

  filteredTransactions: Transaction[] = []
  paginatedTransactions: Transaction[] = []

  ngOnInit() {
    this.applyFilters()
  }

  ngOnChanges() {    
    this.applyFilters()
  }

  trackByTransaction(index: number, transaction: Transaction): string {
    return transaction.id
  }

  applyFilters(): void {
    let filtered = [...this.transactions]

    // Apply search filter
    if (this.searchQuery) {
      const query = this.searchQuery.toLowerCase()
      filtered = filtered.filter(
        (t) =>
          t.description.toLowerCase().includes(query) ||
          t.title?.toLowerCase().includes(query) ||
          t.category.name.toLowerCase().includes(query),
      )
    }

    // Apply category filter
    if (this.selectedCategory) {
      filtered = filtered.filter((t) => t.category.id === this.selectedCategory)
    }

    // Apply sorting
    filtered.sort((a, b) => {
      switch (this.sortBy) {
        case "amount":
          return b.amount - a.amount
        case "category":
          return a.category.name.localeCompare(b.category.name)
        default:
          return new Date(b.transactionDate).getTime() - new Date(a.transactionDate).getTime()
      }
    })

    this.filteredTransactions = filtered
    this.currentPage = 1
    this.updatePagination()
  }

  updatePagination(): void {
    const startIndex = (this.currentPage - 1) * this.pageSize
    const endIndex = startIndex + this.pageSize
    this.paginatedTransactions = this.filteredTransactions.slice(startIndex, endIndex)
  }

  get totalPages(): number {
    return Math.ceil(this.filteredTransactions.length / this.pageSize)
  }

  previousPage(): void {
    if (this.currentPage > 1) {
      this.currentPage--
      this.updatePagination()
    }
  }

  nextPage(): void {
    if (this.currentPage < this.totalPages) {
      this.currentPage++
      this.updatePagination()
    }
  }

  formatCurrency(amount: number): string {
    return new Intl.NumberFormat("en-US", {
      style: "currency",
      currency: "USD",
      minimumFractionDigits: 2,
      maximumFractionDigits: 2,
    }).format(amount)
  }

  formatDate(dateString: string): string {
    return new Date(dateString).toLocaleDateString("en-US", {
      month: "short",
      day: "numeric",
      year: "numeric",
    })
  }

  formatTime(dateString: string): string {
    return new Date(dateString).toLocaleDateString("en-US", {
      weekday: "short",
    })
  }

  //get min value
  getMin(a: number, b: number): number {
  return Math.min(a, b);
  }
}
