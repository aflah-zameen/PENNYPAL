import { Component } from '@angular/core';
import { WalletTransaction } from '../../models/wallet.model';
import { Observable } from 'rxjs';
import { WalletService } from '../../services/wallet-management.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-wallet-transaction-list',
  imports: [CommonModule],
  templateUrl: './wallet-transaction-list.component.html',
  styleUrl: './wallet-transaction-list.component.css'
})
export class WalletTransactionListComponent {
  transactions$!: Observable<WalletTransaction[]>
  expandedTransaction: string | null = null

  constructor(public walletService: WalletService) {}

  ngOnInit() {
    this.transactions$ = this.walletService.transactions$
  }

  trackByTransactionId(index: number, transaction: WalletTransaction): string {
    return transaction.id
  }

  toggleExpanded(transactionId: string) {
    this.expandedTransaction = this.expandedTransaction === transactionId ? null : transactionId
  }

  getTransactionIconClass(type: string, amount: number): string {
    if (amount > 0) {
      return "bg-gradient-to-r from-green-100 to-emerald-100 text-green-600"
    } else {
      return "bg-gradient-to-r from-red-100 to-pink-100 text-red-600"
    }
  }

  getStatusClass(status: string): string {
    const classes = {
      completed: "bg-green-100 text-green-800",
      pending: "bg-yellow-100 text-yellow-800",
      failed: "bg-red-100 text-red-800",
      cancelled: "bg-gray-100 text-gray-800",
    }
    return classes[status as keyof typeof classes] || "bg-gray-100 text-gray-800"
  }
}
