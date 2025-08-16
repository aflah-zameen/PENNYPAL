import { Component, OnDestroy, OnInit } from '@angular/core';
import { CommonModule, CurrencyPipe } from '@angular/common';
import { AddIncomeComponent } from "../../modals/add-income/add-income.component";
import { NgxSpinnerService } from 'ngx-spinner';
import { catchError, finalize, map, Observable, of, Subscription, tap } from 'rxjs';
import { UserCategoryResponse } from '../../models/user-category.model';
import { UserService } from '../../services/user.service';
import { RecurringIncomesModel } from '../../models/recurring-income.model';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmDialogComponent } from '../../../shared/components/confirm-dialog/confirm-dialog.component';
import { ToastrService } from 'ngx-toastr';
import { PendingIncomesComponent } from "../../components/pending-incomes/pending-incomes.component";
import{MatIconModule} from "@angular/material/icon"
import { AddRecurringIncomeComponent } from "../../modals/add-recurring-income/add-recurring-income.component";
import { IncomeRequestModel, IncomeResponseModel,} from '../../models/income.model';
import { AllPendingIncomesSummary, IncomeSummary, PendingIncomesModel, PendingIncomeSummary } from '../../models/income-summary-model';
import { PaymentMethod, PendingTransaction, PendingTransactionTotalSummary, RecurringTransactionRequest, RecurringTransactionResponse, RecurringTransactionSummary, Transaction, TransactionRequest, TransactionSummaryResponseDTO } from '../../models/transaction.model';
import { UserTransactionService } from '../../services/user-transactions.service';
import { UserCardService } from '../../services/user-card.service';

@Component({
  selector: 'app-income-management',
  imports: [CommonModule, PendingIncomesComponent, AddIncomeComponent, MatIconModule, AddRecurringIncomeComponent],
  templateUrl: './income-management.component.html',
  styleUrl: './income-management.component.css',
  providers : [CurrencyPipe]
})
export class IncomeManagementComponent implements OnInit,OnDestroy{
  // readonly AddIncomeIcon = BanknoteArrowUp;

  // private incomeAddedSubscritpion : Subscription | null = null; 

  incomeSummary$! : Observable<TransactionSummaryResponseDTO>; 
  recentIncomeTransactions$! : Observable<Transaction[]|null>;
  recurringIncomes$! : Observable<RecurringTransactionSummary|null>;
  allPendingIncomeSummary$! : Observable<PendingTransactionTotalSummary|null>;
  incomeAddedSubscritpion: Subscription | null = null;
  sizeOfRecentIncomes : number = 5; 
  categories$!: Observable<UserCategoryResponse[]> ;
  paymentMethods$!: Observable<PaymentMethod[]> ;
  



  isIncomeModalOpen = false
  isRecurringModalOpen = false;

  // pendingIncomes$ : Observable<> 

  constructor(private transactionService : UserTransactionService,
    private spinner : NgxSpinnerService,private userService : UserService,
    private dialog : MatDialog,private toastr : ToastrService,private currencyPipe : CurrencyPipe,
    private cardService : UserCardService
  ){

  }

  ngOnInit(): void {
    this.loadCategories();
    this.incomeSummary$ = this.transactionService.getTransactionSummary('INCOME');
    this.recentIncomeTransactions$ = this.transactionService.getRecentTransactions('INCOME', this.sizeOfRecentIncomes);
    this.recurringIncomes$ = this.transactionService.getRecurringTransactions('INCOME');
    this.allPendingIncomeSummary$ = this.transactionService.getPendingTransactionSummary('INCOME');
    
    this.incomeAddedSubscritpion = this.transactionService.reload$.subscribe({
        next :()=>{
          this.loadIncomeSummary();
          this.loadRecentIncomeTransactions();
          this.loadRecurringIncomes();
          this.loadAllPendingIncomeSummary();
        }
      })

    this.paymentMethods$ = this.cardService.getCardsSummary().pipe(
      map(cards => cards.map(card => ({
        id: card.id,
        name: card.name,
        cardNumber: card.number
      } as PaymentMethod))),
    );  

  }

  ngOnDestroy(): void {
      this.incomeAddedSubscritpion?.unsubscribe();
  }

  // get totalIncome(): number {
  //   return this.incomes.reduce((sum, income) => sum + income.amount, 0)
  // }

  // get monthlyAverage(): number {
  //   if (this.incomes.length === 0) return 0
  //   return this.totalIncome / Math.max(1, this.getUniqueMonths())
  // }


  handleSubmitForm(formData : TransactionRequest){
    this.spinner.show()
    this.transactionService.addTransaction(formData).subscribe({
      next : (data)=>{
        this.toastr.success('Income added successfully!');
        this.spinner.hide();
      },
      error : (err)=>{
        this.spinner.hide();
        this.toastr.error(`Failed to add income: ${err.message}`);        
      }
    })
  }

  toggleRecurringIncome(income : RecurringTransactionResponse): void {
    const action = income.active ? 'Deactivate' : 'Activate';
    const message = `Are you sure you want to ${action.toLowerCase()} this recurring income?`;
    this.dialog.open(ConfirmDialogComponent,{
      width: '400px',
        data: {
          title: `Confirm ${action} Recurring Income`,
          message: `Are you sure you want to ${action.toLowerCase()} this recurring income?`,
          confirmText: `${action}`,
          cancelText: 'Cancel'
        }
    }).afterClosed().subscribe({
      next: (confirmed) => {
        if (confirmed) {
          this.spinner.show();
          this.transactionService.toggleRecurringTransactionStatus(income.recurringId).subscribe({
            next: (recurringIncome) => {
              this.spinner.hide();
              this.toastr.success(`${action}d recurring income successfully!`);
              this.loadRecurringIncomes(); // Refresh the recurring incomes list
              this.loadIncomeSummary() // Refresh the summary components
            },
            error: (error) => {
              this.spinner.hide();
              this.toastr.error(`Failed to ${action.toLowerCase()} recurring income: ${error.message}`);
            }
          });
        }
      }
    }); 
  }

  deleteRecurringIncome(incomeId: string): void {
        const message = `Are you sure you want to delete this recurring income?`;
        this.dialog.open(ConfirmDialogComponent, {
          width: '400px',
          data: {
            title: 'Confirm Delete Recurring Income',
            message: message,
            confirmText: 'Delete',
            cancelText: 'Cancel'
          }
        }).afterClosed().subscribe({
          next: (confirmed) => {
           if(confirmed){
             this.spinner.show();
            if (confirmed) {
              this.transactionService.deleteRecurringTransaction(incomeId).subscribe({
                next: () => {
                  this.spinner.hide();
                  this.toastr.success('Deleted recurring income successfully!');
                  this.loadRecurringIncomes(); // Refresh the recurring incomes list
                  this.loadIncomeSummary(); // Refresh the summary components
                },
                error: (error) => {
                  this.spinner.hide();
                  this.toastr.error(`Failed to delete recurring income: ${error.message}`);
                }
              });
            }
           }
          }
        });
  }


   loadIncomeSummary(){
     this.spinner.show();
        this.incomeSummary$ = this.transactionService.getTransactionSummary("INCOME").pipe(
          finalize(() => this.spinner.hide()),
          catchError(err => {
            this.toastr.error(err.message || 'Something went wrong');
            return of({} as TransactionSummaryResponseDTO);
          })
        );
    }
  loadRecentIncomeTransactions(){
    this.spinner.show();
      this.recentIncomeTransactions$ = this.transactionService.getRecentTransactions("INCOME", this.sizeOfRecentIncomes).pipe(
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
          map(categories => categories.filter(cat  => cat.usageTypes.includes('INCOME') || cat.usageTypes.includes('SHARED'))),
          finalize(() => this.spinner.hide()),
          catchError(err => {
            this.toastr.error(err.message || 'Something went wrong');
            return of([]);
          })
        );
      }

loadRecurringIncomes(): void {
  this.spinner.show();
    this.recurringIncomes$ = this.transactionService.getRecurringTransactions("INCOME").pipe(
      finalize(() => this.spinner.hide()),
      catchError(err => {
        this.toastr.error(err.message || 'Something went wrong');
        return of(null as RecurringTransactionSummary | null);
      })
    );
}

handleRecurringIncomeSubmit(formData: RecurringTransactionRequest) {
  this.spinner.show();
    this.transactionService.addRecurringTransaction(formData).subscribe({
      next : (data)=>{
        this.spinner.hide();
        this.toastr.success('Income added successfully!');
      },
      error : (err)=>{
        this.spinner.hide();
        this.toastr.error(`Failed to add income: ${err.message}`);        
      }
    })
}

loadAllPendingIncomeSummary(){
  this.spinner.show();
    this.allPendingIncomeSummary$ = this.transactionService.getPendingTransactionSummary("INCOME").pipe(
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
  collectPendingPayment(income : PendingTransaction){
    const message = `
    <b>You are about to add the following income to your tracker:</b><br><br>
    <strong>• Title:</strong> ${income.title}<br>
    <strong>• Amount:</strong> ${this.getFormattedContent(income.amount)}<br>
    <strong>• Date:</strong> ${income.transactionDate}<br>
    <strong>• Category:</strong> ${income.category.name}<br><br>
    Do you want to proceed?
    `;


    this.dialog.open(ConfirmDialogComponent, {
    width: '400px',
    data: {
      title: 'Add This Income?',
      message: message,
      confirmText: 'Add Income',
      cancelText: 'Cancel'
    }
    }).afterClosed().subscribe({
      next : (confirmed)=>{
        if(confirmed){
          this.spinner.show();
          this.transactionService.collectPendingTransaction(income.transactionId).subscribe({
            next : ()=>{
              this.toastr.success("Income added successfully");
              this.loadAllPendingIncomeSummary();
              this.loadIncomeSummary();
              this.loadRecentIncomeTransactions();
              this.spinner.hide();
            },
            error:(err)=>{
              this.toastr.error(`Failed to collect pending income: ${err.errorCode}`);
              this.spinner.hide();
            }
          })
        }
      }
    })
  }


}
