import { CommonModule } from '@angular/common';
import { Component,EventEmitter,Input,Output } from '@angular/core';
import { AbstractControl, FormControl, FormsModule, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-phone-field',
  imports: [ReactiveFormsModule,CommonModule],
  templateUrl: './phone-field.component.html',
  styleUrl: './phone-field.component.css'
})
export class PhoneFieldComponent {
  @Input() value = '';
  @Input() required = false;
  @Input() control?: FormControl;
  @Output() valueChange = new EventEmitter<string>();

  onInputChange(value: string) {
    this.valueChange.emit(value);
  }

  get errorMessage(): string {
    if (!this.control || !this.control.errors || !this.control.touched) return '';

    const errors = this.control.errors;
    if (errors['required']) return 'Phone number is required';
    if (errors['pattern']) return 'Please enter a valid phone number';

    return 'Invalid phone number';
  }
}
