import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ContributionFormData } from '../../models/contribution-form-date.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-add-contribution',
  imports: [CommonModule,FormsModule],
  templateUrl: './add-contribution.component.html',
  styleUrl: './add-contribution.component.css'
})
export class AddContributionComponent {
  @Input() isOpen = false
  @Input() goalTitle = ""
  @Input() goalColor = "#3B82F6"
  @Output() close = new EventEmitter<void>()
  @Output() submit = new EventEmitter<ContributionFormData>()

  formData: ContributionFormData = {
    amount: 0,
    notes: "",
  }

  onClose(): void {
    this.formData = { amount: 0, notes: "" }
    this.close.emit()
  }

  onSubmit(): void {
    if (this.formData.amount && this.formData.amount > 0) {
      this.submit.emit({ ...this.formData })
      this.formData = { amount: 0, notes: "" }
    }
  }
}
