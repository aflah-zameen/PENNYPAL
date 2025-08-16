import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Contact, PaymentMethod, Transaction } from '../../../models/money-flow.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalOverlayComponent } from "../../../modals/modal-overlay/modal-overlay.component";

@Component({
  selector: 'app-transaction-status-modal',
  imports: [CommonModule, FormsModule, ModalOverlayComponent],
  templateUrl: './transaction-status-modal.component.html',
  styleUrl: './transaction-status-modal.component.css'
})
export class TransactionStatusModalComponent {
  @Input() isOpen = false
  @Input() status: "processing" | "success" | "failed" = "processing"
  @Input() recipient: Contact | null = null
  @Input() transaction: Transaction | null = null
  @Input() failureReason = ""
  @Input() paymentMethod: PaymentMethod | null = null
  @Output() close = new EventEmitter<void>()
  @Output() done = new EventEmitter<void>()
  @Output() retry = new EventEmitter<void>()
  @Output() viewReceipt = new EventEmitter<Transaction>()

  ngOnInit() {
    // Auto-close processing state after demo delay
    if (this.status === "processing") {
      setTimeout(() => {
        // Simulate random success/failure for demo
        this.status = Math.random() > 0.2 ? "success" : "failed"
        if (this.status === "failed") {
          this.failureReason = "Invalid PIN entered"
        }
      }, 3000)
    }
  }

  onClose() {
    this.close.emit()
  }

  onDone() {
    this.done.emit()
  }

  onRetry() {
    this.retry.emit()
  }

  onViewReceipt() {
    if (this.transaction) {
      this.viewReceipt.emit(this.transaction)
    }
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
