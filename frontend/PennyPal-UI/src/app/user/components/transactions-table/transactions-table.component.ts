import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { Transaction } from '../../models/CreditCard.model';

@Component({
  selector: 'app-transactions-table',
  imports: [CommonModule],
  templateUrl: './transactions-table.component.html',
  styleUrl: './transactions-table.component.css'
})
export class TransactionsTableComponent {
@Input() transactions: Transaction[] = []
Math: Math = Math;
}
