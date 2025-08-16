import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { PaymentMethod } from '../../../models/money-flow.model';

@Component({
  selector: 'app-payment-method-selector',
  imports: [CommonModule],
  templateUrl: './payment-method-selector.component.html',
  styleUrl: './payment-method-selector.component.css'
})
export class PaymentMethodSelectorComponent {
  @Input() paymentMethods: PaymentMethod[] = []
  @Input() selectedMethodId = ""
  @Output() methodSelected = new EventEmitter<PaymentMethod>()
  @Output() addPaymentMethod = new EventEmitter<void>()

  onSelectMethod(method: PaymentMethod) {
    if (method.isActive) {
      this.methodSelected.emit(method)
    }
  }

  onAddPaymentMethod() {
    this.addPaymentMethod.emit()
  }

  getIconBackground(type: string): string {
    switch (type) {
      case "wallet":
        return "bg-gradient-to-r from-blue-500 to-blue-600"
      case "card":
        return "bg-gradient-to-r from-purple-500 to-purple-600"
      case "bank":
        return "bg-gradient-to-r from-green-500 to-green-600"
      default:
        return "bg-gradient-to-r from-gray-500 to-gray-600"
    }
  }
}
