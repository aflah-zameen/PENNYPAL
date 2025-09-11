import { Component } from '@angular/core';
import { CoinBalance, CoinTransaction, PaymentMethod, RedemptionHistory, RedemptionRequest } from '../../models/reward-coin-model';
import { CoinService } from '../../services/coin-service';
import { CommonModule } from '@angular/common';
import { CoinBalanceCardComponent } from "../../components/reward-coin/coin-balance-card/coin-balance-card.component";
import { CoinTransactionHistoryComponent } from "../../components/reward-coin/coin-transaction-history/coin-transaction-history.component";
import { CoinRedemptionModalComponent } from "../../components/reward-coin/coin-redemption-modal/coin-redemption-modal.component";

@Component({
  selector: 'app-reward-management',
  imports: [CommonModule, CoinBalanceCardComponent, CoinTransactionHistoryComponent, CoinRedemptionModalComponent],
  templateUrl: './reward-management.component.html',
  styleUrl: './reward-management.component.css'
})
export class RewardManagementComponent {
  balance: CoinBalance | null = null
  transactions: CoinTransaction[] = []
  paymentMethods: PaymentMethod[] = []
  redemptionHistory: RedemptionHistory[] = []
  isRedemptionModalOpen = false
  showSuccessToast = false

  constructor(private coinService: CoinService) {}

  ngOnInit(): void {
    this.loadData()
  }

  private loadData(): void {
    this.coinService.getCoinBalance().subscribe((balance) => {
      this.balance = balance
    })

    this.coinService.getRedemptionHistory().subscribe((history) => {
      this.redemptionHistory = history
    })
  }

  openRedemptionModal(): void {
    this.isRedemptionModalOpen = true
  }

  closeRedemptionModal(): void {
    this.isRedemptionModalOpen = false
  }

  handleRedemption(request: RedemptionRequest): void {
    this.coinService.redeemCoins(request).subscribe((data) => {
      this.showSuccessToast = true
      this.loadData() // Refresh data

      // Hide toast after 4 seconds
      setTimeout(() => {
        this.showSuccessToast = false
      }, 4000)
    })
  }

  getRedemptionStatusClass(status: string): string {
  switch (status) {
    case "APPROVED":
      return "bg-gradient-to-r from-green-400 to-emerald-500 text-white";
    case "PENDING":
      return "bg-gradient-to-r from-yellow-400 to-orange-500 text-white";
    case "REJECTED":
      return "bg-gradient-to-r from-red-400 to-pink-500 text-white";
    default:
      return "bg-gradient-to-r from-gray-400 to-gray-500 text-white";
  }
}


  getRedemptionStatusBadgeClass(status: string): string {
  switch (status) {
    case "APPROVED":
      return "bg-gradient-to-r from-green-100 to-emerald-100 text-green-800 border border-green-200";
    case "PENDING":
      return "bg-gradient-to-r from-yellow-100 to-orange-100 text-yellow-800 border border-yellow-200";
    case "REJECTED":
      return "bg-gradient-to-r from-red-100 to-pink-100 text-red-800 border border-red-200";
    default:
      return "bg-gradient-to-r from-gray-100 to-gray-200 text-gray-800 border border-gray-200";
  }
}


  getRedemptionStatusLabel(status: string): string {
  switch (status) {
    case "APPROVED":
      return "Approved";
    case "PENDING":
      return "Pending";
    case "REJECTED":
      return "Rejected";
    default:
      return status;
  }
}

}
