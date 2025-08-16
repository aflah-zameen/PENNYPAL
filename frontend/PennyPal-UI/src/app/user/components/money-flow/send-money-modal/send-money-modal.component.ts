import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Contact, PaymentMethod, UserBalance } from '../../../models/money-flow.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PaymentMethodSelectorComponent } from "../payment-method-selector/payment-method-selector.component";
import { ModalOverlayComponent } from "../../../modals/modal-overlay/modal-overlay.component";

@Component({
  selector: 'app-send-money-modal',
  imports: [CommonModule, FormsModule, PaymentMethodSelectorComponent, ModalOverlayComponent],
  templateUrl: './send-money-modal.component.html',
  styleUrl: './send-money-modal.component.css'
})
export class SendMoneyModalComponent {
  @Input() isOpen = false
  @Input() recipient: Contact | null = null
  @Input() paymentMethods: PaymentMethod[] = []
  @Output() close = new EventEmitter<void>()
  @Output() continue = new EventEmitter<{ amount: number; note: string; paymentMethod: PaymentMethod }>()

  selectedPaymentMethod: PaymentMethod | null = null

  amount = 0
  note = ""
  amountError = ""

  ngOnInit() {
    // Reset form when modal opens
    if (this.isOpen) {
      this.amount = 0
      this.note = ""
      this.amountError = ""
    }
  }

  onPaymentMethodSelected(method: PaymentMethod) {
    this.selectedPaymentMethod = method
    this.validateAmount() // Re-validate amount based on selected method
  }

  onAddPaymentMethod() {
    console.log("Add new payment method")
    // Implement add payment method functionality
  }

  isFormValid(): boolean {
    return (
      this.amount > 0 &&
      !this.amountError &&
      this.selectedPaymentMethod !== null &&
      this.selectedPaymentMethod.isActive &&
      this.amount <= (this.selectedPaymentMethod.balance || 0)
    )
  }

  validateAmount() {
    this.amountError = ""

    if (this.amount <= 0) {
      this.amountError = "Amount must be greater than 0"
      return
    }

    if (!this.selectedPaymentMethod) {
      this.amountError = "Please select a payment method"
      return
    }

    const availableBalance = this.selectedPaymentMethod.balance || 0
    if (this.amount > availableBalance) {
      this.amountError = `Insufficient balance in ${this.selectedPaymentMethod.name}`
      return
    }

    if (this.amount > 100000) {
      this.amountError = "Amount exceeds daily limit of ₹1,00,000"
      return
    }
  }


  onClose() {
    console.log("Closing send money modal");
    
    this.close.emit()
  }

  onContinue() {
    
    if (this.isFormValid() && this.selectedPaymentMethod) {      
      this.continue.emit({
        amount: this.amount,
        note: this.note.trim(),
        paymentMethod: this.selectedPaymentMethod,
      })
      this.amount = 0
      this.note = ""
      this.amountError = ""
    }
  }
}
