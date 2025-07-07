import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output, output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AddIncomeModel } from '../../models/add-income.model';
import { IncomeModel } from '../../models/income.model';
import { ModalOverlayComponent } from "../modal-overlay/modal-overlay.component";
import { UserCategoryResponse } from '../../models/user-category.model';

@Component({
  selector: 'app-add-income',
  imports: [CommonModule, FormsModule, ModalOverlayComponent],
  templateUrl: './add-income.component.html',
  styleUrl: './add-income.component.css'
})
export class AddIncomeComponent {
  @Input() isModalOpen: boolean = false;
  @Input() categories: UserCategoryResponse[] = [];
  @Output() close = new EventEmitter<void>();
  @Output() submitForm = new EventEmitter<IncomeModel>();


  formData: IncomeModel = {
    source: null,
    amount: 0,
    income_date: new Date().toISOString().split('T')[0], // Default to today
    notes: '',
    recurrence: false, 
    frequency: '',
    description: '',
    status: ''
  };

  errors: any = {};
  isSubmitting: boolean = false;

  ngOnInit() {
    // Set default date to today
    const today = new Date().toISOString().split('T')[0];
    this.formData.income_date = today;
  }

  validateForm(): boolean {
    this.errors = {};

    if (!this.formData.source) {
      this.errors.source = 'Please select an income source';
    }

    if (!this.formData.amount || this.formData.amount <= 0) {
      this.errors.amount = 'Please enter a valid amount';
    }

    if (!this.formData.income_date) {
      this.errors.income_date = 'Please select a date';
    }

    return Object.keys(this.errors).length === 0;
  }

  async onSubmit(): Promise<void> {
    if (!this.validateForm()) {
      return;
    }

    this.isSubmitting = true;

    try {
      // Simulate API call
      await new Promise(resolve => setTimeout(resolve, 1000));
      

      const submissionData = {
        ...this.formData,
        description: this.formData.notes
      };
      
      this.submitForm.emit(submissionData);
      this.resetForm();
      this.close.emit();
    } catch (error) {
      this.errors.general = 'Failed to add income. Please try again.';
    } finally {
      this.isSubmitting = false;
    }
  }

  resetForm(): void {
    this.formData = {
      source: null,
      amount: 0,
      income_date: new Date().toISOString().split('T')[0], // Default to today
      notes: '',
      recurrence: false, 
      frequency: '',
    description: '',
    status: ''
  };
    this.errors = {};
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
