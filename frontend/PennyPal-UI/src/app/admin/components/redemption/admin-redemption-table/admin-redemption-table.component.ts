import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { RedemptionRequest } from '../../../models/admin-redemption-model';

@Component({
  selector: 'app-admin-redemption-table',
  imports: [CommonModule],
  templateUrl: './admin-redemption-table.component.html',
  styleUrl: './admin-redemption-table.component.css'
})
export class AdminRedemptionTableComponent {
  @Input() requests: RedemptionRequest[] = []
  @Input() loading = false
  @Output() approve = new EventEmitter<string>()
  @Output() reject = new EventEmitter<string>()

  processingId: string | null = null

  getUserInitials(name: string): string {
    return name
      .split(" ")
      .map((n) => n[0])
      .join("")
      .toUpperCase()
  }

  getStatusBadgeClass(status: string): string {
    switch (status) {
      case "PENDING":
        return "bg-yellow-100 text-yellow-800"
      case "APPROVED":
        return "bg-green-100 text-green-800"
      case "REJECTED":
        return "bg-red-100 text-red-800"
      case "PROCESSING":
        return "bg-blue-100 text-blue-800"
      default:
        return "bg-gray-100 text-gray-800"
    }
  }

  getPaymentMethodIconClass(type: string): string {
    switch (type) {
      case "BANK_TRANSFER":
        return "bg-blue-100 text-blue-600"
      case "PAYPAL":
        return "bg-purple-100 text-purple-600"
      case "MOBILE_MONEY":
        return "bg-green-100 text-green-600"
      default:
        return "bg-gray-100 text-gray-600"
    }
  }

  onApprove(requestId: string) {
    this.processingId = requestId
    this.approve.emit(requestId)
  }

  onReject(requestId: string) {
    this.processingId = requestId
    this.reject.emit(requestId)
  }
}
