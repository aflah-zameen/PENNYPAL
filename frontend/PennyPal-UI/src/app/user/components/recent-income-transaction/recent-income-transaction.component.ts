import { Component, Input } from '@angular/core';
import { TransactionHeaderComponent } from "../recent-income-transaction/transaction-header/transaction-header.component";
import { TransactionTableComponent } from "../recent-income-transaction/transaction-table/transaction-table.component";
import { TransactionItemComponent } from "../recent-income-transaction/transaction-item/transaction-item.component";
import { Transaction } from '../../models/transaction.model';
import { CommonModule } from '@angular/common';
import { IncomeModel } from '../../models/income.model';

@Component({
  selector: 'app-recent-income-transaction',
  imports: [TransactionHeaderComponent, TransactionTableComponent, TransactionItemComponent,CommonModule],
  templateUrl: './recent-income-transaction.component.html',
  styleUrl: './recent-income-transaction.component.css'
})
export class RecentIncomeTransactionComponent {
  @Input() transactions : IncomeModel[] =[];
}
