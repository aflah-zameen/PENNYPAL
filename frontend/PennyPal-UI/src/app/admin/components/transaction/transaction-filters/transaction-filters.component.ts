import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { TransactionStatus, TransactionType } from '../../../models/transaction-management.model';
import { TransactionService } from '../../../services/transaction-management.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-transaction-filters',
  imports: [CommonModule,ReactiveFormsModule],
  templateUrl: './transaction-filters.component.html',
  styleUrl: './transaction-filters.component.css'
})
export class TransactionFiltersComponent {
   filterForm: FormGroup

  transactionTypes: TransactionType[] = ["Income", "Expense", "Transfer", "Wallet Recharge", "Lending", "Saving"]

  transactionStatuses: TransactionStatus[] = ["Success", "Pending", "Failed", "Reversed"]

  constructor(
    private fb: FormBuilder,
    private transactionService: TransactionService,
  ) {
    this.filterForm = this.fb.group({
      type: [""],
      status: [""],
      dateFrom: [""],
      dateTo: [""],
      userEmail: [""],
      minAmount: [""],
      maxAmount: [""],
    })
  }

  ngOnInit() {
    this.filterForm.valueChanges.subscribe((values) => {
      this.transactionService.updateFilters({
        types: values.type ? [values.type] : [],
        statuses: values.status ? [values.status] : [],
        dateRange: {
          from: values.dateFrom || null,
          to: values.dateTo || null,
        },
        amountRange: {
          min: values.minAmount ? Number(values.minAmount) : null,
          max: values.maxAmount ? Number(values.maxAmount) : null,
        },
        userEmail: values.userEmail || "",
      })
    })
  }

  clearFilters() {
    this.filterForm.reset()
  }
}
