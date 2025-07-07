import { Component, EventEmitter, Input, OnChanges, OnInit, Output } from '@angular/core';
import { IncomeModel } from '../../../models/income.model';
import { CommonModule } from '@angular/common';
import { RecurringIncomesModel } from '../../../models/recurring-income.model';

@Component({
  selector: 'app-recurring-income',
  imports: [CommonModule],
  templateUrl: './recurring-income.component.html',
  styleUrl: './recurring-income.component.css'
})
export class RecurringIncomeComponent implements OnChanges{
  @Input() recurringincomes: RecurringIncomesModel | null = null
  @Output() toggleIncome = new EventEmitter<{
      id : number,
    amount : number,
    source : string,
    incomeDate : string,
    recurrence : boolean,
    frequency : string,
    active : boolean,
  }>()
  @Output() deleteIncome = new EventEmitter<number>()
  @Output() addIncome = new EventEmitter<void>()

  ngOnChanges(): void {
    console.log("Recurring Incomes Updated:", this.recurringincomes);
    
  }

  trackByIncome(index: number, income: IncomeModel): number {
    return income.id!
  }

  getCategoryBgClass(category?: string): string {
    const classes: { [key: string]: string } = {
      salary: "bg-gradient-to-br from-blue-100 to-blue-200",
      freelance: "bg-gradient-to-br from-green-100 to-green-200",
      investment: "bg-gradient-to-br from-purple-100 to-purple-200",
      business: "bg-gradient-to-br from-orange-100 to-orange-200",
      other: "bg-gradient-to-br from-gray-100 to-gray-200",
    }
    return classes[category || "other"] || classes['other']
  }

  getCategoryIconClass(category?: string): string {
    const classes: { [key: string]: string } = {
      salary: "text-blue-600",
      freelance: "text-green-600",
      investment: "text-purple-600",
      business: "text-orange-600",
      other: "text-gray-600",
    }
    return classes[category || "other"] || classes['other']
  }

  formatDate(dateString: string): string {
    return new Date(dateString).toLocaleDateString("en-US", {
      month: "short",
      day: "numeric",
    })
  }

  formatCurrency(amount: number): string {
    return new Intl.NumberFormat("en-US", {
      style: "currency",
      currency: "USD",
      minimumFractionDigits: 0,
      maximumFractionDigits: 0,
    }).format(amount)
  }


  onToggleIncome(income: {
      id : number,
    amount : number,
    source : string,
    incomeDate : string,
    recurrence : boolean,
    frequency : string,
    active : boolean,
  }): void {
    this.toggleIncome.emit(income)
  }

  onDeleteIncome(income: number): void {
    this.deleteIncome.emit(income)
  }

  onAddIncome(): void {
    this.addIncome.emit()
  }
}
