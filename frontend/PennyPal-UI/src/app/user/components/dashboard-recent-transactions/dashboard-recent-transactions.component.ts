import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { FormsModule } from '@angular/forms';
export interface Transaction {
  id: string;
  type: 'income' | 'expense';
  category: string;
  description: string;
  amount: number;
  timestamp: string;
  merchant?: string;
  paymentMethod?: string;
  status: 'completed' | 'pending' | 'failed';
}
@Component({
  selector: 'app-dashboard-recent-transactions',
  imports: [CommonModule, FormsModule],
  templateUrl: './dashboard-recent-transactions.component.html',
  styleUrl: './dashboard-recent-transactions.component.css'
})
export class DashboardRecentTransactionsComponent {
   @Input() transactions: Transaction[] = [];
  
  selectedFilter = 'all';
  hasMoreTransactions = true;

  ngOnInit() {
    if (this.transactions.length === 0) {
      this.transactions = this.generateSampleTransactions();
    }
  }

  private generateSampleTransactions(): Transaction[] {
    return [
      {
        id: '1',
        type: 'expense',
        category: 'Food',
        description: 'Grocery Shopping',
        amount: 85.50,
        timestamp: '2 hours ago',
        merchant: 'Whole Foods',
        paymentMethod: 'Credit Card',
        status: 'completed'
      },
      {
        id: '2',
        type: 'income',
        category: 'Freelance',
        description: 'Web Development Project',
        amount: 450.00,
        timestamp: '5 hours ago',
        merchant: 'Client ABC',
        paymentMethod: 'Bank Transfer',
        status: 'completed'
      },
      {
        id: '3',
        type: 'expense',
        category: 'Transportation',
        description: 'Gas Station',
        amount: 42.30,
        timestamp: '1 day ago',
        merchant: 'Shell',
        paymentMethod: 'Debit Card',
        status: 'completed'
      },
      {
        id: '4',
        type: 'expense',
        category: 'Entertainment',
        description: 'Netflix Subscription',
        amount: 15.99,
        timestamp: '2 days ago',
        merchant: 'Netflix',
        paymentMethod: 'Credit Card',
        status: 'completed'
      },
      {
        id: '5',
        type: 'income',
        category: 'Salary',
        description: 'Monthly Salary',
        amount: 2500.00,
        timestamp: '3 days ago',
        merchant: 'Company XYZ',
        paymentMethod: 'Direct Deposit',
        status: 'completed'
      },
      {
        id: '6',
        type: 'expense',
        category: 'Food',
        description: 'Coffee Shop',
        amount: 12.75,
        timestamp: '3 days ago',
        merchant: 'Starbucks',
        paymentMethod: 'Mobile Pay',
        status: 'pending'
      }
    ];
  }

  onFilterChange() {
    // Handle filter change logic
  }

  getFilteredTransactions(): Transaction[] {
    switch (this.selectedFilter) {
      case 'income':
        return this.transactions.filter(t => t.type === 'income');
      case 'expense':
        return this.transactions.filter(t => t.type === 'expense');
      case 'pending':
        return this.transactions.filter(t => t.status === 'pending');
      default:
        return this.transactions;
    }
  }

  getTransactionClasses(transaction: Transaction): string {
    if (transaction.status === 'pending') {
      return 'border-yellow-200 bg-yellow-50';
    } else if (transaction.status === 'failed') {
      return 'border-red-200 bg-red-50';
    }
    return '';
  }

  getTransactionIconBg(transaction: Transaction): string {
    if (transaction.type === 'income') {
      return 'bg-green-100';
    }
    return 'bg-red-100';
  }

  getTransactionIconColor(transaction: Transaction): string {
    if (transaction.type === 'income') {
      return 'text-green-600';
    }
    return 'text-red-600';
  }

  getTransactionIconPath(transaction: Transaction): string {
    if (transaction.type === 'income') {
      return 'M7 11l5-5m0 0l5 5m-5-5v12';
    }
    return 'M17 13l-5 5m0 0l-5-5m5 5V6';
  }

  getStatusClasses(status: string): string {
    switch (status) {
      case 'completed':
        return 'bg-green-100 text-green-700';
      case 'pending':
        return 'bg-yellow-100 text-yellow-700';
      case 'failed':
        return 'bg-red-100 text-red-700';
      default:
        return 'bg-gray-100 text-gray-700';
    }
  }

  getAmountClasses(transaction: Transaction): string {
    if (transaction.type === 'income') {
      return 'text-green-600';
    }
    return 'text-red-600';
  }

  getTotalIncome(): number {
    return this.transactions
      .filter(t => t.type === 'income' && t.status === 'completed')
      .reduce((sum, t) => sum + t.amount, 0);
  }

  getTotalExpenses(): number {
    return this.transactions
      .filter(t => t.type === 'expense' && t.status === 'completed')
      .reduce((sum, t) => sum + t.amount, 0);
  }

  getNetFlow(): number {
    return this.getTotalIncome() - this.getTotalExpenses();
  }
}
