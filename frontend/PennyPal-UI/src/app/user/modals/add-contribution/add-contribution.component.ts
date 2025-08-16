import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ContributionFormData } from '../../models/contribution-form-date.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PaymentMethod } from '../../models/transaction.model';

@Component({
  selector: 'app-add-contribution',
  imports: [CommonModule,FormsModule],
  templateUrl: './add-contribution.component.html',
  styleUrl: './add-contribution.component.css'
})
export class AddContributionComponent implements OnInit {
  @Input() isModalOpen = false
  @Input() goalId!: string
  @Input() targetAmount!: number
  @Input() currentAmount!: number
  @Input() goalTitle = ""
  @Input() goalColor = "#3B82F6"
  @Output() close = new EventEmitter<void>()
  @Output() contributionSubmit = new EventEmitter<ContributionFormData>()

  @Input() paymentMethods: PaymentMethod[] = [];
  isDropdownOpen: boolean = false;
  selectedPaymentMethod: PaymentMethod | null = null;

  errorMessage: string | null = null;

  formData!: ContributionFormData;

  ngOnInit(): void {
    this.formData = {
      goalId: this.goalId,
      cardId: '',
      amount: null,
      notes: '',
    };
  }

  // UPDATED: Added check for cardId
  get isSubmitDisabled(): boolean {
    return (
      !this.formData.amount ||
      this.formData.amount <= 0 ||
      !this.formData.cardId || // Checks if a card has been selected
      !!this.errorMessage
    );
  }

  onAmountChange(value: number): void {
    this.formData.amount = value;
    this.errorMessage = null;

    const total = this.currentAmount + value;
    if (total > this.targetAmount) {
      this.errorMessage =
        'Your contribution exceeds the target amount for this goal.';
    }
  }

  onClose(): void {
    this.formData = { amount: null, notes: '', goalId: this.goalId, cardId: '' };
    this.errorMessage = null;
    this.close.emit();
  }

  onSubmit(): void {
    const contribution = this.formData.amount ?? 0;
    const total = this.currentAmount + contribution;

    if (contribution <= 0 || !this.formData.cardId) return; // Added cardId check to prevent invalid submission

    if (total > this.targetAmount) {
      this.errorMessage =
        'Your contribution exceeds the target amount for this goal.';
      return;
    }

    this.contributionSubmit.emit({ ...this.formData });
    this.onClose();
  }

  toggleDropdown(): void {
    this.isDropdownOpen = !this.isDropdownOpen;
  }

  selectCard(card: PaymentMethod): void {
    this.selectedPaymentMethod = card;
    this.formData.cardId = card.id;
    this.isDropdownOpen = false; // Close the dropdown after selection
  }

  getLastFourDigits(cardNumber: string): string {
    return cardNumber ? cardNumber.slice(-4) : '';
  }

  onBackdropClick(event: Event): void {
    if (event.target === event.currentTarget) {
      this.close.emit();
    }
  }
}
