import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Goal } from '../../../models/goal.model';
import { CommonModule } from '@angular/common';
import { AddContributionComponent } from "../../../modals/add-contribution/add-contribution.component";
import { ContributionHistoryComponent } from "../contribution-history/contribution-history.component";
import { ContributionFormData } from '../../../models/contribution-form-date.model';

@Component({
  selector: 'app-goal-card',
  imports: [CommonModule, AddContributionComponent, ContributionHistoryComponent],
  templateUrl: './goal-card.component.html',
  styleUrl: './goal-card.component.css'
})
export class GoalCardComponent {
  @Input() goal!: Goal;
  @Output() edit = new EventEmitter<Goal>();
  @Output() delete = new EventEmitter<number>();
  @Output() addContribution = new EventEmitter<ContributionFormData>()
  @Output() deleteContribution = new EventEmitter<number>()

  isContributionModalOpen = false

  get progressPercentage(): number {
    return Math.round((this.goal.currentAmount / this.goal.targetAmount) * 100);
  }

  get daysRemaining(): number {
    const endDate = new Date(this.goal.endDate);
    const today = new Date();
    const diffTime = endDate.getTime() - today.getTime();
    return Math.ceil(diffTime / (1000 * 60 * 60 * 24));
  }

  get daysRemainingClass(): string {
    const days = this.daysRemaining;
    if (days < 30) return 'text-red-600';
    if (days < 90) return 'text-yellow-600';
    return 'text-green-600';
  }

  formatCurrency(amount: number): string {
    return new Intl.NumberFormat('en-US', {
      style: 'currency',
      currency: 'USD',
      minimumFractionDigits: 0,
      maximumFractionDigits: 0
    }).format(amount);
  }

  formatDate(dateString: string): string {
    return new Date(dateString).toLocaleDateString('en-US', {
      day: '2-digit',
      month: 'short',
      year: 'numeric'
    });
  }

  onEdit(): void {
    this.edit.emit(this.goal);
  }

  onDelete(): void {
    this.delete.emit(this.goal.id);
  }

  openContributionModal(): void {
    this.isContributionModalOpen = true
  }

  closeContributionModal(): void {
    this.isContributionModalOpen = false
  }

  onAddContribution(formData: ContributionFormData): void {
    this.addContribution.emit(formData)
    this.closeContributionModal()
  }

  onDeleteContribution(contributionId: number): void {
    this.deleteContribution.emit(contributionId)
  }
}
