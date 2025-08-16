import { Component } from '@angular/core';
import { Transaction } from '../../models/transaction-management.model';
import { ActivatedRoute, Router } from '@angular/router';
import { TransactionFiltersComponent } from "../../components/transaction/transaction-filters/transaction-filters.component";
import { TransactionListComponent } from "../../components/transaction/transaction-list/transaction-list.component";
import { FlaggedTransactionComponent } from "../../components/transaction/flagged-transaction/flagged-transaction.component";
import { TransactionDetailComponent } from "../../components/transaction/transaction-detail/transaction-detail.component";
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-transaction-management',
  imports: [TransactionFiltersComponent, TransactionListComponent, FlaggedTransactionComponent, TransactionDetailComponent,CommonModule],
  templateUrl: './transaction-management.component.html',
  styleUrl: './transaction-management.component.css'
})
export class TransactionManagementComponent {
  activeTab: "all" | "flagged" = "all"
  selectedTransaction: Transaction | null = null
  isDetailOpen = false
  totalTransactions = 5
  flaggedCount = 2

  constructor(
    private router: Router,
    private route: ActivatedRoute,
  ) {}

  ngOnInit() {
    // Check route to determine active tab
    this.route.url.subscribe((segments) => {
      if (segments.some((segment) => segment.path === "flagged")) {
        this.activeTab = "flagged"
      } else {
        this.activeTab = "all"
      }
    })
  }

  setActiveTab(tab: "all" | "flagged") {
    this.activeTab = tab

    // Update URL
    if (tab === "flagged") {
      this.router.navigate(["/admin/transaction-management/flagged"])
    } else {
      this.router.navigate(["/admin/transaction-management"])
    }
  }

  openTransactionDetail(transaction: Transaction) {
    this.selectedTransaction = transaction
    this.isDetailOpen = true
  }

  closeTransactionDetail() {
    this.isDetailOpen = false
    this.selectedTransaction = null
  }
}
