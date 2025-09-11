import { Component, Input } from '@angular/core';
import { CoinTransaction, CoinTransactionType, TransactionStatus } from '../../../models/reward-coin-model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-coin-transaction-history',
  imports: [CommonModule,FormsModule],
  templateUrl: './coin-transaction-history.component.html',
  styleUrl: './coin-transaction-history.component.css'
})
export class CoinTransactionHistoryComponent {
  @Input() transactions: CoinTransaction[] = []

  trackByTransactionId(index: number, transaction: CoinTransaction): string {
    return transaction.id
  }

  isEarnedTransaction(type: CoinTransactionType): boolean {
    return ["GOAL_COMPLETION", "LOAN_REPAYMENT", "EARNED", "BONUS"].includes(type)
  }

  getTransactionIconClass(type: CoinTransactionType): string {
    switch (type) {
      case "GOAL_COMPLETION":
        return "bg-gradient-to-r from-green-400 to-emerald-500 text-white"
      case "LOAN_REPAYMENT":
        return "bg-gradient-to-r from-blue-400 to-blue-500 text-white"
      case "EARNED":
      case "BONUS":
        return "bg-gradient-to-r from-yellow-400 to-orange-500 text-white"
      case "REDEMPTION":
        return "bg-gradient-to-r from-red-400 to-pink-500 text-white"
      case "PENALTY":
        return "bg-gradient-to-r from-orange-400 to-red-500 text-white"
      default:
        return "bg-gradient-to-r from-gray-400 to-gray-500 text-white"
    }
  }

  getStatusBadgeClass(status: TransactionStatus): string {
    switch (status) {
      case "COMPLETED":
        return "bg-gradient-to-r from-green-100 to-emerald-100 text-green-800 border border-green-200"
      case "PENDING":
        return "bg-gradient-to-r from-yellow-100 to-orange-100 text-yellow-800 border border-yellow-200"
      case "FAILED":
        return "bg-gradient-to-r from-red-100 to-pink-100 text-red-800 border border-red-200"
      case "CANCELLED":
        return "bg-gradient-to-r from-gray-100 to-gray-200 text-gray-800 border border-gray-200"
      default:
        return "bg-gradient-to-r from-gray-100 to-gray-200 text-gray-800 border border-gray-200"
    }
  }

  getTypeClass(type: CoinTransactionType): string {
    switch (type) {
      case "GOAL_COMPLETION":
        return "bg-gradient-to-r from-green-50 to-emerald-50 text-green-700 border border-green-200"
      case "LOAN_REPAYMENT":
        return "bg-gradient-to-r from-blue-50 to-blue-100 text-blue-700 border border-blue-200"
      case "EARNED":
        return "bg-gradient-to-r from-green-50 to-emerald-50 text-green-700 border border-green-200"
      case "REDEMPTION":
        return "bg-gradient-to-r from-red-50 to-pink-50 text-red-700 border border-red-200"
      case "BONUS":
        return "bg-gradient-to-r from-purple-50 to-indigo-50 text-purple-700 border border-purple-200"
      case "PENALTY":
        return "bg-gradient-to-r from-orange-50 to-red-50 text-orange-700 border border-orange-200"
      default:
        return "bg-gradient-to-r from-gray-50 to-gray-100 text-gray-700 border border-gray-200"
    }
  }

  getAmountClass(amount: number): string {
    return amount > 0
      ? "text-green-600 bg-gradient-to-r from-green-600 to-emerald-600 bg-clip-text text-transparent"
      : "text-red-600 bg-gradient-to-r from-red-600 to-pink-600 bg-clip-text text-transparent"
  }

  getStatusLabel(status: TransactionStatus): string {
    switch (status) {
      case "COMPLETED":
        return "Completed"
      case "PENDING":
        return "Pending"
      case "FAILED":
        return "Failed"
      case "CANCELLED":
        return "Cancelled"
      default:
        return status
    }
  }

  getTypeLabel(type: CoinTransactionType): string {
    switch (type) {
      case "GOAL_COMPLETION":
        return "Goal Reward"
      case "LOAN_REPAYMENT":
        return "Loan Bonus"
      case "EARNED":
        return "Earned"
      case "REDEMPTION":
        return "Redeemed"
      case "BONUS":
        return "Bonus"
      case "PENALTY":
        return "Penalty"
      case "REFUND":
        return "Refund"
      case "EXPIRED":
        return "Expired"
      default:
        return type
    }
  }
}
