import { Component, Input } from '@angular/core';
import { SubscriptionAnalytics } from '../../models/admin-analytics-model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-analytics-summary-cards',
  imports: [CommonModule],
  templateUrl: './analytics-summary-cards.component.html',
  styleUrl: './analytics-summary-cards.component.css'
})
export class AnalyticsSummaryCardsComponent {
  @Input() analytics: SubscriptionAnalytics | null = null
}
