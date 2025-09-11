import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ActionType, CreateRewardPolicyRequest, RewardPolicy, UpdateRewardPolicyRequest } from '../../../models/reward.models';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { RewardPolicyService } from '../../../services/admin-reward-service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-reward-policy-modal',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './reward-policy-modal.component.html',
  styleUrl: './reward-policy-modal.component.css'
})
export class RewardPolicyModalComponent {
  @Input() isOpen = false
  @Input() editingPolicy: RewardPolicy | null = null
  @Input() loading = false
  @Output() close = new EventEmitter<void>()
  @Output() save = new EventEmitter<CreateRewardPolicyRequest | UpdateRewardPolicyRequest>()

  policyForm!: FormGroup
  actionTypeOptions: ActionType[] = []

  constructor(
    private fb: FormBuilder,
    private rewardPolicyService: RewardPolicyService,
  ) {
    this.initializeForm()
    this.actionTypeOptions = [
      "GOAL_COMPLETION","LOAN_REPAYMENT"
    ]
  }

  ngOnInit() {
    this.initializeForm()
  }

  ngOnChanges() {
    if (this.isOpen) {
      this.initializeForm()
      if (this.editingPolicy) {
        this.populateForm()
      }
    }
  }

  get selectedActionType() {
    const actionType = this.policyForm.get("actionType")?.value
    return actionType
  }

  private initializeForm() {
    this.policyForm = this.fb.group({
      actionType: ["", [Validators.required]],
      coinAmount: ["", [Validators.required, Validators.min(1), Validators.max(10000)]],
      description: [""],
      isActive: [true],
    })
  }

  private populateForm() {
    if (!this.editingPolicy) return

    this.policyForm.patchValue({
      actionType: this.editingPolicy.actionType,
      coinAmount: this.editingPolicy.coinAmount,
      description: this.editingPolicy.description || "",
      isActive: this.editingPolicy.isActive,
    })
  }

  onSubmit() {
    if (this.policyForm.invalid) {
      this.policyForm.markAllAsTouched()
      return
    }

    const formValue = this.policyForm.value
    const request = {
      actionType: formValue.actionType,
      coinAmount: Number.parseInt(formValue.coinAmount),
      description: formValue.description,
      isActive: formValue.isActive,
    }

    if (this.editingPolicy) {
      const updateRequest: UpdateRewardPolicyRequest = {
        ...request,
        id: this.editingPolicy.id,
      }
      this.save.emit(updateRequest)
    } else {
      this.save.emit(request as CreateRewardPolicyRequest)
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
