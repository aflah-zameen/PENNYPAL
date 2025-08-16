import { CommonModule, CurrencyPipe, TitleCasePipe } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { AllPendingIncomesSummary, PendingIncomesModel } from '../../models/income-summary-model';
import { PendingTransaction, PendingTransactionTotalSummary } from '../../models/transaction.model';

@Component({
  selector: 'app-pending-incomes',
  imports: [CommonModule,TitleCasePipe],
  templateUrl: './pending-incomes.component.html',
  styleUrl: './pending-incomes.component.css',
  providers : [CurrencyPipe]
})
export class PendingIncomesComponent {
 @Input() pendingIncomes!: PendingTransactionTotalSummary | null
  @Output() collectIncomePayment = new EventEmitter<PendingTransaction>()

  constructor(private currencyPipe : CurrencyPipe){}

  ngOnInit() {
    console.log("pending incomes :"+ this.pendingIncomes);
    
  }

  getStatusBadgeClass(status: string): string {
    switch (status) {
      case "overdue":
        return "bg-red-100 text-red-800"
      case "pending":
        return "bg-yellow-100 text-yellow-800"
      default:
        return "bg-gray-100 text-gray-800"
    }
  }

  getStatusIconClass(status: string): string {
    switch (status) {
      case "overdue":
        return "text-red-600"
      case "pending":
        return "text-yellow-600"
      default:
        return "text-gray-600"
    }
  }

  getPayButtonClass(status: string): string {
    return status === "overdue" ? "bg-red-600 hover:bg-red-700" : "bg-blue-600 hover:bg-blue-700"
  }

  isOverdue(dueDate: string): boolean {
    return new Date(dueDate) < new Date()
  }

  formatDate(dateString: string): string {
    return new Date(dateString).toLocaleDateString("en-US", {
      month: "short",
      day: "numeric",
      year: "numeric",
    })
  }


   getFormattedContent(content : number): string {
      return this.currencyPipe.transform(content, 'USD', 'symbol', '1.2-2') || '$0.00';
  }
  onCollectPendingIncome(income : PendingTransaction ){
    if(income != null)
      this.collectIncomePayment.emit(income);
  }
}
