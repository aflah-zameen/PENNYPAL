import { Component, ElementRef, EventEmitter, Input, Output, QueryList, ViewChildren } from '@angular/core';
import { ModalOverlayComponent } from "../modal-overlay/modal-overlay.component";
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-set-card-pin',
  imports: [ModalOverlayComponent,CommonModule,FormsModule],
  templateUrl: './set-card-pin.component.html',
  styleUrl: './set-card-pin.component.css'
})
export class SetCardPinComponent {
  @Input() isModalOpen = false;
  @Output() close = new EventEmitter<void>();
  @Output() onSubmitPin = new EventEmitter<string>();
  @ViewChildren('pinInputs') pinInputs!: QueryList<ElementRef>;
  @ViewChildren('confirmPinInputs') confirmPinInputs!: QueryList<ElementRef>;

  isSubmitting = false;
  pin: string[] = ['', '', '', ''];
  confirmPin: string[] = ['', '', '', ''];
  errors: { [key: string]: string } = {};

  onClose() {
    this.close.emit();
  }

  onPinInput(event: Event, index: number) {
    const input = event.target as HTMLInputElement;
    if (input.value.length === 1 && index < 3) {
      this.pinInputs.toArray()[index + 1].nativeElement.focus();
    }
    this.validatePin();
  }

  onPinKeydown(event: KeyboardEvent, index: number) {
    if (event.key === 'Backspace' && !this.pin[index] && index > 0) {
      this.pinInputs.toArray()[index - 1].nativeElement.focus();
    }
  }

  onConfirmPinInput(event: Event, index: number) {
    const input = event.target as HTMLInputElement;
    if (input.value.length === 1 && index < 3) {
      this.confirmPinInputs.toArray()[index + 1].nativeElement.focus();
    }
    this.validatePin();
  }

  onConfirmPinKeydown(event: KeyboardEvent, index: number) {
    if (event.key === 'Backspace' && !this.confirmPin[index] && index > 0) {
      this.confirmPinInputs.toArray()[index - 1].nativeElement.focus();
    }
  }

  validatePin() {
    this.errors = {};
    const pinStr = this.pin.join('');
    const confirmPinStr = this.confirmPin.join('');

    if (pinStr.length < 4) {
      this.errors['pin'] = 'Please enter a 4-digit PIN';
    } else if (!/^\d{4}$/.test(pinStr)) {
      this.errors['pin'] = 'PIN must be numeric';
    }

    if (confirmPinStr.length < 4) {
      this.errors['confirmPin'] = 'Please confirm your 4-digit PIN';
    } else if (!/^\d{4}$/.test(confirmPinStr)) {
      this.errors['confirmPin'] = 'PIN must be numeric';
    } else if (pinStr !== confirmPinStr) {
      this.errors['general'] = 'PINs do not match';
    }
  }

  isFormValid(): boolean {
    return this.pin.join('').length === 4 && this.confirmPin.join('').length === 4 && this.pin.join('') === this.confirmPin.join('') && !this.errors['pin'] && !this.errors['confirmPin'];
  }

  onSubmit() {
    this.validatePin();
    if (this.isFormValid()) {
      this.isSubmitting = true;
      // Simulate secure PIN submission (e.g., API call)
      setTimeout(() => {
        this.isSubmitting = false;
        this.onSubmitPin.emit(this.pin.join(''));
        this.onClose();
      }, 1000);
    }
  }
}
