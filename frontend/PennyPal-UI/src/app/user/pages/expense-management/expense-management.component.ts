import { Component } from '@angular/core';
import { UserCategoryResponse } from '../../models/user-category.model';
import { AddExpenseForm, ExpenseResponseModel, ExpenseSummary, PendingExpense, RecurringExpensesResponse } from '../../models/expense.model';
import { catchError, Observable, of } from 'rxjs';
import { Transaction } from '../../models/transaction.model';
import { UserExpenseService } from '../../services/user-expense.service';
import { UserService } from '../../services/user.service';
import { PendingExpensesComponent } from "../../components/pending-expenses/pending-expenses.component";
import { CommonModule } from '@angular/common';
import { AddExpenseModalComponent } from "../../components/add-expense-modal/add-expense-modal.component";
import { AddRecurringExpenseModalComponent } from "../../components/add-recurring-expense-modal/add-recurring-expense-modal.component";

@Component({
  selector: 'app-expense-management',
  imports: [PendingExpensesComponent, CommonModule, AddExpenseModalComponent, AddRecurringExpenseModalComponent],
  templateUrl: './expense-management.component.html',
  styleUrl: './expense-management.component.css'
})
export class ExpenseManagementComponent {
  isExpenseModalOpen = false
  isRecurringModalOpen = false
  categories$: Observable<UserCategoryResponse[]> = of([])

  expenseSummary$!: Observable<ExpenseSummary>
  recurringExpenses$!: Observable<RecurringExpensesResponse>
  allPendingExpenseSummary$!: Observable<PendingExpense[]>
  recentExpenseTransactions$!: Observable<Transaction[]>

  Math = Math

  constructor(private expenseService: UserExpenseService,
    private userService : UserService
  ) {}

  ngOnInit() {
  this.categories$ = this.userService.getCategories().pipe(
    catchError(() => of([]))
  );
  this.expenseSummary$ = this.expenseService.getExpenseSummary().pipe(
    catchError(() => of({ totalAmount: 0, recurringExpenseCount: 0, progressValue: 0 }))
  );
  this.recurringExpenses$ = this.expenseService.getRecurringExpensesWithSummary().pipe(
    catchError(() => of({ recurringExpenses: [], total: 0 }))
  );
  this.allPendingExpenseSummary$ = this.expenseService.getAllPendingExpenseSummary().pipe(
    catchError(() => of([]))
  );
  this.recentExpenseTransactions$ = this.expenseService.getRecentExpenseTransaction().pipe(
    catchError(() => of([]))
  );
}

  getFormattedContent(amount: number): string {
    return this.expenseService.formatCurrency(amount)
  }

  handleSubmitForm(expense: AddExpenseForm) {
    this.expenseService.addExpense(expense)
  }

  toggleRecurringExpense(expense: ExpenseResponseModel) {
    this.expenseService.toggleRecurringExpense(expense.id)
  }

  deleteRecurringExpense(expense: ExpenseResponseModel) {
    this.expenseService.deleteRecurringExpense(expense.id)
  }

  collectPendingPayment(expense: PendingExpense) {
    this.expenseService.payPendingExpense(expense.id)
  }
}
