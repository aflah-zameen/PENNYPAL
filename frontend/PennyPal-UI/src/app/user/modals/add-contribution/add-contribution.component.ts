import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ContributionFormData } from '../../models/contribution-form-date.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-add-contribution',
  imports: [CommonModule,FormsModule],
  templateUrl: './add-contribution.component.html',
  styleUrl: './add-contribution.component.css'
})
export class AddContributionComponent implements OnInit {
  @Input() isOpen = false
  @Input()goalId!:number
  @Input()targetAmount!:number
  @Input()currentAmount!:number
  @Input() goalTitle = ""
  @Input() goalColor = "#3B82F6"
  @Output() close = new EventEmitter<void>()
  @Output() submit = new EventEmitter<ContributionFormData>()

  errorMessage: string | null = null;

  formData!: ContributionFormData;

  ngOnInit(): void {
    this.formData = {
    goalId: this.goalId,
    amount: null,
    notes: '',
  };
  }

  get isSubmitDisabled(): boolean {
    return (
      !this.formData.amount ||
      this.formData.amount <= 0 ||
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
    this.formData = { amount: null, notes: '', goalId: this.goalId };
    this.errorMessage = null;
    this.close.emit();
  }

  onSubmit(): void {
    const contribution = this.formData.amount ?? 0;
    const total = this.currentAmount + contribution;

    if (contribution <= 0) return;

    if (total > this.targetAmount) {
      this.errorMessage =
        'Your contribution exceeds the target amount for this goal.';
      return;
    }

    this.submit.emit({ ...this.formData });
    this.onClose();
  }
}
