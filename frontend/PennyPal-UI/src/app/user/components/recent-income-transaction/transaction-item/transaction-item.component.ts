import { Component, Input } from '@angular/core';
import { Transaction } from '../../../models/transaction.model';
import { IncomeModel } from '../../../models/income.model';
import { CurrencyPipe } from '@angular/common';

@Component({
  selector: 'app-transaction-item',
  imports: [],
  templateUrl: './transaction-item.component.html',
  styleUrl: './transaction-item.component.css',
  providers :[CurrencyPipe]
})
export class TransactionItemComponent {
  @Input() incomes!: IncomeModel

  constructor(private currencyPipe : CurrencyPipe){}

  getFormattedContent(): string {
    if (typeof this.incomes.amount === 'number') {
      return this.currencyPipe.transform(this.incomes.amount, 'USD', 'symbol', '1.2-2') || '$0.00';
    }
    return this.incomes.amount;
  }
}
