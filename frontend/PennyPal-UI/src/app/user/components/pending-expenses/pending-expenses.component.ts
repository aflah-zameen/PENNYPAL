import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ExpenseResponseModel, PendingExpense } from '../../models/expense.model';
import { UserExpenseService } from '../../services/user-expense.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-pending-expenses',
  imports: [CommonModule],
  templateUrl: './pending-expenses.component.html',
  styleUrl: './pending-expenses.component.css'
})
export class PendingExpensesComponent {
  @Input() pendingExpenses: PendingExpense[] = []
  @Output() collectExpensePayment = new EventEmitter<PendingExpense>()

  constructor(public expenseService: UserExpenseService) {}

  payExpense(expense: PendingExpense) {
    this.collectExpensePayment.emit(expense)
  }
}
