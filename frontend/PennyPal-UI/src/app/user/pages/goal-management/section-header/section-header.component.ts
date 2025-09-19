import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
interface FilterChange {
  category: string
  progress: string
  sort: string
}
@Component({
  selector: 'app-section-header',
  imports: [CommonModule,FormsModule],
  templateUrl: './section-header.component.html',
  styleUrl: './section-header.component.css'
})
export class SectionHeaderComponent {
  @Input() title = ""
  @Input() icon = true
  @Input() showSeeAll = false
  @Input() showSearch = false
  @Input() filterCategories : string[]=[]

  @Output() seeAll = new EventEmitter<void>()
  @Output() search = new EventEmitter<string>()
  @Output() filterChange = new EventEmitter<FilterChange>()
  @Output() viewModeChange = new EventEmitter<string>()

  searchQuery = ""
  showFilterDropdown = false
  selectedCategory = ""
  selectedProgress = ""
  selectedSort = "recent"
  viewMode = "grid"

  get hasActiveFilters(): boolean {
    return !!(this.selectedCategory || this.selectedProgress || this.selectedSort !== "title")
  }

  onSeeAll(): void {
    this.seeAll.emit()
  }

  onSearchChange(query: string): void {
    this.searchQuery = query
    this.search.emit(query)
  }

  clearSearch(): void {
    this.searchQuery = ""
    this.search.emit("")
  }

  toggleFilterDropdown(): void {
    this.showFilterDropdown = !this.showFilterDropdown
  }

  onCategoryChange(category: string): void {
    this.selectedCategory = category
    this.emitFilterChange()
  }

  onProgressChange(progress: string): void {
    this.selectedProgress = progress
    this.emitFilterChange()
  }

  onSortChange(sort: string): void {
    this.selectedSort = sort
    this.emitFilterChange()
  }

  clearFilters(): void {
    this.selectedCategory = ""
    this.selectedProgress = ""
    this.selectedSort = "recent"
    this.showFilterDropdown = false
    this.emitFilterChange()
  }

  applyFilters(): void {
    this.showFilterDropdown = false
    this.emitFilterChange()
  }

  setViewMode(mode: string): void {
    this.viewMode = mode
    this.viewModeChange.emit(mode)
  }

  private emitFilterChange(): void {
    this.filterChange.emit({
      category: this.selectedCategory,
      progress: this.selectedProgress,
      sort: this.selectedSort,
    })
  }

  getActiveFiltersText(): string {
    const filters = []
    if (this.selectedCategory) filters.push(`Category: ${this.selectedCategory}`)
    if (this.selectedProgress) filters.push(`Progress: ${this.selectedProgress}`)
    if (this.selectedSort !== "title") filters.push(`Sort: ${this.selectedSort}`)
    return filters.join(", ")
  }
}
