import { Component, EventEmitter, Input, Output } from '@angular/core';
import { UserCategoryResponse } from '../../models/user-category.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { range } from 'rxjs';

export interface CardExpenseOverview {
  category : UserCategoryResponse;
  amount: number
  trend: number;
}

export interface filterValue{
  range : string,
  fromDate?:string,
  toDate?:string
}

@Component({
  selector: 'app-expense-overview',
  imports: [CommonModule, FormsModule],
  templateUrl: './expense-overview.component.html',
  styleUrl: './expense-overview.component.css'
})
export class ExpenseOverviewComponent {
  @Input() expenseCategories: CardExpenseOverview[] = [];

  @Output() filterChanged = new EventEmitter<filterValue>();

  selectedRange: string = 'monthly';
  fromDate?: string;
  toDate?: string;

  get totalSpend(): number {
    return this.expenseCategories.reduce((sum, e) => sum + e.amount, 0);
  }

  get circumference(): number {
    return 2 * Math.PI * 40;
  }

  get maxSpend(): number {
    return Math.max(this.totalSpend, 1); // prevent divide-by-zero
  }

  onFilterChange(): void {
    if(this.fromDate && this.toDate){
      this.filterChanged.emit({
      range: this.selectedRange,
      fromDate: this.fromDate,
      toDate: this.toDate
     });
    }
  }
  onRangeSelect(){
    if(this.selectedRange !== "custom"){
        this.filterChanged.emit({
      range: this.selectedRange,
      fromDate: '',
      toDate: ''
    });
    }
  }
}
