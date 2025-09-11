import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-subscription-plans-header',
  imports: [CommonModule],
  templateUrl: './subscription-plans-header.component.html',
  styleUrl: './subscription-plans-header.component.css'
})
export class SubscriptionPlansHeaderComponent {
  @Input() showBillingToggle = false
  @Input() selectedBilling: "monthly" | "annual" = "monthly"

  onBillingChange(billing: "monthly" | "annual"): void {
    // Emit event if needed for parent component
    console.log("Billing changed to:", billing)
  }
}
