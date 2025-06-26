import { CommonModule } from '@angular/common';
import { Component, Input,ElementRef, EventEmitter, Output, QueryList, ViewChildren, AfterViewInit } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-otp-input-field',
  imports: [CommonModule,FormsModule],
  templateUrl: './otp-input-field.component.html',
  styleUrl: './otp-input-field.component.css'
})
export class OtpInputFieldComponent {
  @ViewChildren('otpInput') inputs!: QueryList<ElementRef<HTMLInputElement>>;
  @Output() otpChange = new EventEmitter<string>();
  
  otp: string[] = ['', '', '', '', '', ''];
  error: boolean = false;

  onInput(event: Event, index: number): void {
    const input = event.target as HTMLInputElement;
    const value = input.value;

    // Only allow single digit
    if (value && !/^[0-9]$/.test(value)) {
      input.value = '';
      this.otp[index] = '';
      return;
    }

    // Move to next input if a digit is entered
    if (value && index < 5) {
      this.inputs.toArray()[index + 1].nativeElement.focus();
    }

    this.emitOtp();
  }

  onKeyDown(event: KeyboardEvent, index: number): void {
    const input = event.target as HTMLInputElement;

    // Handle backspace
    if (event.key === 'Backspace' && !input.value && index > 0) {
      this.inputs.toArray()[index - 1].nativeElement.focus();
    }
  }

  onPaste(event: ClipboardEvent): void {
    event.preventDefault();
    const pastedData = event.clipboardData?.getData('text') || '';
    
    // Validate pasted data
    if (/^\d{6}$/.test(pastedData)) {
      this.otp = pastedData.split('');
      this.error = false;
      this.emitOtp();
      // Focus last input
      this.inputs.toArray()[5].nativeElement.focus();
    } else {
      this.error = true;
    }
  }

  clearOtp(){
    this.otp = Array(6).fill('');
    this.emitOtp();
  }

  private emitOtp(): void {
    const otpValue = this.otp.join('');
    if (otpValue.length === 6 && /^\d{6}$/.test(otpValue)) {
      this.otpChange.emit(otpValue);
      this.error = false;
    } else {
      this.error = true;
    }
  }
}
