import { Component, EventEmitter, Input, Output } from '@angular/core';
import { UserCategoryResponse } from '../../models/user-category.model';
import { AddExpenseForm } from '../../models/expense.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalOverlayComponent } from "../modal-overlay/modal-overlay.component";
import { PaymentMethod, TransactionRequest } from '../../models/transaction.model';
type ExpenseFormErrors = {
  amount?: string;
  sourceDetail?: string;
  expense_date?: string;
  category?: string;
  paymentMethod?: string;
  general ?: string;
};

@Component({
  selector: 'app-add-expense-modal',
  imports: [CommonModule, FormsModule, ModalOverlayComponent],
  templateUrl: './add-expense-modal.component.html',
  styleUrl: './add-expense-modal.component.css'
})
export class AddExpenseModalComponent {
  
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
    paymentMethod: 'card',
    transactionType: 'EXPENSE',
    cardId : null
  };

  errors: ExpenseFormErrors = {};
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
      this.errors.expense_date = 'Date is required';
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
      setTimeout(() => {
      this.resetForm();
      this.close.emit();
    }, 500);
  }

  resetForm(): void {
    this.formData = {
      title: '',
      categoryId: null,
      amount: null,
      transactionDate: new Date().toISOString().split('T')[0], // Default to today
      description: '',
      paymentMethod: 'card',
      transactionType: 'EXPENSE',
      cardId: null
    };
    this.selectedPaymentMethod = null;
    this.isOpen = false;
    this.isSubmitting = false;
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
