import { CommonModule } from '@angular/common';
import { Component, ElementRef, EventEmitter, Input, OnInit, Output, QueryList, ViewChild, ViewChildren } from '@angular/core';
import { Contact, PaymentMethod } from '../../../models/money-flow.model';
import { FormsModule } from '@angular/forms';
import { ModalOverlayComponent } from "../../../modals/modal-overlay/modal-overlay.component";

@Component({
  selector: 'app-pin-confirmation-modal',
  imports: [CommonModule, FormsModule, ModalOverlayComponent],
  templateUrl: './pin-confirmation-modal.component.html',
  styleUrl: './pin-confirmation-modal.component.css'
})
export class PinConfirmationModalComponent implements OnInit {
  @Input() isOpen = false
  @Input() recipient: Contact | null = null
  @Input() amount = 0
  @Input() paymentMethod: PaymentMethod | null = null
  @Output() close = new EventEmitter<void>()
  @Output() confirm = new EventEmitter<string>()
  @ViewChildren('pinInput') pinInputs!: QueryList<ElementRef>;
  pinDigits: string[] = ['', '', '', ''];
  pinError = '';
  isProcessing = false;

  ngOnInit() {
    if (this.isOpen) {
      this.resetPin();
    }
  }

  resetPin() {
    this.pinDigits = ['', '', '', ''];
    this.pinError = '';
    this.isProcessing = false;
  }

  onPinInput(index: number, event: Event) {
  const input = event.target as HTMLInputElement;
  const value = input.value;

  if (value && !/^[0-9]$/.test(value)) {
    input.value = '';
    this.pinDigits[index] = '';
    return;
  }

  this.pinDigits[index] = value;

  // Delay focus to next input to ensure pinInputs is populated
  if (value && index < this.pinDigits.length - 1) {
    setTimeout(() => {
      const nextInput = this.pinInputs.toArray()[index + 1];
      nextInput?.nativeElement?.focus();
    }, 0);
  }
}




  onKeyDown(index: number, event: KeyboardEvent) {
  const input = event.target as HTMLInputElement;
  if (event.key === 'Backspace' && !input.value && index > 0) {
    setTimeout(() => {
      const prevInput = this.pinInputs.toArray()[index - 1];
      prevInput?.nativeElement?.focus();
    }, 0);
  }
}

  

  isComplete(): boolean {
    return this.pinDigits.every((digit) => digit !== '');
  }

  onBackdropClick(event: Event) {
    if (event.target === event.currentTarget) {
      this.onClose();
    }
  }

  onClose() {
    this.close.emit();
  }

  onConfirm() {
    if (this.isComplete()) {
      this.isProcessing = true;
      const pin = this.pinDigits.join('');
      setTimeout(() => {
        this.confirm.emit(pin);
        this.resetPin();
        this.isProcessing = false;
      }, 1000);
    }
  }
}
