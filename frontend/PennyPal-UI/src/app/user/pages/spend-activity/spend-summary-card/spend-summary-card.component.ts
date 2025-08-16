import { Component, Input } from '@angular/core';
import { SpendingSummary } from '../../../models/spend-activity';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-spend-summary-card',
  imports: [CommonModule],
  templateUrl: './spend-summary-card.component.html',
  styleUrl: './spend-summary-card.component.css'
})
export class SpendSummaryCardComponent {
  @Input() summary!: SpendingSummary | null

  formatCurrency(amount: number): string {
    return new Intl.NumberFormat("en-US", {
      style: "currency",
      currency: "USD",
      minimumFractionDigits: 0,
      maximumFractionDigits: 0,
    }).format(amount)
  }
}
