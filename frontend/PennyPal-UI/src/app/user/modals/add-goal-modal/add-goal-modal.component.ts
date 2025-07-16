import { Component, EventEmitter, Input, Output } from '@angular/core';
import { GoalFormData, GoalFormErrors } from '../../models/goal.model';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ModalOverlayComponent } from "../modal-overlay/modal-overlay.component";
import { UserCategoryResponse } from '../../models/user-category.model';

@Component({
  selector: 'app-add-goal-modal',
  imports: [FormsModule, CommonModule, ModalOverlayComponent],
  templateUrl: './add-goal-modal.component.html',
  styleUrl: './add-goal-modal.component.css'
})
export class AddGoalModalComponent {
  @Input() isOpen: boolean = false;
  @Input() categories: UserCategoryResponse[] = [];
  @Output() close = new EventEmitter<void>();
  @Output() goalAdded = new EventEmitter<GoalFormData>();

  formData: GoalFormData = {
    title: '',
    targetAmount: null,
    startDate: new Date().toISOString().split('T')[0], // Default to today,
    endDate: '',
    description: '',
    categoryId: null
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

    if (!this.formData.title.trim()) {
      this.errors.name = 'Goal title is required';
    }

    if (!this.formData.targetAmount || this.formData.targetAmount <= 0) {
      this.errors.amount = 'Please enter a valid target amount';
    }

    if (!this.formData.startDate) {
      this.errors.startDate = 'Start date is required';
    }

    if (!this.formData.endDate) {
      this.errors.endDate = 'Target date is required';
    }

    if(!this.formData.categoryId){
      this.errors.category = 'Please choose a category';
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

    // try {
    //   // Simulate API call
    //   await new Promise(resolve => setTimeout(resolve, 1000));
      
      this.goalAdded.emit({ ...this.formData });
      this.resetForm();
      this.onClose();
    // } catch (error) {
    //   this.errors.general = 'Failed to create goal. Please try again.';
    // } finally {
    //   this.isSubmitting = false;
    // }
  }

  resetForm(): void {
    this.formData = {
    title: '',
    targetAmount: null,
    startDate: new Date().toISOString().split('T')[0], // Default to today,
    endDate: '',
    description: '',
    categoryId: null
  };
    this.errors = {};
    this.isSubmitting = false;
  }

  onClose(): void {
    this.resetForm();
    this.close.emit();
  }
}
