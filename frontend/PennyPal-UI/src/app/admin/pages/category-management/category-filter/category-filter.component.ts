import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CategoryFilter } from '../../../models/category-management.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-category-filter',
  imports: [CommonModule,FormsModule],
  templateUrl: './category-filter.component.html',
  styleUrl: './category-filter.component.css'
})
export class CategoryFilterComponent {
  @Input() filter: CategoryFilter = {
    usageTypes: "all",
    status: "all",
    search: "",
  }
  @Output() filterChange = new EventEmitter<CategoryFilter>()

  onFilterChange(): void {
    this.filterChange.emit({ ...this.filter })
  }

  clearFilters(): void {
    this.filter = {
      usageTypes: "all",
      status: "all",
      search: "",
    }
    this.onFilterChange()
  }

  clearSearch(): void {
    this.filter.search = ""
    this.onFilterChange()
  }

  clearUsageType(): void {
    this.filter.usageTypes = "all"
    this.onFilterChange()
  }

  clearStatus(): void {
    this.filter.status = "all"
    this.onFilterChange()
  }

  hasActiveFilters(): boolean {
    return this.filter.search !== "" || this.filter.usageTypes !== "all" || this.filter.status !== "all"
  }
}
