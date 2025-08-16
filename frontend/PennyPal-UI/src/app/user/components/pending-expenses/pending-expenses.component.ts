import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ExpenseResponseModel, PendingExpense } from '../../models/expense.model';
import { UserExpenseService } from '../../services/user-expense.service';
import { CommonModule } from '@angular/common';
import { PendingTransaction, PendingTransactionTotalSummary } from '../../models/transaction.model';

@Component({
  selector: 'app-pending-expenses',
  imports: [CommonModule],
  templateUrl: './pending-expenses.component.html',
  styleUrl: './pending-expenses.component.css'
})
export class PendingExpensesComponent {
  @Input() pendingExpenses: PendingTransactionTotalSummary|null = null
  @Output() collectExpensePayment = new EventEmitter<PendingTransaction>()

  constructor(public expenseService: UserExpenseService) {}

  payExpense(expense: PendingTransaction) {
    this.collectExpensePayment.emit(expense)
  }
}
