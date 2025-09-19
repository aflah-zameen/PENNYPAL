import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Contact, PaymentMethod, Transaction } from '../../../models/money-flow.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalOverlayComponent } from "../../../modals/modal-overlay/modal-overlay.component";

@Component({
  selector: 'app-transaction-receipt',
  imports: [CommonModule, FormsModule, ModalOverlayComponent],
  templateUrl: './transaction-receipt.component.html',
  styleUrl: './transaction-receipt.component.css'
})
export class TransactionReceiptComponent {
  @Input() isOpen = false
  @Input() transaction: Transaction | null = null
  @Input() recipient: Contact | null = null
  @Output() close = new EventEmitter<void>()
  @Input() paymentMethod: PaymentMethod | null = null

  onBackdropClick(event: Event) {
    if (event.target === event.currentTarget) {
      this.onClose()
    }
  }

  onClose() {
    this.close.emit()
  }

  onDownloadReceipt() {
    // Implement download functionality
  }

  onShareReceipt() {
    // Implement share functionality
  }

  getPaymentMethodBackground(): string {
    switch (this.paymentMethod?.type) {
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
