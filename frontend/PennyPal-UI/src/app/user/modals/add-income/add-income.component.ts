import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output, output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ModalOverlayComponent } from "../modal-overlay/modal-overlay.component";
import { UserCategoryResponse } from '../../models/user-category.model';
import { IncomeRequestModel } from '../../models/income.model';
import { PaymentMethod, TransactionRequest } from '../../models/transaction.model';

@Component({
  selector: 'app-add-income',
  imports: [CommonModule, FormsModule, ModalOverlayComponent],
  templateUrl: './add-income.component.html',
  styleUrl: './add-income.component.css'
})
export class AddIncomeComponent {
  @Input() isModalOpen: boolean = false;  
  @Input() categories: UserCategoryResponse[] = [];
  @Input() paymentMethods: PaymentMethod[] = [];
  @Output() close = new EventEmitter<void>();
  @Output() submitForm = new EventEmitter<TransactionRequest>();

  isOpen: boolean = false;
  selectedPaymentMethod: PaymentMethod | null = null;


  formData: TransactionRequest = {
    title: '',
    amount: null,
    transactionDate: new Date().toISOString().split('T')[0], // Default to today
    description: '',
    categoryId : null,
    transactionType: 'INCOME',
    paymentMethod: 'card',
    cardId:null
  };

  errors: any = {};
  isSubmitting: boolean = false;

  ngOnInit() {
    // Set default date to today
    const today = new Date().toISOString().split('T')[0];
    this.formData.transactionDate = today;
  }

  toggleDropdown(): void {
    this.isOpen = !this.isOpen;
  }

  selectCard(card: PaymentMethod): void {
    this.selectedPaymentMethod = card;
    this.formData.cardId = card.id; 
    this.isOpen = false; // Close the dropdown after selection
  }

  validateForm(): boolean {
    this.errors = {};

    if (!this.formData.amount || this.formData.amount <= 0) {
      this.errors.amount = 'Please enter a valid amount';
    }

    if (!this.formData.title || this.formData.title.trim() === '') {
      this.errors.sourceDetail = 'Source is required';
    }

    if (!this.formData.transactionDate) {
      this.errors.income_date = 'Date is required';
    }

    if (!this.formData.categoryId) {
      this.errors.category = 'Category is required';
    }

     if (!this.formData.cardId) {
      this.errors.paymentMethod = 'Payment Method is required';
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
      transactionDate: new Date().toISOString().split('T')[0], // Default to today
      description: '',
      transactionType: 'INCOME',
      paymentMethod: 'card',
      cardId: null
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
   getLastFourDigits(cardNumber: string): string {
    return cardNumber ? cardNumber.slice(-4) : '';
  } 
}
