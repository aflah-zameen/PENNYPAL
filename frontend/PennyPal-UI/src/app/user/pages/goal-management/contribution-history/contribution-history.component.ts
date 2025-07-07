import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Contribution } from '../../../models/goal.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-contribution-history',
  imports: [CommonModule],
  templateUrl: './contribution-history.component.html',
  styleUrl: './contribution-history.component.css'
})
export class ContributionHistoryComponent {
  @Input() contributions: Contribution[] = []
  @Output() deleteContribution = new EventEmitter<number>()

  trackByContribution(index: number, contribution: Contribution): number {
    return contribution.id
  }

  formatCurrency(amount: number): string {
    return new Intl.NumberFormat("en-US", {
      style: "currency",
      currency: "USD",
      minimumFractionDigits: 0,
      maximumFractionDigits: 0,
    }).format(amount)
  }

  formatDate(dateString: string): string {
    return new Date(dateString).toLocaleDateString("en-US", {
      month: "short",
      day: "numeric",
      year: "numeric",
    })
  }

  getTotalContributions(): string {
    const total = this.contributions.reduce((sum, contribution) => sum + contribution.amount, 0)
    return this.formatCurrency(total)
  }

  onDeleteContribution(contributionId: number): void {
    this.deleteContribution.emit(contributionId)
  }
}
