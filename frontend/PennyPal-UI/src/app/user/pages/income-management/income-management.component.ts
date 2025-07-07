import { Component, OnDestroy, OnInit } from '@angular/core';
import { SummaryCardComponent } from "../../components/summary-card/summary-card.component";
import { FeatureButtonComponent } from "../../components/feature-button/feature-button.component";
import { BanknoteArrowUp } from 'lucide-angular';
import { CommonModule } from '@angular/common';
import { RecentIncomeTransactionComponent } from "../../components/recent-income-transaction/recent-income-transaction.component";
import { AddIncomeComponent } from "../../modals/add-income/add-income.component";
import { UserIncomeService } from '../../services/user-income.service';
import { IncomeModel } from '../../models/income.model';
import { AddIncomeModel } from '../../models/add-income.model';
import { NgxSpinnerService } from 'ngx-spinner';
import { Observable, Subscription } from 'rxjs';
import { RecurringIncomeComponent } from "./recurring-income/recurring-income.component";
import { UserCategoryResponse } from '../../models/user-category.model';
import { UserService } from '../../services/user.service';
import { RecurringIncomesModel } from '../../models/recurring-income.model';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmDialogComponent } from '../../../shared/components/confirm-dialog/confirm-dialog.component';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-income-management',
  imports: [SummaryCardComponent, FeatureButtonComponent, CommonModule, AddIncomeComponent, RecentIncomeTransactionComponent, RecurringIncomeComponent],
  templateUrl: './income-management.component.html',
  styleUrl: './income-management.component.css'
})
export class IncomeManagementComponent implements OnInit,OnDestroy{
  // readonly AddIncomeIcon = BanknoteArrowUp;

  // private incomeAddedSubscritpion : Subscription | null = null; 

  totalIncome : number = 0;
  incomeAddedSubscritpion: Subscription | null = null;
  sizeOfRecentIncomes : number = 7; 
  categories: UserCategoryResponse[] = [];
  // isModalOpen = false;
  // incomes: IncomeModel[]|null =null; 

  constructor(private incomeService : UserIncomeService,
    private spinner : NgxSpinnerService,private userService : UserService,
    private dialog : MatDialog,private toastr : ToastrService
  ){
  }

  // ngOnInit(): void {
  //     this.loadTotalIncome();
  //     this.loadAllIncomes();
  //     this.incomeAddedSubscritpion = this.incomeService.incomeAdded$.subscribe({
  //       next :()=>{
  //         this.loadTotalIncome();
  //         this.loadAllIncomes();
  //       }
  //     })

  // }

  // ngOnDestroy(): void {
  //     this.incomeAddedSubscritpion?.unsubscribe();
  // }


  // trackByIncome(index: number, income: any): number {
  //   return income.id;
  // }

  // formatDate(date: Date): string {
  //   const options: Intl.DateTimeFormatOptions = { 
  //     day: 'numeric', 
  //     month: 'long', 
  //     year: 'numeric' 
  //   };
  //   return date.toLocaleDateString('en-US', options);
  // }

  // formatTime(date: Date): string {
  //   const options: Intl.DateTimeFormatOptions = { 
  //     hour: 'numeric', 
  //     minute: '2-digit',
  //     hour12: true 
  //   };
  //   return date.toLocaleTimeString('en-US', options);
  // }

  // editIncome(income: any): void {
  //   console.log('Edit income:', income);
  //   // Implement edit functionality
  // }

  // deleteIncome(income: any): void {
  //   console.log('Delete income:', income);
  //   // Implement delete functionality
  //   // this.incomes = this.incomes.filter(i => i.id !== income.id);
  // }

  


  isModalOpen = false

  incomes: IncomeModel[] = []
  recurringIncomes: RecurringIncomesModel|null = null;

  ngOnInit(): void {
      this.loadRecurringIncomes();
      this.loadCategories();
      this.loadTotalIncome();
      this.loadAllIncomes();
      this.incomeAddedSubscritpion = this.incomeService.incomeAdded$.subscribe({
        next :()=>{
          this.loadTotalIncome();
          this.loadAllIncomes();
          this.loadRecurringIncomes();
        }
      })

  }

  ngOnDestroy(): void {
      this.incomeAddedSubscritpion?.unsubscribe();
  }

  // get totalIncome(): number {
  //   return this.incomes.reduce((sum, income) => sum + income.amount, 0)
  // }

  get monthlyAverage(): number {
    if (this.incomes.length === 0) return 0
    return this.totalIncome / Math.max(1, this.getUniqueMonths())
  }

  private getUniqueMonths(): number {
    const months = new Set(this.incomes.map((income) => new Date(income.income_date).toISOString().slice(0, 7)))
    return months.size
  }

  trackByIncome(index: number, income: IncomeModel): number {
    return income.id!
  }

  handleSubmitForm(formData : IncomeModel){
    const newIncome= {
      source: formData.source,
      amount: formData.amount,
      income_date: formData.income_date,
      recurrence: formData.recurrence,
      frequency: formData.frequency,
      description: formData.notes || formData.description,
      status  : formData.status || 'active'
    }
    this.incomeService.addIncome(formData).subscribe({
      next : (data)=>{
        this.toastr.success('Income added successfully!');
        this.incomes.unshift(newIncome)
      },
      error : (err)=>{
        this.toastr.error(`Failed to add income: ${err.message}`);        
      }
    })
  }

  toggleRecurringIncome(income : {
      id : number,
    amount : number,
    source : string,
    incomeDate : string,
    recurrence : boolean,
    frequency : string,
    active : boolean,
  }): void {
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
        this.spinner.show();
        if (confirmed) {
          this.incomeService.toggleRecurringIncome(income.id).subscribe({
            next: (recurringIncome) => {
              this.spinner.hide();
              this.recurringIncomes!.recurringIncomeDTOS = this.recurringIncomes!.recurringIncomeDTOS.filter((i) => i.id !== recurringIncome.id);
              this.toastr.success(`${action}d recurring income successfully!`);
              this.loadRecurringIncomes(); // Refresh the recurring incomes list
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
            this.spinner.show();
            if (confirmed) {
              this.incomeService.deleteRecurringIncome(incomeId).subscribe({
                next: () => {
                  this.spinner.hide();
                  this.toastr.success('Deleted recurring income successfully!');
                  this.loadRecurringIncomes(); // Refresh the recurring incomes list
                },
                error: (error) => {
                  this.spinner.hide();
                  this.toastr.error(`Failed to delete recurring income: ${error.message}`);
                }
              });
            }
          }
        });
  }

  onViewAllTransactions(): void {
    console.log("View all transactions clicked")
    // Implement navigation to full transactions page
  }

   loadTotalIncome(){
      this.spinner.show();
      this.incomeService.getTotalIncome().subscribe({
        next : (data)=>{
          this.totalIncome = data;
          this.spinner.hide();
        }
      })
  }
  loadAllIncomes(){
    this.incomeService.getRecentIncomes(this.sizeOfRecentIncomes).subscribe({
        next : (incomes)=>{
          this.incomes = incomes;
        }
      })
  }
  loadCategories(): void {
    this.userService.getCategories().subscribe({
      next: (categories: UserCategoryResponse[]) => {
        this.categories = categories.filter(category => category.usageTypes.includes('INCOME') || category.usageTypes.includes('SHARED'));
      },
      error: (error) => {
        console.error('Error loading categories:', error);
      }
    });
}

loadRecurringIncomes(): void {
  this.incomeService.getRecurringIncomes().subscribe({
    next: (recurringIncomes) => {
      this.recurringIncomes = recurringIncomes;
    },
    error: (error) => {
      console.error('Error loading recurring incomes:', error);
    }
  });
}
}
