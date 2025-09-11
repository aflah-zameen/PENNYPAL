import { Component } from '@angular/core';
import { PaginationInfo, RedemptionFilter, RedemptionRequest, RedemptionStats } from '../../models/admin-redemption-model';
import { AdminRedemptionService } from '../../services/admin-redemption-management-service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AdminRedemptionStatsComponent } from "../../components/redemption/admin-redemption-stats/admin-redemption-stats.component";
import { AdminRedemptionFiltersComponent } from "../../components/redemption/admin-redemption-filters/admin-redemption-filters.component";
import { AdminRedemptionTableComponent } from "../../components/redemption/admin-redemption-table/admin-redemption-table.component";
import { AdminRedemptionPaginationComponent } from "../../components/redemption/admin-redemption-pagination/admin-redemption-pagination.component";
import { AdminRejectionModalComponent } from "../../components/redemption/admin-rejection-modal/admin-rejection-modal.component";

@Component({
  selector: 'app-redemption-management',
  imports: [CommonModule, FormsModule, AdminRedemptionStatsComponent, AdminRedemptionFiltersComponent, AdminRedemptionTableComponent, AdminRedemptionPaginationComponent, AdminRejectionModalComponent],
  templateUrl: './redemption-management.component.html',
  styleUrl: './redemption-management.component.css'
})
export class RedemptionManagementComponent {
  stats: RedemptionStats | null = null
  requests: RedemptionRequest[] = []
  pagination: PaginationInfo | null = null
  loading = false

  currentFilter: RedemptionFilter = { status: "ALL", search: "" }
  currentPage = 1

  showRejectionModal = false
  processingRejection = false
  pendingRejectionId: string | null = null

  showSuccessToast = false
  showErrorToast = false
  successMessage = ""
  errorMessage = ""

  constructor(private adminRedemptionService: AdminRedemptionService) {}

  ngOnInit() {
    this.loadStats()
    this.loadRequests()
  }

  loadStats() {
    this.adminRedemptionService.getRedemptionStats().subscribe({
      next: (stats) => {        
        this.stats = stats
      },
      error: (error) => {
        console.error("Error loading stats:", error)
        this.showError("Failed to load statistics")
      },
    })
  }

  loadRequests() {
    this.loading = true
    this.adminRedemptionService.getRedemptionRequests(this.currentFilter, this.currentPage).subscribe({
      next: (response) => {
        this.requests = response.requests
        this.pagination = response.pagination
        this.loading = false
      },
      error: (error) => {
        console.error("Error loading requests:", error)
        this.loading = false
        this.showError("Failed to load redemption requests")
      },
    })
  }

  onFilterChange(filter: RedemptionFilter) {
    this.currentFilter = filter
    this.currentPage = 1
    this.loadRequests()
  }

  onPageChange(page: number) {
    this.currentPage = page
    this.loadRequests()
  }

  onApproveRequest(requestId: string) {
    this.adminRedemptionService.approveRequest(requestId).subscribe({
      next: () => {
        this.showSuccess("Redemption request approved successfully")
        this.loadStats()
        this.loadRequests()
      },
      error: (error) => {
        console.error("Error approving request:", error)
        this.showError("Failed to approve redemption request")
      },
    })
  }

  onRejectRequest(requestId: string) {
    this.pendingRejectionId = requestId
    this.showRejectionModal = true
  }

  closeRejectionModal() {
    this.showRejectionModal = false
    this.pendingRejectionId = null
  }

  confirmRejection(reason: string) {
    if (!this.pendingRejectionId) return

    this.processingRejection = true
    this.adminRedemptionService.rejectRequest(this.pendingRejectionId, reason).subscribe({
      next: () => {
        this.processingRejection = false
        this.showRejectionModal = false
        this.pendingRejectionId = null
        this.showSuccess("Redemption request rejected successfully")
        this.loadStats()
        this.loadRequests()
      },
      error: (error) => {
        console.error("Error rejecting request:", error)
        this.processingRejection = false
        this.showError("Failed to reject redemption request")
      },
    })
  }

  refreshData() {
    this.loadStats()
    this.loadRequests()
  }

  private showSuccess(message: string) {
    this.successMessage = message
    this.showSuccessToast = true
    setTimeout(() => {
      this.showSuccessToast = false
    }, 4000)
  }

  private showError(message: string) {
    this.errorMessage = message
    this.showErrorToast = true
    setTimeout(() => {
      this.showErrorToast = false
    }, 4000)
  }
}
