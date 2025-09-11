import { Component, EventEmitter, Input, Output } from '@angular/core';
import { AdminSubscriptionPlan, CreatePlanRequest, UpdatePlanRequest } from '../../../models/admin-subscription-model';
import { FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-admin-plan-modal',
  imports: [ReactiveFormsModule,CommonModule],
  templateUrl: './admin-plan-modal.component.html',
  styleUrl: './admin-plan-modal.component.css'
})
export class AdminPlanModalComponent {
  @Input() isOpen = false
  @Input() editingPlan: AdminSubscriptionPlan | null = null
  @Input() loading = false
  @Output() close = new EventEmitter<void>()
  @Output() save = new EventEmitter<CreatePlanRequest | UpdatePlanRequest>()

  planForm!: FormGroup

  constructor(private fb: FormBuilder) {
    this.initializeForm()
  }

  ngOnInit() {
    this.initializeForm()
  }

  ngOnChanges() {
    if (this.isOpen) {
      this.initializeForm()
      if (this.editingPlan) {
        this.populateForm()
      }
    }
  }

  private initializeForm() {
    this.planForm = this.fb.group({
      name: ["", [Validators.required, Validators.minLength(3)]],
      description: [""],
      amount: ["", [Validators.required, Validators.min(0.01)]],
      durationDays: ["", [Validators.required]],
      features: this.fb.array([]),
      isActive: [true],
    })
  }

  private populateForm() {
    if (!this.editingPlan) return

    this.planForm.patchValue({
      name: this.editingPlan.name,
      description: this.editingPlan.description || "",
      amount: this.editingPlan.amount,
      durationDays: this.editingPlan.durationDays,
      isActive: this.editingPlan.isActive,
    })

    // Clear existing features and add from editing plan
    this.featuresArray.clear()
    this.editingPlan.features.forEach((feature) => {
      this.featuresArray.push(this.fb.control(feature, [Validators.required]))
    })
  }

  get featuresArray(): FormArray {
    return this.planForm.get("features") as FormArray
  }

  addFeature() {
    this.featuresArray.push(this.fb.control("", [Validators.required]))
  }

  removeFeature(index: number) {
    this.featuresArray.removeAt(index)
  }

  onSubmit() {
    if (this.planForm.invalid) {
      this.planForm.markAllAsTouched()
      return
    }

    const formValue = this.planForm.value
    const request = {
      name: formValue.name,
      description: formValue.description,
      amount: Number.parseFloat(formValue.amount),
      durationDays: Number.parseInt(formValue.durationDays),
      features: formValue.features.filter((f: string) => f.trim() !== ""),
    }

    if (this.editingPlan) {
      const updateRequest: UpdatePlanRequest = {
        ...request,
        id: this.editingPlan.id
      }
      this.save.emit(updateRequest)
    } else {
      this.save.emit(request as CreatePlanRequest)
    }
  }

  onClose() {
    this.close.emit()
  }

  onBackdropClick(event: Event) {
    if (event.target === event.currentTarget) {
      this.onClose()
    }
  }
}
