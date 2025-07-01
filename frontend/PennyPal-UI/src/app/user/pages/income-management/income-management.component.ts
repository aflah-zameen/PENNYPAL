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

@Component({
  selector: 'app-income-management',
  imports: [SummaryCardComponent, FeatureButtonComponent, CommonModule, RecentIncomeTransactionComponent, AddIncomeComponent],
  templateUrl: './income-management.component.html',
  styleUrl: './income-management.component.css'
})
export class IncomeManagementComponent implements OnInit,OnDestroy{
  readonly AddIncomeIcon = BanknoteArrowUp;

  private incomeAddedSubscritpion : Subscription | null = null; 

  totalIncome : number = 0;
  isModalOpen = false;
  incomes: IncomeModel[]|null =null; 

  constructor(private incomeService : UserIncomeService,
    private spinner : NgxSpinnerService
  ){
  }

  ngOnInit(): void {
      this.loadTotalIncome();
      this.loadAllIncomes();
      this.incomeAddedSubscritpion = this.incomeService.incomeAdded$.subscribe({
        next :()=>{
          this.loadTotalIncome();
          this.loadAllIncomes();
        }
      })

  }

  ngOnDestroy(): void {
      this.incomeAddedSubscritpion?.unsubscribe();
  }


  trackByIncome(index: number, income: any): number {
    return income.id;
  }

  formatDate(date: Date): string {
    const options: Intl.DateTimeFormatOptions = { 
      day: 'numeric', 
      month: 'long', 
      year: 'numeric' 
    };
    return date.toLocaleDateString('en-US', options);
  }

  formatTime(date: Date): string {
    const options: Intl.DateTimeFormatOptions = { 
      hour: 'numeric', 
      minute: '2-digit',
      hour12: true 
    };
    return date.toLocaleTimeString('en-US', options);
  }

  editIncome(income: any): void {
    console.log('Edit income:', income);
    // Implement edit functionality
  }

  deleteIncome(income: any): void {
    console.log('Delete income:', income);
    // Implement delete functionality
    // this.incomes = this.incomes.filter(i => i.id !== income.id);
  }

  handleSubmitForm(data : AddIncomeModel){
    console.log(data);
    this.incomeService.addIncome(data).subscribe({
      next : (data)=>{
        console.log(data);
      },
      error : (err)=>{
        console.log(err);
        
      }
    })
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
    this.incomeService.getAllIncomes().subscribe({
        next : (incomes)=>{
          this.incomes = incomes;
        }
      })
  }
}
