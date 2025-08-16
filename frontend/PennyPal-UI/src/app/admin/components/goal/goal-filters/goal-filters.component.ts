import { Component, Input } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { GoalStatus } from '../../../models/goal-management.model';
import { CommonModule } from '@angular/common';
import { GoalDashboardService } from '../../../services/goal-management.service';
import { UserCategoryResponse } from '../../../../user/models/user-category.model';
import { AdminCategory } from '../../../models/category-management.model';

@Component({
  selector: 'app-goal-filters',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './goal-filters.component.html',
  styleUrl: './goal-filters.component.css'
})
export class GoalFiltersComponent {

  @Input() categories: AdminCategory[] = [];

  filterForm: FormGroup
  goalStatuses: GoalStatus[] = ["ACTIVE", "COMPLETED", "EXPIRED", "WITHDRAW_PENDING", "WITHDRAW_COLLECTED", "CANCELLED"]

  constructor(
    private fb: FormBuilder,
    private goalDashboardService: GoalDashboardService,
  ) {
    this.filterForm = this.fb.group({
      keyword: [""],
      status: [""],
      category: [""],
      dateFrom: [""],
      dateTo: [""],
      minAmount: [""],
      maxAmount: [""],
    })
  }

  ngOnInit() {
    this.filterForm.valueChanges.subscribe((values) => {
      this.goalDashboardService.updateFilters({
        keyword: values.keyword || "",
        status: values.status ? [values.status] : [],
        category: values.category || "",
        dateRange: {
          from: values.dateFrom || null,
          to: values.dateTo || null,
        },
        minAmount: values.minAmount ? Number(values.minAmount) : null,
        maxAmount: values.maxAmount ? Number(values.maxAmount) : null,
      })
    })
  }

  clearFilters() {
    this.filterForm.reset()
  }

  applyQuickFilter(event: Event) {
    const target = event.target as HTMLSelectElement
    const filterType = target.value

    switch (filterType) {
      case "active":
        this.filterForm.patchValue({ status: "Active" })
        break
      case "completed":
        this.filterForm.patchValue({ status: "Completed" })
        break
      case "pending_withdrawals":
        // This would need special handling in the service
        break
      case "high_progress":
        // This would need special handling in the service
        break
      case "low_progress":
        // This would need special handling in the service
        break
    }

    // Reset the select
    target.value = ""
  }
}
