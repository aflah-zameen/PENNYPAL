import { Component } from '@angular/core';
import { AdminSubscriptionPlan, CreatePlanRequest, PlanSubscriber, UpdatePlanRequest } from '../../models/admin-subscription-model';
import { AdminSubscriptionService } from '../../services/admin-subscription-service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AdminPlanTableComponent } from "../../components/subscription/admin-plan-table/admin-plan-table.component";
import { AdminPlanModalComponent } from "../../components/subscription/admin-plan-modal/admin-plan-modal.component";
import { AdminSubscribersModalComponent } from "../../components/subscription/admin-subscribers-modal/admin-subscribers-modal.component";

@Component({
  selector: 'app-subscription-management',
  imports: [CommonModule, FormsModule, AdminPlanTableComponent, AdminPlanModalComponent, AdminSubscribersModalComponent],
  templateUrl: './subscription-management.component.html',
  styleUrl: './subscription-management.component.css'
})
export class SubscriptionManagementComponent {
  plans: AdminSubscriptionPlan[] = []
  loading = false

  // Modal states
  showPlanModal = false
  showSubscribersModal = false
  showDeleteModal = false
  modalLoading = false
  subscribersLoading = false
  deleteLoading = false

  // Selected data
  editingPlan: AdminSubscriptionPlan | null = null
  selectedPlan: AdminSubscriptionPlan | null = null
  planToDelete: AdminSubscriptionPlan | null = null
  planSubscribers: PlanSubscriber[] = []

  // Toast states
  showSuccessToast = false
  showErrorToast = false
  successMessage = ""
  errorMessage = ""

  constructor(private subscriptionService: AdminSubscriptionService) {
    subscriptionService.getPlans().subscribe((data)=>{
      this.plans = data
    });
  }

  ngOnInit() {
  }

  get totalPlans(): number {
    return this.plans.length
  }

  get activePlans(): number {
    return this.plans.filter((p) => p.isActive).length
  }

  get totalSubscribers(): number {
    return this.plans.reduce((sum, plan) => sum + plan.subscriberCount, 0)
  }

  onAddPlan() {
    this.editingPlan = null
    this.showPlanModal = true
  }

  onEditPlan(plan: AdminSubscriptionPlan) {
    this.editingPlan = plan
    this.showPlanModal = true
  }

  onDeletePlan(plan: AdminSubscriptionPlan) {
    this.planToDelete = plan
    this.showDeleteModal = true
  }

  onViewSubscribers(plan: AdminSubscriptionPlan) {
    // this.selectedPlan = plan
    // this.subscribersLoading = true
    // this.showSubscribersModal = true

    // this.subscriptionService.getPlanSubscribers(plan.id).subscribe({
    //   next: (subscribers) => {
    //     this.planSubscribers = subscribers
    //     this.subscribersLoading = false
    //   },
    //   error: (error) => {
    //     this.showError("Failed to load subscribers")
    //     this.subscribersLoading = false
    //   },
    // })
  }

 onSavePlan(request: CreatePlanRequest | UpdatePlanRequest): void {
  this.modalLoading = true

  const isUpdate = 'id' in request
  const operation = isUpdate
    ? this.subscriptionService.updatePlan(request.id,request as CreatePlanRequest)
    : this.subscriptionService.createPlan(request as CreatePlanRequest)

  operation.subscribe({
    next: () => {
      this.showSuccess(isUpdate ? 'Plan updated successfully' : 'Plan created successfully')
      this.modalLoading = false
      this.showPlanModal = false
      this.editingPlan = null
    },
    error: () => {
      this.showError(isUpdate ? 'Failed to update plan' : 'Failed to create plan')
      this.modalLoading = false
    }
  })
}


  onConfirmDelete() {
    if (!this.planToDelete) return

    this.deleteLoading = true
    this.subscriptionService.deletePlan(this.planToDelete.id).subscribe({
      next: () => {
        this.plans = this.plans.filter((p) => p.id !== this.planToDelete!.id)
        this.showSuccess("Plan deleted successfully")
        this.deleteLoading = false
        this.showDeleteModal = false
        this.planToDelete = null
      },
      error: (error) => {
        this.showError("Failed to delete plan")
        this.deleteLoading = false
      },
    })
  }

  onCancelDelete() {
    this.showDeleteModal = false
    this.planToDelete = null
  }

  onClosePlanModal() {
    this.showPlanModal = false
    this.editingPlan = null
  }

  onCloseSubscribersModal() {
    this.showSubscribersModal = false
    this.selectedPlan = null
    this.planSubscribers = []
  }

  private showSuccess(message: string) {
    this.successMessage = message
    this.showSuccessToast = true
    setTimeout(() => {
      this.showSuccessToast = false
    }, 3000)
  }

  private showError(message: string) {
    this.errorMessage = message
    this.showErrorToast = true
    setTimeout(() => {
      this.showErrorToast = false
    }, 3000)
  }
}
