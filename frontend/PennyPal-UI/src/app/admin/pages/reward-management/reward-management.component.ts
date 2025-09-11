import { Component, OnInit } from '@angular/core';
import { CreateRewardPolicyRequest, RewardPolicy, UpdateRewardPolicyRequest } from '../../models/reward.models';
import { RewardPolicyService } from '../../services/admin-reward-service';
import { CommonModule } from '@angular/common';
import { RewardPolicyTableComponent } from "../../components/reward/reward-policy-table/reward-policy-table.component";
import { RewardPolicyModalComponent } from "../../components/reward/reward-policy-modal/reward-policy-modal.component";
import { RewardPolicyDeleteModalComponent } from "../../components/reward/reward-policy-delete-modal/reward-policy-delete-modal.component";

@Component({
  selector: 'app-reward-management',
  imports: [CommonModule, RewardPolicyTableComponent, RewardPolicyModalComponent, RewardPolicyDeleteModalComponent],
  templateUrl: './reward-management.component.html',
  styleUrl: './reward-management.component.css'
})
export class RewardManagementComponent implements OnInit {
  policies: RewardPolicy[] = [];
  loading = false;

  // Modal states
  showPolicyModal = false;
  showDeleteModal = false;
  modalLoading = false;
  deleteLoading = false;

  // Selected data
  editingPolicy: RewardPolicy | null = null;
  policyToDelete: RewardPolicy | null = null;

  // Toast states
  showSuccessToast = false;
  showErrorToast = false;
  successMessage = "";
  errorMessage = "";

  constructor(private rewardPolicyService: RewardPolicyService) {}

  ngOnInit() {
    this.loadPolicies();
  }

  get totalPolicies(): number {
    return this.policies.filter((p) => !p.isDeleted).length;
  }

  get activePolicies(): number {
    return this.policies.filter((p) => p.isActive && !p.isDeleted).length;
  }

  get totalCoinsPerAction(): number {
    return this.policies.filter((p) => p.isActive && !p.isDeleted)
      .reduce((sum, policy) => sum + policy.coinAmount, 0);
  }

  loadPolicies() {
    this.loading = true;
    this.rewardPolicyService.getPolicies().subscribe({
      next: (policies) => {
        this.policies = policies;
        this.loading = false;
      },
      error: () => {
        this.showError("Failed to load reward policies");
        this.loading = false;
      },
    });
  }

  onAddPolicy() {
    this.editingPolicy = null;
    this.showPolicyModal = true;
  }

  onEditPolicy(policy: RewardPolicy) {
    this.editingPolicy = policy;
    this.showPolicyModal = true;
  }

  onDeletePolicy(policy: RewardPolicy) {
    this.policyToDelete = policy;
    this.showDeleteModal = true;
  }

  onToggleStatus(policy: RewardPolicy) {
    this.rewardPolicyService.togglePolicyStatus(policy.id,policy.isActive).subscribe({
      next: () => {
        this.showSuccess(`Policy ${policy.isActive ? "deactivated" : "activated"} successfully`);
      },
      error: () => {
        this.showError("Failed to update policy status");
      },
    });
  }

  onSavePolicy(request: CreateRewardPolicyRequest | UpdateRewardPolicyRequest) {
    this.modalLoading = true;

    const isUpdate = "id" in request;
    const operation = isUpdate
      ? this.rewardPolicyService.updatePolicy(request.id, request as UpdateRewardPolicyRequest)
      : this.rewardPolicyService.createPolicy(request as CreateRewardPolicyRequest);

    operation.subscribe({
      next: () => {
        this.showSuccess(isUpdate ? "Policy updated successfully" : "Policy created successfully");
        this.modalLoading = false;
        this.showPolicyModal = false;
        this.editingPolicy = null;
      },
      error: () => {
        this.showError(isUpdate ? "Failed to update policy" : "Failed to create policy");
        this.modalLoading = false;
      },
    });
  }

  onConfirmDelete() {
    if (!this.policyToDelete) return;

    this.deleteLoading = true;
    this.rewardPolicyService.deletePolicy(this.policyToDelete.id).subscribe({
      next: () => {
        this.showSuccess("Policy deleted successfully");
        this.deleteLoading = false;
        this.showDeleteModal = false;
        this.policyToDelete = null;
      },
      error: () => {
        this.showError("Failed to delete policy");
        this.deleteLoading = false;
      },
    });
  }

  onCancelDelete() {
    this.showDeleteModal = false;
    this.policyToDelete = null;
  }

  onClosePolicyModal() {
    this.showPolicyModal = false;
    this.editingPolicy = null;
  }

  private showSuccess(message: string) {
    this.successMessage = message;
    this.showSuccessToast = true;
    setTimeout(() => (this.showSuccessToast = false), 3000);
  }

  private showError(message: string) {
    this.errorMessage = message;
    this.showErrorToast = true;
    setTimeout(() => (this.showErrorToast = false), 3000);
  }
}
