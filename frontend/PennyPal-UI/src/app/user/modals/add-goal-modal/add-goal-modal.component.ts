import { Component, EventEmitter, Input, Output } from '@angular/core';
import { GoalFormData, GoalFormErrors } from '../../models/goal.model';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ModalOverlayComponent } from "../modal-overlay/modal-overlay.component";

@Component({
  selector: 'app-add-goal-modal',
  imports: [FormsModule, CommonModule, ModalOverlayComponent],
  templateUrl: './add-goal-modal.component.html',
  styleUrl: './add-goal-modal.component.css'
})
export class AddGoalModalComponent {
  @Input() isOpen: boolean = false;
  @Output() close = new EventEmitter<void>();
  @Output() goalAdded = new EventEmitter<GoalFormData>();

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

  ngOnInit() {
    // Set default start date to today
    const today = new Date().toISOString().split('T')[0];
    this.formData.startDate = today;
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
    if (!this.validateForm()) {
      return;
    }

    this.isSubmitting = true;

    try {
      // Simulate API call
      await new Promise(resolve => setTimeout(resolve, 1000));
      
      this.goalAdded.emit({ ...this.formData });
      this.resetForm();
      this.onClose();
    } catch (error) {
      this.errors.general = 'Failed to create goal. Please try again.';
    } finally {
      this.isSubmitting = false;
    }
  }

  resetForm(): void {
    this.formData = {
      name: '',
      amount: 0,
      startDate: new Date().toISOString().split('T')[0],
      endDate: '',
      description: '',
      category: 'other'
    };
    this.errors = {};
  }

  onClose(): void {
    this.resetForm();
    this.close.emit();
  }
}
