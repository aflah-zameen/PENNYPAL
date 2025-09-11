import { Component, EventEmitter, Input, Output } from '@angular/core';
import { PurchaseEvent, SubscriptionPlan } from '../../../models/subscription-plan-model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-subscription-plan-card',
  imports: [CommonModule],
  templateUrl: './subscription-plan-card.component.html',
  styleUrl: './subscription-plan-card.component.css'
})
export class SubscriptionPlanCardComponent {
  @Input() plan!: SubscriptionPlan
  @Output() purchase = new EventEmitter<PurchaseEvent>()

  formatCurrency(amount: number): string {
    return new Intl.NumberFormat("en-US", {
      style: "currency",
      currency: "USD",
    }).format(amount)
  }

  getDurationText(durationDays: number): string {
  if (durationDays === 30) return "per month";
  if (durationDays === 90) return "per quarter";
  if (durationDays === 180) return "per 6 months";
  if (durationDays === 365) return "per year";

  const months = Math.floor(durationDays / 30);
  const remainingDays = durationDays % 30;

  let label = "";

  if (months > 0) {
    label += `per ${months} month${months > 1 ? "s" : ""}`;
  }

  if (remainingDays > 0) {
    label += months > 0 ? ` + ${remainingDays} day${remainingDays > 1 ? "s" : ""}` : `per ${remainingDays} day${remainingDays > 1 ? "s" : ""}`;
  }

  return label || "custom duration";
}


  calculateSavings(original: number, current: number): number {
    return Math.round(((original - current) / original) * 100)
  }

  getButtonClasses(): string {
    if (this.plan.isPopular) {
      return "bg-gradient-to-r from-purple-400 via-purple-500 to-purple-600 hover:from-purple-500 hover:via-purple-600 hover:to-purple-700 shadow-lg hover:shadow-xl"
    }
    return "bg-gradient-to-r from-blue-600 via-purple-600 to-blue-800 hover:from-blue-700 hover:via-purple-700 hover:to-blue-900 shadow-lg hover:shadow-xl"
  }

  onPurchase(): void {
    this.purchase.emit({
      planId: this.plan.id,
      plan: this.plan,
    })
  }
}
