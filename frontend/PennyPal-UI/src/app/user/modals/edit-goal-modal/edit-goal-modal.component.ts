import { Component, EventEmitter, Input, Output, SimpleChanges } from '@angular/core';
import { Goal, GoalFormData, GoalFormErrors } from '../../models/goal.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalOverlayComponent } from "../modal-overlay/modal-overlay.component";

@Component({
  selector: 'app-edit-goal-modal',
  imports: [CommonModule, FormsModule, ModalOverlayComponent],
  templateUrl: './edit-goal-modal.component.html',
  styleUrl: './edit-goal-modal.component.css'
})
export class EditGoalModalComponent {
  @Input() isOpen: boolean = false;
  @Input() goal: Goal | null = null;
  @Output() close = new EventEmitter<void>();
  @Output() goalUpdated = new EventEmitter<{ id: number; data: GoalFormData }>();

  formData: GoalFormData = {
    name: '',
    amount: 0,
    startDate: '',
    endDate: '',
    description: '',
    category: 'other'
  };

  errors: GoalFormErrors = {};
  isSubmitting: boolean = false;

  ngOnChanges(changes: SimpleChanges) {
    if (changes['goal'] && this.goal) {
      this.populateForm();
    }
  }

  populateForm(): void {
    if (this.goal) {
      this.formData = {
        name: this.goal.title,
        amount: this.goal.targetAmount,
        startDate: this.goal.startDate,
        endDate: this.goal.endDate,
        description: '', // Add description field to Goal interface if needed
        category: this.goal.category
      };
    }
  }

  getCurrentProgress(): number {
    if (!this.goal || !this.formData.amount) return 0;
    return Math.round((this.goal.currentAmount / this.formData.amount) * 100);
  }

  formatCurrency(amount: number): string {
    return new Intl.NumberFormat('en-US', {
      style: 'currency',
      currency: 'USD',
      minimumFractionDigits: 0,
      maximumFractionDigits: 0
    }).format(amount);
  }

  validateForm(): boolean {
    this.errors = {};

    if (!this.formData.name.trim()) {
      this.errors.name = 'Goal name is required';
    }

    if (!this.formData.amount || this.formData.amount <= 0) {
      this.errors.amount = 'Please enter a valid target amount';
    }

    if (!this.formData.startDate) {
      this.errors.startDate = 'Start date is required';
    }

    if (!this.formData.endDate) {
      this.errors.endDate = 'Target date is required';
    }

    if (this.formData.startDate && this.formData.endDate) {
      const startDate = new Date(this.formData.startDate);
      const endDate = new Date(this.formData.endDate);
      
      if (endDate <= startDate) {
        this.errors.endDate = 'Target date must be after start date';
      }
    }

    return Object.keys(this.errors).length === 0;
  }

  async onSubmit(): Promise<void> {
    if (!this.validateForm() || !this.goal) {
      return;
    }

    this.isSubmitting = true;

    try {
      // Simulate API call
      await new Promise(resolve => setTimeout(resolve, 1000));
      
      this.goalUpdated.emit({ 
        id: this.goal.id, 
        data: { ...this.formData } 
      });
      this.onClose();
    } catch (error) {
      this.errors.general = 'Failed to update goal. Please try again.';
    } finally {
      this.isSubmitting = false;
    }
  }

  onClose(): void {
    this.errors = {};
    this.close.emit();
  }
}
