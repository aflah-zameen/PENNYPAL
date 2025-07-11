import { Component } from '@angular/core';
import { Transaction } from '../../models/transaction.model';
import { TransactionHeaderComponent } from "./transaction-header/transaction-header.component";
import { TransactionTableComponent } from "./transaction-table/transaction-table.component";
import { TransactionItemComponent } from "./transaction-item/transaction-item.component";
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-recent-transaction-card',
  imports: [TransactionHeaderComponent, TransactionTableComponent, TransactionItemComponent,CommonModule],
  templateUrl: './recent-transaction-card.component.html',
  styleUrl: './recent-transaction-card.component.css'
})
export class RecentTransactionCardComponent {
  transactions: Transaction[] = [
    {
      name: 'Netflix',
      category: 'Entertainment',
      date: 'Today',
      time: '12.00PM',
      amount: '-$50'
    },
    {
      name: 'Amazon',
      category: 'Shopping',
      date: '12 march 2025',
      time: '12.00PM',
      amount: '-$150'
    },
    {
      name: 'Robert Green',
      category: 'Payment Link',
      date: '10 march 2025',
      time: '12.00PM',
      amount: '+$1150'
    }
  ];
}
