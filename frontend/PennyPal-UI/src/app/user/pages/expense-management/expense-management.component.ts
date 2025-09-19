import { Component } from '@angular/core';
import { UserCategoryResponse } from '../../models/user-category.model';
import { catchError, finalize, map, Observable, of, Subscription, tap } from 'rxjs';
import { PaymentMethod, PendingTransaction, PendingTransactionTotalSummary, RecurringTransactionRequest, RecurringTransactionResponse, RecurringTransactionSummary, Transaction, TransactionRequest, TransactionSummaryResponseDTO } from '../../models/transaction.model';
import { UserService } from '../../services/user.service';
import { PendingExpensesComponent } from "../../components/pending-expenses/pending-expenses.component";
import { CommonModule, CurrencyPipe } from '@angular/common';
import { AddExpenseModalComponent } from "../../modals/add-expense-modal/add-expense-modal.component";
import { AddRecurringExpenseModalComponent } from "../../modals/add-recurring-expense-modal/add-recurring-expense-modal.component";
import { UserTransactionService } from '../../services/user-transactions.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { MatDialog } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { ConfirmDialogComponent } from '../../../shared/components/confirm-dialog/confirm-dialog.component';
import { UserCardService } from '../../services/user-card.service';

@Component({
  selector: 'app-expense-management',
  imports: [PendingExpensesComponent, CommonModule, AddExpenseModalComponent, AddRecurringExpenseModalComponent],
  templateUrl: './expense-management.component.html',
  styleUrl: './expense-management.component.css',
  providers : [CurrencyPipe]
})
export class ExpenseManagementComponent {
  isExpenseModalOpen = false
  isRecurringModalOpen = false

  expenseSummary$!: Observable<TransactionSummaryResponseDTO>
  recentExpenseTransactions$!: Observable<Transaction[]>
  recurringExpenses$!: Observable<RecurringTransactionSummary | null>
  allPendingExpenseSummary$!: Observable<PendingTransactionTotalSummary | null>
  reloadSubscription: Subscription | null = null;
  categories$: Observable<UserCategoryResponse[]> | null = null;
  paymentMethods$!: Observable<PaymentMethod[]> ;

  sizeOfRecentExpenses : number = 5; 
  categories: UserCategoryResponse[] = [];
  Math = Math

   constructor(private transactionService : UserTransactionService,
    private spinner : NgxSpinnerService,private userService : UserService,
    private dialog : MatDialog,private toastr : ToastrService,private currencyPipe : CurrencyPipe,
    private cardService : UserCardService
  ){

  }

  ngOnInit() {
    this.loadCategories();
    this.expenseSummary$ = this.transactionService.getTransactionSummary('EXPENSE');
    this.recentExpenseTransactions$ = this.transactionService.getRecentTransactions('EXPENSE', this.sizeOfRecentExpenses);
    this.recurringExpenses$ = this.transactionService.getRecurringTransactions('EXPENSE');
    this.allPendingExpenseSummary$ = this.transactionService.getPendingTransactionSummary('EXPENSE');
    this.paymentMethods$ = this.cardService.getCardsSummary().pipe(
      map(cards => cards.map(card => ({
        id: card.id,
        name: card.name,
        cardNumber: card.number
      } as PaymentMethod)))
    );  
    
    this.reloadSubscription = this.transactionService.reload$.subscribe({
        next :()=>{
          this.loadExpenseSummary();
          this.loadRecentExpenseTransactions();
          this.loadRecurringExpenses();
          this.loadAllPendingExpenseSummary();
        }
      })

}
  handleSubmitForm(formData : TransactionRequest){
      this.spinner.show()
      this.transactionService.addTransaction(formData).subscribe({
        next : (data)=>{
          this.toastr.success('Expense added successfully!');
          this.spinner.hide();
        },
        error : (err)=>{
          this.spinner.hide();
          this.toastr.error(`Failed to add expense: ${err.message}`);        
        }
      })
    }
  
    toggleRecurringExpense(expense : RecurringTransactionResponse): void {
      const action = expense.active ? 'Deactivate' : 'Activate';
      const message = `Are you sure you want to ${action.toLowerCase()} this recurring expense?`;
      this.dialog.open(ConfirmDialogComponent,{
        width: '400px',
          data: {
            title: `Confirm ${action} Recurring Expense`,
            message: `Are you sure you want to ${action.toLowerCase()} this recurring expense?`,
            confirmText: `${action}`,
            cancelText: 'Cancel'
          }
      }).afterClosed().subscribe({
        next: (confirmed) => {
          if (confirmed) {
            this.spinner.show();
            this.transactionService.toggleRecurringTransactionStatus(expense.recurringId).subscribe({
              next: (recurringExpense) => {
                this.spinner.hide();
                this.toastr.success(`${action}d recurring expense successfully!`);
                this.loadRecurringExpenses(); // Refresh the recurring expenses list
                this.loadExpenseSummary() // Refresh the summary components
              },
              error: (error) => {
                this.spinner.hide();
                this.toastr.error(`Failed to ${action.toLowerCase()} recurring expense: ${error.message}`);
              }
            });
          }
        }
      }); 
    }
  
    deleteRecurringExpense(expenseId: string): void {
          const message = `Are you sure you want to delete this recurring expense?`;
          this.dialog.open(ConfirmDialogComponent, {
            width: '400px',
            data: {
              title: 'Confirm Delete Recurring Expense',
              message: message,
              confirmText: 'Delete',
              cancelText: 'Cancel'
            }
          }).afterClosed().subscribe({
            next: (confirmed) => {
             if(confirmed){
               this.spinner.show();
              if (confirmed) {
                this.transactionService.deleteRecurringTransaction(expenseId).subscribe({
                  next: () => {
                    this.spinner.hide();
                    this.toastr.success('Deleted recurring expense successfully!');
                    this.loadRecurringExpenses(); // Refresh the recurring expenses list
                    this.loadExpenseSummary(); // Refresh the summary components
                  },
                  error: (error) => {
                    this.spinner.hide();
                    this.toastr.error(`Failed to delete recurring expense: ${error.message}`);
                  }
                });
              }
             }
            }
          });
    }
  
  
     loadExpenseSummary(){
        this.spinner.show();
        this.expenseSummary$ = this.transactionService.getTransactionSummary("EXPENSE").pipe(
          finalize(() => this.spinner.hide()),
          catchError(err => {
            this.toastr.error(err.message || 'Something went wrong');
            return of({} as TransactionSummaryResponseDTO);
          })
        );
      }
    loadRecentExpenseTransactions(){
      this.spinner.show();
      this.recentExpenseTransactions$ = this.recentExpenseTransactions$ = this.transactionService.getRecentTransactions("EXPENSE", this.sizeOfRecentExpenses).pipe(
        finalize(() => this.spinner.hide()),
        catchError(err => {
            this.toastr.error(err.message || 'Something went wrong')
            return of([]);
          })
      )
    }
  
    loadCategories(): void {
      this.spinner.show();
      this.categories$ = this.userService.getCategories().pipe(
        map(categories => categories.filter(cat  => cat.usageTypes.includes('EXPENSE') || cat.usageTypes.includes('SHARED'))),
        finalize(() => this.spinner.hide()),
        catchError(err => {
          this.toastr.error(err.message || 'Something went wrong');
          return of([]);
        })
      );
    }
  
  loadRecurringExpenses(): void {
    this.spinner.show();
    this.recurringExpenses$ = this.transactionService.getRecurringTransactions("EXPENSE").pipe(
      finalize(() => this.spinner.hide()),
      catchError(err => {
        this.toastr.error(err.message || 'Something went wrong');
        return of(null as RecurringTransactionSummary | null);
      })
    );
  }
  
  handleRecurringExpenseSubmit(formData: RecurringTransactionRequest) {    
    this.spinner.show();
      this.transactionService.addRecurringTransaction(formData).subscribe({
        next : (data)=>{
          this.spinner.hide();
          this.toastr.success('Expense added successfully!');
        },
        error : (err)=>{
          this.spinner.hide();
          this.toastr.error(`Failed to add expense: ${err.message}`);        
        }
      })
  }

  loadAllPendingExpenseSummary(){
    this.spinner.show();
    this.allPendingExpenseSummary$ = this.transactionService.getPendingTransactionSummary("EXPENSE").pipe(
      finalize(() => this.spinner.hide()),
      catchError(err => {
        this.toastr.error(err.message || 'Something went wrong');
        return of(null as PendingTransactionTotalSummary | null);
      })
    );
  }
  
     getFormattedContent(amount: number | undefined | null): string {
        if (amount == null) return '$0.00';
        return this.currencyPipe.transform(amount, 'USD', 'symbol', '1.2-2') || '$0.00';
    }
  
    // Collect Pending payments
    collectPendingPayment(expense : PendingTransaction){
      const message = `
      <b>You are about to add the following expense to your tracker:</b><br><br>
      <strong>• Title:</strong> ${expense.title}<br>
      <strong>• Amount:</strong> ${this.getFormattedContent(expense.amount)}<br>
      <strong>• Date:</strong> ${expense.transactionDate}<br>
      <strong>• Category:</strong> ${expense.category.name}<br><br>
      Do you want to proceed?
      `;
  
  
      this.dialog.open(ConfirmDialogComponent, {
      width: '400px',
      data: {
        title: 'Add This Expense?',
        message: message,
        confirmText: 'Add Expense',
        cancelText: 'Cancel'
      }
      }).afterClosed().subscribe({
        next : (confirmed)=>{
          if(confirmed){
            this.spinner.show();
            this.transactionService.collectPendingTransaction(expense.transactionId).subscribe({
              next : ()=>{
                this.toastr.success("Expense added successfully");
                this.loadAllPendingExpenseSummary();
                this.loadExpenseSummary();
                this.loadRecentExpenseTransactions();
                this.spinner.hide();
              },
              error:(err)=>{
                this.toastr.error(`Failed to collect pending expense: ${err.errorCode}`);
                this.spinner.hide();
              }
            })
          }
        }
      })
    }


 ngOnDestroy(): void {
      this.reloadSubscription?.unsubscribe();
  }

  
}
