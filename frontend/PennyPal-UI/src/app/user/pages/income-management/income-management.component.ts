import { Component, OnDestroy, OnInit } from '@angular/core';
import { CommonModule, CurrencyPipe } from '@angular/common';
import { AddIncomeComponent } from "../../modals/add-income/add-income.component";
import { UserIncomeService } from '../../services/user-income.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { catchError, finalize, Observable, of, Subscription } from 'rxjs';
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
import { Transaction } from '../../models/transaction.model';

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

  incomeSummary$! : Observable<IncomeSummary>; 
  recentIncomeTransactions$ : Observable<Transaction[]|null>;
  allPendingIncomeSummary$! : Observable<AllPendingIncomesSummary|null>;
  incomeAddedSubscritpion: Subscription | null = null;
  sizeOfRecentIncomes : number = 5; 
  categories: UserCategoryResponse[] = [];



  isIncomeModalOpen = false
  isRecurringModalOpen = false;

  incomes: IncomeResponseModel[] = []
  recurringIncomes$: Observable<RecurringIncomesModel|null>;
  // pendingIncomes$ : Observable<> 

  constructor(private incomeService : UserIncomeService,
    private spinner : NgxSpinnerService,private userService : UserService,
    private dialog : MatDialog,private toastr : ToastrService,private currencyPipe : CurrencyPipe
  ){
    this.recentIncomeTransactions$ = this.incomeService.recentIncomeTransaction$;
    this.recurringIncomes$ = this.incomeService.recurringIncome$
    this.allPendingIncomeSummary$ = this.incomeService.allPendingIncomeSummary$;
  }

  ngOnInit(): void {
      this.loadRecurringIncomes();
      this.loadCategories();
      this.loadIncomeSummary();
      this.loadRecentIncomeTransactions();
      this.loadAllPendingIncomeSummary();
      this.incomeAddedSubscritpion = this.incomeService.incomeAdded$.subscribe({
        next :()=>{
          this.loadIncomeSummary();
          this.loadRecentIncomeTransactions();
          this.loadRecurringIncomes();
          this.loadAllPendingIncomeSummary();
        }
      })

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


  handleSubmitForm(formData : IncomeRequestModel){
    this.spinner.show()
    this.incomeService.addIncome(formData).subscribe({
      next : (data)=>{
        this.toastr.success('Income added successfully!');
        this.incomes.unshift(data)
        this.spinner.hide();
      },
      error : (err)=>{
        this.spinner.hide();
        this.toastr.error(`Failed to add income: ${err.message}`);        
      }
    })
  }

  toggleRecurringIncome(income : IncomeResponseModel): void {
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
          this.incomeService.toggleRecurringIncome(income.id).subscribe({
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

  deleteRecurringIncome(incomeId: number): void {
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
              this.incomeService.deleteRecurringIncome(incomeId).subscribe({
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
      this.incomeSummary$ = this.incomeService.getIncomeSummary().pipe(
        finalize(() => this.spinner.hide()), // Always hide spinner on success/fail
        catchError(err => {
          this.toastr.error(err.message || 'Something went wrong')
          return of({totalIncomeSummary : {totalAmount :0, progressValue : null},
                      pendingIncomeSummary : {totalAmount : 0, pendingIncomes : 0},
                      activeRecurringIncome :{count : 0}});
        })
      );
    }
  loadRecentIncomeTransactions(){
    this.spinner.show();
    this.recentIncomeTransactions$ = this.incomeService.getRecentIncomeTransactions(this.sizeOfRecentIncomes).pipe(
      finalize(() => this.spinner.hide()),
      catchError(err => {
          this.toastr.error(err.message || 'Something went wrong')
          return of([]);
        })
    )
  }

  loadCategories(): void {
    this.spinner.show();
    this.userService.getCategories().subscribe({
      next: (categories: UserCategoryResponse[]) => {
        this.categories = categories.filter(category => category.usageTypes.includes('INCOME') || category.usageTypes.includes('SHARED'));
        this.spinner.hide();
      },
      error: (error) => {
        this.spinner.hide();
        console.error('Error loading categories:', error);
      }
    });
}

loadRecurringIncomes(): void {
  this.spinner.show();
  this.incomeService.getRecurringIncomes().subscribe({
    next: (recurringIncomes) => {
        this.spinner.hide();
    },
    error: (error) => {
      this.spinner.hide();
      console.error('Error loading recurring incomes:', error);
    }
  });
}

handleRecurringIncomeSubmit(formData: IncomeRequestModel){
  this.spinner.show();
    this.incomeService.addIncome(formData).subscribe({
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
  this.incomeService.getAllPendingIncomesSummary().subscribe({
    next : ()=>{
      this.spinner.hide();
    },
    error:(err)=>{
      this.spinner.hide();
      this.toastr.error(`Failed to load pending incomes: ${err.errorCode}`);
    }
  })
}


   getFormattedContent(amount: number | undefined | null): string {
      if (amount == null) return '$0.00';
      return this.currencyPipe.transform(amount, 'USD', 'symbol', '1.2-2') || '$0.00';
  }

  // Collect Pending payments
  collectPendingPayment(income : PendingIncomesModel){
    const message = `
    <b>You are about to add the following income to your tracker:</b><br><br>
    <strong>• Title:</strong> ${income.title}<br>
    <strong>• Amount:</strong> ${this.getFormattedContent(income.amount)}<br>
    <strong>• Date:</strong> ${income.incomeDate}<br>
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
          this.incomeService.collectPendingIcomes(income).subscribe({
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
