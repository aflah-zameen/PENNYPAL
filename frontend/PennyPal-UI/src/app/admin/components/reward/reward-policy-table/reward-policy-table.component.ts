import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { PaginationConfig, RewardPolicy, RewardPolicyFilter } from '../../../models/reward.models';

@Component({
  selector: 'app-reward-policy-table',
  imports: [CommonModule,FormsModule],
  templateUrl: './reward-policy-table.component.html',
  styleUrl: './reward-policy-table.component.css'
})
export class RewardPolicyTableComponent {
  @Input() policies: RewardPolicy[] = []
  @Input() loading = false
  @Output() addPolicy = new EventEmitter<void>()
  @Output() editPolicy = new EventEmitter<RewardPolicy>()
  @Output() deletePolicy = new EventEmitter<RewardPolicy>()
  @Output() toggleStatus = new EventEmitter<RewardPolicy>()

  filter: RewardPolicyFilter = {
    search: "",
    status: "all",
  }

  pagination: PaginationConfig = {
    page: 1,
    pageSize: 10,
    total: 0,
  }

  filteredPolicies: RewardPolicy[] = []
  paginatedPolicies: RewardPolicy[] = []
  toggleLoading: string | null = null

  ngOnInit() {
    this.applyFilters()
  }

  ngOnChanges() {
    this.applyFilters()
  }

  get totalPages(): number {
    return Math.ceil(this.filteredPolicies.length / this.pagination.pageSize)
  }

  onFilterChange() {
    this.pagination.page = 1
    this.applyFilters()
  }

  onPageSizeChange() {
    this.pagination.page = 1
    this.applyFilters()
  }

  onPreviousPage() {
    if (this.pagination.page > 1) {
      this.pagination.page--
      this.applyFilters()
    }
  }

  onNextPage() {
    if (this.pagination.page < this.totalPages) {
      this.pagination.page++
      this.applyFilters()
    }
  }

  private applyFilters() {
    let filtered = [...this.policies]

    // Apply search filter
    if (this.filter.search) {
      const term = this.filter.search.toLowerCase()
      filtered = filtered.filter(
        (policy) =>
          this.getActionTypeLabel(policy.actionType).toLowerCase().includes(term) ||
          policy.description?.toLowerCase().includes(term) ||
          policy.coinAmount.toString().includes(term),
      )
    }

    // Apply status filter
    switch (this.filter.status) {
      case "active":
        filtered = filtered.filter((policy) => policy.isActive && !policy.isDeleted)
        break
      case "inactive":
        filtered = filtered.filter((policy) => !policy.isActive && !policy.isDeleted)
        break
      case "deleted":
        filtered = filtered.filter((policy) => policy.isDeleted)
        break
    }

    this.filteredPolicies = filtered
    this.pagination.total = filtered.length

    // Apply pagination
    const startIndex = (this.pagination.page - 1) * this.pagination.pageSize
    const endIndex = startIndex + this.pagination.pageSize
    this.paginatedPolicies = filtered.slice(startIndex, endIndex)
  }

  getActionTypeLabel(actionType: string): string {
    const labels: Record<string, string> = {
      GOAL_COMPLETION: "Goal Completion",
      LOAN_REPAYMENT: "Loan Repayment",
      EXPENSE_TRACKING: "Expense Tracking",
      BUDGET_ADHERENCE: "Budget Adherence",
      SAVINGS_MILESTONE: "Savings Milestone",
      INVESTMENT_DEPOSIT: "Investment Deposit",
      REFERRAL_BONUS: "Referral Bonus",
      DAILY_LOGIN: "Daily Login",
      PROFILE_COMPLETION: "Profile Completion",
      TRANSACTION_VERIFICATION: "Transaction Verification",
    }
    return labels[actionType] || actionType
  }

  onAddPolicy() {
    this.addPolicy.emit()
  }

  onEditPolicy(policy: RewardPolicy) {
    this.editPolicy.emit(policy)
  }

  onDeletePolicy(policy: RewardPolicy) {
    this.deletePolicy.emit(policy)
  }

  onToggleStatus(policy: RewardPolicy) {
    if (policy.isDeleted) return
    this.toggleLoading = policy.id
    this.toggleStatus.emit(policy)
    // Reset loading state after a delay (will be handled by parent component)
    setTimeout(() => {
      this.toggleLoading = null
    }, 1000)
  }
}
