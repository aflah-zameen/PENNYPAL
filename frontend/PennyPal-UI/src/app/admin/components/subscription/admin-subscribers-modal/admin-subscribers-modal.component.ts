import { Component, EventEmitter, Input, Output } from '@angular/core';
import { AdminSubscriptionPlan, PlanSubscriber } from '../../../models/admin-subscription-model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-admin-subscribers-modal',
  imports: [CommonModule],
  templateUrl: './admin-subscribers-modal.component.html',
  styleUrl: './admin-subscribers-modal.component.css'
})
export class AdminSubscribersModalComponent {
  @Input() isOpen = false
  @Input() plan: AdminSubscriptionPlan | null = null
  @Input() subscribers: PlanSubscriber[] = []
  @Input() loading = false
  @Output() close = new EventEmitter<void>()

  getInitials(name: string): string {
    return name
      .split(" ")
      .map((n) => n[0])
      .join("")
      .toUpperCase()
      .slice(0, 2)
  }

  onClose() {
    this.close.emit()
  }

  onBackdropClick(event: Event) {
    if (event.target === event.currentTarget) {
      this.onClose()
    }
  }
}
