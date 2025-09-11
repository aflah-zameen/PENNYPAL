import { Component, EventEmitter, Input, Output } from '@angular/core';
import { RewardPolicy } from '../../../models/reward.models';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-reward-policy-delete-modal',
  imports: [CommonModule],
  templateUrl: './reward-policy-delete-modal.component.html',
  styleUrl: './reward-policy-delete-modal.component.css'
})
export class RewardPolicyDeleteModalComponent {
  @Input() isOpen = false
  @Input() policy: RewardPolicy | null = null
  @Input() loading = false
  @Output() confirm = new EventEmitter<void>()
  @Output() cancel = new EventEmitter<void>()

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

  onConfirm() {
    this.confirm.emit()
  }

  onCancel() {
    this.cancel.emit()
  }
}
