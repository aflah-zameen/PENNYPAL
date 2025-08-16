import { Component, EventEmitter, Output } from '@angular/core';
import { Observable } from 'rxjs';
import { Transaction } from '../../../models/transaction-management.model';
import { TransactionService } from '../../../services/transaction-management.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-flagged-transaction',
  imports: [CommonModule],
  templateUrl: './flagged-transaction.component.html',
  styleUrl: './flagged-transaction.component.css'
})
export class FlaggedTransactionComponent {
  @Output() transactionSelected = new EventEmitter<Transaction>()

  flaggedTransactions$!: Observable<Transaction[]>

  constructor(public transactionService: TransactionService) {}

  ngOnInit() {
    this.flaggedTransactions$ = this.transactionService.getFlaggedTransactions()
  }

  trackByTransactionId(index: number, transaction: Transaction): string {
    return transaction.id
  }

  reviewTransaction(transaction: Transaction) {
    this.transactionSelected.emit(transaction)
  }

  getTypeClass(type: string): string {
    const classes = {
      Income: "bg-green-100 text-green-800",
      Expense: "bg-red-100 text-red-800",
      Transfer: "bg-blue-100 text-blue-800",
      "Wallet Recharge": "bg-purple-100 text-purple-800",
      Lending: "bg-yellow-100 text-yellow-800",
      Saving: "bg-indigo-100 text-indigo-800",
    }
    return classes[type as keyof typeof classes] || "bg-gray-100 text-gray-800"
  }

  getStatusClass(status: string): string {
    const classes = {
      Success: "bg-green-100 text-green-800",
      Pending: "bg-yellow-100 text-yellow-800",
      Failed: "bg-red-100 text-red-800",
      Reversed: "bg-gray-100 text-gray-800",
    }
    return classes[status as keyof typeof classes] || "bg-gray-100 text-gray-800"
  }
}
