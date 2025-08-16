import { Component, EventEmitter, Input, Output, SimpleChanges } from '@angular/core';
import { Goal, GoalFormData, GoalFormErrors } from '../../models/goal.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalOverlayComponent } from "../modal-overlay/modal-overlay.component";
import { UserCategoryResponse } from '../../models/user-category.model';

@Component({
  selector: 'app-edit-goal-modal',
  imports: [CommonModule, FormsModule, ModalOverlayComponent],
  templateUrl: './edit-goal-modal.component.html',
  styleUrl: './edit-goal-modal.component.css'
})
export class EditGoalModalComponent {
  @Input() isOpen: boolean = false;
  @Input() goal: Goal | null = null;
  @Input() categories :UserCategoryResponse[] =[]
  @Output() close = new EventEmitter<void>();
  @Output() goalUpdated = new EventEmitter<{ id: string; data: GoalFormData }>();

  formData: GoalFormData = {
    title: '',
    targetAmount: null,
    startDate: '',
    endDate: '',
    description: '',
    categoryId: null,
  };

  errors: GoalFormErrors = {};
  isSubmitting: boolean = false;

  originalFormData: GoalFormData | null = null;


  ngOnChanges(changes: SimpleChanges) {
    if (changes['goal'] && this.goal) {
      this.populateForm();
    }
  }

  populateForm(): void {
  if (this.goal) {
    
    const data: GoalFormData = {
      title: this.goal.title,
      targetAmount: this.goal.targetAmount,
      startDate: this.goal.startDate,
      endDate: this.goal.endDate,
      description: '', // If your goal has description, use this.goal.description
      categoryId: this.goal.category.categoryId,
    };
    
    this.formData = { ...data };
    this.originalFormData = { ...data };
    
  }
}

hasChanges(): boolean {
  if (!this.originalFormData) return true;

  return (
    this.formData.title !== this.originalFormData.title ||
    this.formData.targetAmount !== this.originalFormData.targetAmount ||
    this.formData.startDate !== this.originalFormData.startDate ||
    this.formData.endDate !== this.originalFormData.endDate ||
    this.formData.description !== this.originalFormData.description ||
    this.formData.categoryId !== this.originalFormData.categoryId
  );
}



  getCurrentProgress(): number {
    if (!this.goal || !this.formData.targetAmount) return 0;
    return Math.round((this.goal.currentAmount / this.formData.targetAmount!) * 100);
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

    if (!this.formData.title.trim()) {
      this.errors.name = 'Goal name is required';
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

    if (this.formData.startDate && this.formData.endDate) {
      const startDate = new Date(this.formData.startDate);
      const endDate = new Date(this.formData.endDate);
      
      if (endDate <= startDate) {
        this.errors.endDate = 'Target date must be after start date';
      }
    }

    return Object.keys(this.errors).length === 0;
  }

  onSubmit(){
    if (!this.validateForm() || !this.goal) {
      return;
    }
    this.isSubmitting = true;  
      const submitData = { 
        id: this.goal.id, 
        data: { ...this.formData } 
      }
      this.goalUpdated.emit(submitData);
      this.onClose();
    this.isSubmitting = false;
  }

  onClose(): void {
    this.errors = {};
    this.close.emit();
  }
}
