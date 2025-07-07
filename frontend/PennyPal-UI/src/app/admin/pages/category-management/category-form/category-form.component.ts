import { Component, EventEmitter, Input, OnChanges, OnDestroy, OnInit, Output, SimpleChanges } from '@angular/core';
import { AdminCategory, CategoryFormData, CategoryUsageType } from '../../../models/category-management.model';
import { FormBuilder, FormGroup, FormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-category-form',
  imports: [CommonModule,FormsModule],
  templateUrl: './category-form.component.html',
  styleUrl: './category-form.component.css'
})
export class CategoryFormComponent implements OnInit , OnChanges  {
  @Input() isOpen = false;
  @Input() category: AdminCategory | null = null;

  @Output() save = new EventEmitter<CategoryFormData>();
  @Output() cancel = new EventEmitter<void>();

  usageTypes: CategoryUsageType[] = ['GOAL', 'INCOME', 'EXPENSE', 'SHARED'];

  formData: CategoryFormData = this.getEmptyFormData();

  get isEditMode(): boolean {
    return !!this.category;
  }

  ngOnInit(): void {
    if (!this.isEditMode) this.resetForm();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['category'] && this.category) {
      this.formData = { ...this.category };
    } else {
      this.resetForm();
    }
  }

  onUsageTypeChange(type: CategoryUsageType, event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.checked && !this.formData.usageTypes.includes(type)) {
      this.formData.usageTypes.push(type);
    } else {
      this.formData.usageTypes = this.formData.usageTypes.filter(t => t !== type);
    }
  }

  onSubmit(): void {
    if (this.formData.name.trim() && this.formData.usageTypes.length > 0) {
      console.log('Form submitted:', this.formData);
      
      this.save.emit({ ...this.formData });
      this.resetForm();
    }
  }

  onCancel(): void {
    this.cancel.emit();
    this.resetForm();
  }

  private resetForm(): void {
    this.formData = this.getEmptyFormData();
  }

  private getEmptyFormData(): CategoryFormData {
    return {
      name: '',
      description: '',
      icon: '',
      color: '#3B82F6',
      usageTypes: [],
      active: true,
      sortOrder: 1
    };
  }
}
