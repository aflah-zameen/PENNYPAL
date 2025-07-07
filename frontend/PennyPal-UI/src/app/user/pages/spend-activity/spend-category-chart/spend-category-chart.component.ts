import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CategorySpending } from '../../../models/spend-activity';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-spend-category-chart',
  imports: [CommonModule],
  templateUrl: './spend-category-chart.component.html',
  styleUrl: './spend-category-chart.component.css'
})
export class SpendCategoryChartComponent {
  @Input() categories: CategorySpending[] = []
  @Input() viewMode: "bar" | "pie" = "bar"
  @Output() viewModeChange = new EventEmitter<"bar" | "pie">()

  trackByCategory(index: number, category: CategorySpending): string {
    return category.category.id
  }

  setViewMode(mode: "bar" | "pie"): void {
    this.viewMode = mode
    this.viewModeChange.emit(mode)
  }

  getTotalTransactions(): number {
    return this.categories.reduce((sum, cat) => sum + cat.transactionCount, 0)
  }

  formatCurrency(amount: number): string {
    return new Intl.NumberFormat("en-US", {
      style: "currency",
      currency: "USD",
      minimumFractionDigits: 0,
      maximumFractionDigits: 0,
    }).format(amount)
  }
}
