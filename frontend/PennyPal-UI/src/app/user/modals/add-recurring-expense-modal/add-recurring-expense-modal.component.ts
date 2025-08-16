import { Component, EventEmitter, Input, Output } from '@angular/core';
import { RecurringFrequency } from '../../enums/income-frequency.enum';
import { UserCategoryResponse } from '../../models/user-category.model';
import { AddExpenseForm } from '../../models/expense.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalOverlayComponent } from "../modal-overlay/modal-overlay.component";
import { RecurringTransactionRequest } from '../../models/transaction.model';

@Component({
  selector: 'app-add-recurring-expense-modal',
  imports: [CommonModule, FormsModule, ModalOverlayComponent],
  templateUrl: './add-recurring-expense-modal.component.html',
  styleUrl: './add-recurring-expense-modal.component.css'
})
export class AddRecurringExpenseModalComponent {
  @Input() isModalOpen: boolean = false;
  @Input() categories: UserCategoryResponse[] = [];
  @Output() close = new EventEmitter<void>();
  @Output() submitForm = new EventEmitter<RecurringTransactionRequest>();

    formData :RecurringTransactionRequest = {
      title: '',
      amount: null,
      categoryId : null,
      frequency : RecurringFrequency.MONTHLY,
      endDate : '',
      startDate : '',
      description : '',
      transactionType: 'EXPENSE',
      cardId : null
    };
  
    errors: any = {};
    isSubmitting: boolean = false;
  
    ngOnInit() {
      // Set start default date to today
      const today = new Date().toISOString().split('T')[0];
      this.formData.startDate = today;
    }
  
    validateForm(): boolean {
  this.errors = {};

  if (!this.formData.amount || this.formData.amount <= 0) {
    this.errors.amount = 'Please enter a valid amount';
  }

  if (!this.formData.categoryId) {
    this.errors.category = 'Category is required';
  }

  if( !this.formData.title || this.formData.title.trim() === '') {
    this.errors.title = 'Details are required';
  }

  if (!this.formData.frequency) {
    this.errors.frequency = 'Frequency is required';
  }

  if (!this.formData.startDate) {
    this.errors.startDate = 'Start date is required';
  }

  if (!this.formData.endDate) {
    this.errors.endDate = 'End date is required';
  }
   
  if (this.formData.startDate && this.formData.endDate && this.formData.endDate < this.formData.startDate) {
    this.errors.endDateBeforeStart = 'End date cannot be before start date';
  }

  return Object.keys(this.errors).length === 0;
}
  
    onSubmit(){
      
      if (!this.validateForm()) {
        return;
      }
  
      this.isSubmitting = true;      
      this.submitForm.emit(this.formData);
      setTimeout(() => {
      this.resetForm();
      this.close.emit();
    }, 500);

    }
  
    resetForm(): void {
      this.formData = {
      title: '',
      amount: null,
      categoryId : null,
      frequency : RecurringFrequency.MONTHLY,
      endDate : '',
      startDate : '',
      description : '',
      transactionType: 'EXPENSE',
      cardId :null};
      this.errors = {};
      this.isSubmitting = false;
    }
  
    onBackdropClick(event: Event): void {
      if (event.target === event.currentTarget) {
        this.close.emit();
      }
    }
    
    onClose(): void {   
      this.errors = {};
      this.close.emit();
    }   
}
