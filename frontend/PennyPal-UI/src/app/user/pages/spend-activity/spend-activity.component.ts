import { Component } from '@angular/core';
import { CategorySpending, SpendingCategory, SpendingSummary, Transaction } from '../../models/spend-activity';
import { SpendingDataService } from '../../services/spending-data.service';
import { CommonModule } from '@angular/common';
import { SpendSummaryCardComponent } from "./spend-summary-card/spend-summary-card.component";
import { SpendCategoryChartComponent } from "./spend-category-chart/spend-category-chart.component";
import { RecentSpendTransactionComponent } from "./recent-spend-transaction/recent-spend-transaction.component";

@Component({
  selector: 'app-spend-activity',
  imports: [CommonModule, SpendSummaryCardComponent, SpendCategoryChartComponent, RecentSpendTransactionComponent],
  templateUrl: './spend-activity.component.html',
  styleUrl: './spend-activity.component.css'
})
export class SpendActivityComponent {
  isLoading = true
  transactions: Transaction[] = []
  categories: SpendingCategory[] = []
  spendingSummary!: SpendingSummary
  categorySpending: CategorySpending[] = []
  chartViewMode: "bar" | "pie" = "bar"

  constructor(private spendingDataService: SpendingDataService) {}

  ngOnInit() {
    this.loadData()
  }

  private async loadData() {
    try {
      // Simulate loading delay for better UX
      await new Promise((resolve) => setTimeout(resolve, 1000))

      this.transactions = this.spendingDataService.getTransactions()
      this.categories = this.spendingDataService.getCategories()
      this.spendingSummary = this.spendingDataService.calculateSpendingSummary(this.transactions)
      this.categorySpending = this.spendingDataService.calculateCategorySpending(
        this.transactions.filter((t) => {
          const date = new Date(t.date)
          const currentMonth = new Date().getMonth()
          const currentYear = new Date().getFullYear()
          return date.getMonth() === currentMonth && date.getFullYear() === currentYear
        }),
      )

      this.isLoading = false
    } catch (error) {
      console.error("Error loading spending data:", error)
      this.isLoading = false
    }
  }

  onChartViewModeChange(mode: "bar" | "pie"): void {
    this.chartViewMode = mode
  }
}
