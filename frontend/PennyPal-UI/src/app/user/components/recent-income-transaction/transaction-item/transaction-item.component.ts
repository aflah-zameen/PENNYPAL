import { Component, Input } from '@angular/core';
import { Transaction } from '../../../models/transaction.model';
import { IncomeModel } from '../../../models/income.model';
import { CommonModule, CurrencyPipe } from '@angular/common';

@Component({
  selector: 'app-transaction-item',
  imports: [CommonModule],
  templateUrl: './transaction-item.component.html',
  styleUrl: './transaction-item.component.css',
  providers :[CurrencyPipe]
})
export class TransactionItemComponent {
  @Input() incomes!: IncomeModel

  getFormattedContent(): string {
    return new Intl.NumberFormat("en-US", {
      style: "currency",
      currency: "USD",
      minimumFractionDigits: 0,
      maximumFractionDigits: 0,
    }).format(this.incomes.amount!)
  }

  getCategoryType(): number {
    return this.incomes.category!.id 
  }

  getCategoryBgClass(): string {
    const classes: { [key: string]: string } = {
      salary: "bg-gradient-to-br from-blue-100 to-blue-200",
      freelance: "bg-gradient-to-br from-green-100 to-green-200",
      investment: "bg-gradient-to-br from-purple-100 to-purple-200",
      business: "bg-gradient-to-br from-orange-100 to-orange-200",
      other: "bg-gradient-to-br from-gray-100 to-gray-200",
    }
    return classes[this.getCategoryType()] || classes['other']
  }

  getCategoryIconClass(): string {
    const classes: { [key: string]: string } = {
      salary: "text-blue-600",
      freelance: "text-green-600",
      investment: "text-purple-600",
      business: "text-orange-600",
      other: "text-gray-600",
    }
    return classes[this.getCategoryType()] || classes['other']
  }

  formatDate(dateString: string): string {
    return new Date(dateString).toLocaleDateString("en-US", {
      month: "short",
      day: "numeric",
    })
  }

  formatDayOfWeek(dateString: string): string {
    return new Date(dateString).toLocaleDateString("en-US", {
      weekday: "short",
    })
  }

  getStatus(): string {
    return "Active"
  }
}
