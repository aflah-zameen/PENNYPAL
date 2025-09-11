import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';

export interface LoanFilters {
  status?: string
  dateRange?: string
  amountRange?: string
  searchTerm?: string
  sortBy?: string
  sortOrder?: "asc" | "desc"
}
@Component({
  selector: 'app-loan-filter',
  imports: [CommonModule,FormsModule],
  templateUrl: './loan-filter.component.html',
  styleUrl: './loan-filter.component.css'
})
export class LoanFilterComponent {
  @Output() filtersChanged = new EventEmitter<LoanFilters>()

   filters: LoanFilters = {
    status: "",
    dateRange: "",
    amountRange: "",
    searchTerm: "",
    sortBy: "date",
    sortOrder: "desc",
  }

  onFilterChange() {
    this.filtersChanged.emit(this.filters)
  }

  clearFilters() {
    this.filters = {
      status: "",
      dateRange: "",
      amountRange: "",
      searchTerm: "",
      sortBy: "date",
      sortOrder: "desc",
    }
    this.onFilterChange()
  }

  removeFilter(filterKey: keyof LoanFilters) {
    // this.filters[filterKey] = ""
    this.onFilterChange()
  }

  hasActiveFilters(): boolean {
    return !!(this.filters.status || this.filters.dateRange || this.filters.amountRange || this.filters.searchTerm)
  }

  getDateRangeLabel(value: string): string {
    const labels: { [key: string]: string } = {
      "7d": "Last 7 days",
      "30d": "Last 30 days",
      "90d": "Last 90 days",
      "1y": "Last year",
    }
    return labels[value] || value
  }

  getAmountRangeLabel(value: string): string {
    const labels: { [key: string]: string } = {
      "0-100": "$0 - $100",
      "100-500": "$100 - $500",
      "500-1000": "$500 - $1,000",
      "1000+": "$1,000+",
    }
    return labels[value] || value
  }
}
