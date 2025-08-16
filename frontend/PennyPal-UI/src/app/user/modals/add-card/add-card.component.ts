import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ModalOverlayComponent } from "../modal-overlay/modal-overlay.component";
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { CreditCardForm } from '../../models/CreditCard.model';

@Component({
  selector: 'app-add-card-modal',
  imports: [ModalOverlayComponent,FormsModule,CommonModule],
  templateUrl: './add-card.component.html',
  styleUrl: './add-card.component.css'
})
export class AddCardComponentModal {
  @Input() isModalOpen = false;
  @Output() close = new EventEmitter<void>();
  @Output() submitCard = new EventEmitter<Omit<CreditCardForm,'pin'>>();

  isSubmitting = false;
  cardNumberRegex = /^(\d{4}[\s-]?){3}\d{4}$/;
  expiryRegex = /^(0[1-9]|1[0-2])\/\d{2}$/;



  formData: Omit<CreditCardForm,'pin'> = {
    number: '',
    name: '',
    holder: '',
    expiry: '',
    type: null,
    gradient : '',
    balance: null,
    notes: '',
  };

  errors: { [key: string]: string } = {};

  onClose() {
    this.close.emit();
  }

  onSubmit() {
    this.errors = {};
    this.isSubmitting = true;

    if (!this.validateForm()) {
      this.isSubmitting = false;
      return;
    }

    const generatedGradient = this.generateUniqueGradient();
    this.formData = {...this.formData,gradient : generatedGradient}
    this.submitCard.emit(this.formData);
    this.isSubmitting = false;
    this.onClose();
  }

  validateForm(): boolean {
    let valid = true;
    if (!this.expiryRegex.test(this.formData.expiry.trim())) {
      this.errors['expiry'] = 'Expiry must be in MM/YY format';
      valid = false;
    }
    if (!this.cardNumberRegex.test(this.formData.number.trim())) {
      this.errors['number'] = 'Card number must be 16 digits, optionally grouped (e.g., "1234 5678 9012 3456")';
      valid = false;
    }
    if (!this.formData.number?.trim()) {
      this.errors['number'] = 'Card number is required';
      valid = false;
    }
    if (!this.formData.holder?.trim()) {
      this.errors['holder'] = 'Holder name is required';
      valid = false;
    }
    if (!this.formData.name?.trim()) {
      this.errors['name'] = 'Card name is required';
      valid = false;
    }
    if (!this.formData.expiry?.trim()) {
      this.errors['expiry'] = 'Expiry date is required';
      valid = false;
    }
    if (!this.formData.type) {
      this.errors['type'] = 'Card type is required';
      valid = false;
    }
    if (this.formData.balance === null || this.formData.balance < 0) {
      this.errors['balance'] = 'Balance must be a non-negative number';
      valid = false;
    }
    return valid;
  }

  generateUniqueGradient(): string {
    const presets = [
      "bg-gradient-to-br from-purple-500 via-pink-500 to-orange-400",
      "bg-gradient-to-br from-blue-500 via-purple-500 to-pink-400",
      "bg-gradient-to-br from-green-500 via-teal-400 to-blue-500",
      "bg-gradient-to-br from-yellow-400 via-orange-500 to-red-500",
      "bg-gradient-to-br from-indigo-500 via-purple-500 to-pink-500",
      "bg-gradient-to-br from-cyan-500 via-sky-500 to-indigo-500",
      "bg-gradient-to-br from-fuchsia-500 via-purple-400 to-rose-400",
      "bg-gradient-to-br from-lime-400 via-green-500 to-emerald-500",
    ];
    return presets[Math.floor(Math.random() * presets.length)];
  }
}



