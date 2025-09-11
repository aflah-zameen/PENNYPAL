import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { AnalyticsFilters } from '../../models/admin-analytics-model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SubscriptionPlan } from '../../../user/models/subscription-plan-model';

@Component({
  selector: 'app-analytics-filter',
  imports: [CommonModule,FormsModule],
  templateUrl: './analytics-filter.component.html',
  styleUrl: './analytics-filter.component.css'
})
export class AnalyticsFilterComponent implements OnInit {
  @Input() plans!: SubscriptionPlan[];
  @Output() filtersChanged = new EventEmitter<AnalyticsFilters>();
  @Output() exportRequested = new EventEmitter<{ format: 'pdf' | 'excel' }>();

  showExportMenu = false;
  startDate = new Date();
  endDate = new Date();

  allStatuses = ['COMPLETED', 'PENDING', 'FAILED'];

  filters: AnalyticsFilters = {
    subscriptionType: [],
    dateRange: {
      start: new Date(new Date().getFullYear(), 0, 1),
      end: new Date(),
    },
    paymentStatus: []
  };

  ngOnInit(): void {
    const now = new Date();
    const startOfYear = new Date(now.getFullYear(), 0, 1);

    this.startDate = startOfYear;
    this.endDate = now;

    this.filters.dateRange = { start: startOfYear, end: now };
    this.filters.subscriptionType = this.plans.map(p => p.id);
    this.filters.paymentStatus = [...this.allStatuses];

    this.filtersChanged.emit(this.filters);
  }

  toggleSubscriptionType(planId: string, event: Event): void {
  const input = event.target as HTMLInputElement;
  const checked = input.checked;

  if (checked) {
    if (!this.filters.subscriptionType.includes(planId)) {
      this.filters.subscriptionType.push(planId);
    }
  } else {
    this.filters.subscriptionType = this.filters.subscriptionType.filter(id => id !== planId);
  }

  this.onFilterChange();
}


  togglePaymentStatus(status: string, event: Event): void {
    const input = event.target as HTMLInputElement;
    const checked = input.checked;

    if (checked) {
      if (!this.filters.paymentStatus.includes(status)) {
        this.filters.paymentStatus.push(status);
      }
    } else {
      this.filters.paymentStatus = this.filters.paymentStatus.filter(s => s !== status);
    }

    this.onFilterChange();
  }

  onDateRangeChange(): void {
    if (this.startDate && this.endDate) {
      this.filters.dateRange = {
        start: new Date(this.startDate),
        end: new Date(this.endDate),
      };
      this.onFilterChange();
    }
  }

  clearFilters(): void {
    const now = new Date();
    const startOfYear = new Date(now.getFullYear(), 0, 1);

    this.startDate = startOfYear;
    this.endDate = now;

    this.filters = {
      subscriptionType: this.plans.map(p => p.id),
      paymentStatus: [...this.allStatuses],
      dateRange: {
        start: startOfYear,
        end: now
      }
    };

    this.onFilterChange();
  }

  exportData(format: 'pdf' | 'excel'): void {
    this.showExportMenu = false;
    this.exportRequested.emit({ format });
  }

  onFilterChange(): void {
    this.filtersChanged.emit(this.filters);
  }

}