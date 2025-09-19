import { Component, EventEmitter, Input, Output, SimpleChanges } from '@angular/core';
import { PaymentMethodSelectorComponent } from "../../components/money-flow/payment-method-selector/payment-method-selector.component";
import { ModalOverlayComponent } from "../modal-overlay/modal-overlay.component";
import { Contact, PaymentMethod } from '../../models/money-flow.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-send-lent-money',
  imports: [PaymentMethodSelectorComponent, ModalOverlayComponent,CommonModule,FormsModule],
  templateUrl: './send-lent-money.component.html',
  styleUrl: './send-lent-money.component.css'
})
export class SendLentMoneyComponent {
 @Input() isOpen = false;
 @Input() recipient: Contact | null = null;
 @Input() paymentMethods: PaymentMethod[] = [];
 @Input() fixedAmount: number = 0;

 @Output() close = new EventEmitter<void>();
 @Output() continue = new EventEmitter<{ amount: number; note: string; paymentMethod: PaymentMethod }>();

  selectedPaymentMethod: PaymentMethod | null = null;
  note: string = '';
  amountError: string = '';

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['isOpen'] && this.isOpen) {
      this.resetForm();
    }
  }

  resetForm(): void {
    this.selectedPaymentMethod = null;
    this.note = '';
    this.amountError = '';
  }

  onPaymentMethodSelected(method: PaymentMethod): void {
    this.selectedPaymentMethod = method;
    this.validateFixedAmount();
  }

  onAddPaymentMethod(): void {
    // Implement your logic here
  }

  validateFixedAmount(): void {
    if (!this.selectedPaymentMethod) {
      this.amountError = 'Please select a payment method';
      return;
    }

    const availableBalance = this.selectedPaymentMethod.balance || 0;
    if (this.fixedAmount > availableBalance) {
      this.amountError = `Insufficient balance in ${this.selectedPaymentMethod.name}`;
    } else if (this.fixedAmount > 100000) {
      this.amountError = 'Amount exceeds daily limit of ₹1,00,000';
    } else {
      this.amountError = '';
    }
  }

  isFormValid(): boolean {
    return (
      this.fixedAmount > 0 &&
      !this.amountError &&
      this.selectedPaymentMethod !== null &&
      this.selectedPaymentMethod.isActive &&
      this.fixedAmount <= (this.selectedPaymentMethod.balance || 0)
    );
  }

  onClose(): void {
    this.close.emit();
  }

  onContinue(): void {
    if (this.isFormValid() && this.selectedPaymentMethod) {
      this.continue.emit({
        amount: this.fixedAmount,
        note: this.note.trim(),
        paymentMethod: this.selectedPaymentMethod
      });
      this.resetForm();
    }
  }

}
