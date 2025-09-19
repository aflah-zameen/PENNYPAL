import { Component, EventEmitter, Input, Output } from '@angular/core';
import { AdminSubscriptionPlan, SortConfig, TableColumn } from '../../../models/admin-subscription-model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-admin-plan-table',
  imports: [CommonModule,FormsModule],
  templateUrl: './admin-plan-table.component.html',
  styleUrl: './admin-plan-table.component.css'
})
export class AdminPlanTableComponent {
  @Input() plans: AdminSubscriptionPlan[] = []
  @Input() loading = false
  @Output() addPlan = new EventEmitter<void>()
  @Output() editPlan = new EventEmitter<AdminSubscriptionPlan>()
  @Output() deletePlan = new EventEmitter<AdminSubscriptionPlan>()
  @Output() viewSubscribers = new EventEmitter<AdminSubscriptionPlan>()

  searchTerm = ""
  filteredPlans: AdminSubscriptionPlan[] = []
  sortConfig: SortConfig | null = null

  columns: TableColumn[] = [
    { key: "name", label: "Plan Name", sortable: true, type: "text" },
    { key: "amount", label: "Amount", sortable: true, type: "currency" },
    { key: "durationDays", label: "Duration", sortable: true, type: "text" },
    { key: "features", label: "Features", sortable: false, type: "text" },
    // { key: "isActive", label: "Status", sortable: true, type: "badge" },
    // { key: "subscriberCount", label: "Subscribers", sortable: true, type: "number" },
    { key: "createdDate", label: "Created", sortable: true, type: "date" },
    { key: "actions", label: "Actions", sortable: false, type: "actions" },
  ]

  ngOnInit() {
    this.filteredPlans = [...this.plans]
  }

  ngOnChanges() {
    this.applyFilters()
  }

  onSearch() {
    this.applyFilters()
  }

  onSort(key: string) {
    if (this.sortConfig?.key === key) {
      this.sortConfig.direction = this.sortConfig.direction === "asc" ? "desc" : "asc"
    } else {
      this.sortConfig = { key, direction: "asc" }
    }
    this.applyFilters()
  }

  private applyFilters() {
    let filtered = [...this.plans]

    // Apply search filter
    if (this.searchTerm) {
      const term = this.searchTerm.toLowerCase()
      filtered = filtered.filter(
        (plan) =>
          plan.name.toLowerCase().includes(term) ||
          plan.description?.toLowerCase().includes(term) ||
          plan.features.some((feature) => feature.toLowerCase().includes(term)),
      )
    }

    // Apply sorting
    if (this.sortConfig) {
      filtered.sort((a, b) => {
        const aValue = this.getValueForSort(a, this.sortConfig!.key)
        const bValue = this.getValueForSort(b, this.sortConfig!.key)

        if (aValue < bValue) return this.sortConfig!.direction === "asc" ? -1 : 1
        if (aValue > bValue) return this.sortConfig!.direction === "asc" ? 1 : -1
        return 0
      })
    }

    this.filteredPlans = filtered
  }

  private getValueForSort(plan: AdminSubscriptionPlan, key: string): string | number{
    switch (key) {
      case "name":
        return plan.name.toLowerCase()
      case "amount":
        return plan.amount
      case "durationDays":
        return plan.durationDays
      case "isActive":
        return plan.isActive ? 1 : 0
      case "subscriberCount":
        return plan.subscriberCount
      case "createdDate":
        return plan.createdDate.getTime()
      default:
        return ""
    }
  }

  getDurationText(days: number): string {
    if (days === 30) return "1 Month"
    if (days === 90) return "3 Months"
    if (days === 180) return "6 Months"
    if (days === 365) return "1 Year"
    if (days < 30) return `${days} Days`
    const months = Math.round(days / 30)
    return `${months} Months`
  }

  onAddPlan() {
    this.addPlan.emit()
  }

  onEditPlan(plan: AdminSubscriptionPlan) {
    this.editPlan.emit(plan)
  }

  onDeletePlan(plan: AdminSubscriptionPlan) {
    this.deletePlan.emit(plan)
  }

  onViewSubscribers(plan: AdminSubscriptionPlan) {
    this.viewSubscribers.emit(plan)
  }
}
