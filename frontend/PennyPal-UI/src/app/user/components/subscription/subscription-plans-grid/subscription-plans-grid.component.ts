import { Component, EventEmitter, Input, Output, SimpleChanges } from '@angular/core';
import { PurchaseEvent, SubscriptionPlan } from '../../../models/subscription-plan-model';
import { CommonModule } from '@angular/common';
import { SubscriptionPlanCardComponent } from "../subscription-plan-card/subscription-plan-card.component";

@Component({
  selector: 'app-subscription-plans-grid',
  imports: [CommonModule, SubscriptionPlanCardComponent],
  templateUrl: './subscription-plans-grid.component.html',
  styleUrl: './subscription-plans-grid.component.css'
})
export class SubscriptionPlansGridComponent {
  @Input() plans: SubscriptionPlan[] = []
  @Output() purchase = new EventEmitter<PurchaseEvent>()

  onPlanPurchase(event: PurchaseEvent): void {
    this.purchase.emit(event)
  }
}
