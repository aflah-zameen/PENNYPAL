import { CommonModule } from '@angular/common';
import { Component, EventEmitter } from '@angular/core';
import { Input,Output } from '@angular/core';
import { AbstractControl, FormControl, FormsModule, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-input-field',
  imports: [ReactiveFormsModule,CommonModule],
  templateUrl: './input-field.component.html',
  styleUrl: './input-field.component.css'
})
export class InputFieldComponent {
  @Input() label = '';
  @Input() placeholder = '';
  @Input() type = 'text';
  @Input() disabled = false;
  @Input() required = false;
  @Input() value = '';
  @Input() control?: FormControl;
  @Output() onChange = new EventEmitter<string>();

  get errorMessage(): string {
    
    if (!this.control || !this.control.errors || !this.control.touched) return '';

    const errors = this.control.errors;
    
   if (errors['required']) return 'This field is required';
    if (errors['email']) return 'Please enter a valid email address';
    if (errors['minlength']) return `Minimum ${errors['minlength'].requiredLength} characters required`;
    if (errors['maxlength']) return `Maximum ${errors['maxlength'].requiredLength} characters allowed`;
    if (errors['mismatch']) return 'Passwords do not match';
    if (errors['pattern']) return 'Password must be at least 8 characters long, and include an uppercase letter, a lowercase letter, a number, and a special character.';
    if (errors['custom']) return errors['custom']; 

    return 'Invalid input';
  }

  onInputChange(event: Event): void {
  const input = event.target as HTMLInputElement;
  const value = input?.value ?? '';

  if (this.control) {
    this.control.setValue(value);
    this.control.markAsTouched();
  }

  this.onChange.emit(value);
}

}
