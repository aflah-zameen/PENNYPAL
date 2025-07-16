import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output, output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ModalOverlayComponent } from "../modal-overlay/modal-overlay.component";
import { UserCategoryResponse } from '../../models/user-category.model';
import { IncomeRequestModel } from '../../models/income.model';

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
  @Output() submitForm = new EventEmitter<IncomeRequestModel>();


  formData: IncomeRequestModel = {
    title: '',
    amount: null,
    incomeDate: new Date().toISOString().split('T')[0], // Default to today
    description: '',
    categoryId : null,
    isRecurring : false
  };

  errors: any = {};
  isSubmitting: boolean = false;

  ngOnInit() {
    // Set default date to today
    const today = new Date().toISOString().split('T')[0];
    this.formData.incomeDate = today;
  }

  validateForm(): boolean {
    this.errors = {};

    if (!this.formData.amount || this.formData.amount <= 0) {
      this.errors.amount = 'Please enter a valid amount';
    }

    if (!this.formData.title || this.formData.title.trim() === '') {
      this.errors.sourceDetail = 'Source is required';
    }

    if (!this.formData.incomeDate) {
      this.errors.income_date = 'Date is required';
    }

    if (!this.formData.categoryId) {
      this.errors.category = 'Category is required';
    }

    return Object.keys(this.errors).length === 0;
  }

  onSubmit(){
    if (!this.validateForm()) {
      return;
    }

    this.isSubmitting = true;
    this.submitForm.emit(this.formData);
    this.resetForm();
    this.close.emit();
  }

  resetForm(): void {
    this.formData = {
      title: '',
      categoryId: null,
      amount: null,
      incomeDate: new Date().toISOString().split('T')[0], // Default to today
      description: '',
      isRecurring : false
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
