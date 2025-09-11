import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RedemptionFilter } from '../../../models/admin-redemption-model';

@Component({
  selector: 'app-admin-redemption-filters',
  imports: [CommonModule,FormsModule],
  templateUrl: './admin-redemption-filters.component.html',
  styleUrl: './admin-redemption-filters.component.css'
})
export class AdminRedemptionFiltersComponent {
  @Output() filterChange = new EventEmitter<RedemptionFilter>()

  filter: RedemptionFilter = {
    status: "ALL",
    search: "",
    dateFrom: undefined,
    dateTo: undefined,
  }

  dateFromString = ""
  dateToString = ""

  ngOnInit() {
    this.onFilterChange()
  }

  onFilterChange() {
    this.filterChange.emit({ ...this.filter })
  }

  onDateFromChange(dateString: string) {
    this.filter.dateFrom = dateString ? new Date(dateString) : undefined
    this.onFilterChange()
  }

  onDateToChange(dateString: string) {
    this.filter.dateTo = dateString ? new Date(dateString) : undefined
    this.onFilterChange()
  }

  clearFilters() {
    this.filter = {
      status: "ALL",
      search: "",
      dateFrom: undefined,
      dateTo: undefined,
    }
    this.dateFromString = ""
    this.dateToString = ""
    this.onFilterChange()
  }
}
