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
    if (errors['mismatch']) return 'Passwords do not match';
    if(errors['pattern']) return 'Password must be at least 8 characters long, and include an uppercase letter, a lowercase letter, a number, and a special character.'

    return 'Invalid input';
  }

  onInputChange(value: string) {
    this.onChange.emit(value);
  }
}
