import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CoinBalance, PaymentMethod, RedemptionRequest } from '../../../models/reward-coin-model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ModalOverlayComponent } from "../../../modals/modal-overlay/modal-overlay.component";

@Component({
  selector: 'app-coin-redemption-modal',
  imports: [CommonModule, FormsModule, ModalOverlayComponent],
  templateUrl: './coin-redemption-modal.component.html',
  styleUrl: './coin-redemption-modal.component.css'
})
export class CoinRedemptionModalComponent implements OnInit {
  @Input() isOpen = false
  @Input() balance: CoinBalance | null = null
  @Input() paymentMethods: PaymentMethod[] = []
  @Output() close = new EventEmitter<void>()
  @Output() redeem = new EventEmitter<RedemptionRequest>()

  coinAmount = 100
  realMoneyAmount = 10.0
  selectedPaymentMethodId = ""
  isSubmitting = false

  ngOnInit(): void {
    this.updateRealMoney()
  }

  getMaxCashValue(): string {
    if (!this.balance) return "0.00"
    return (this.balance.availableCoins * 0.1).toFixed(2)
  }

  updateRealMoney(): void {
    this.realMoneyAmount = this.coinAmount * 0.1
  }

  setQuickAmount(amount: number): void {
    this.coinAmount = Math.min(amount, this.balance?.availableCoins || 0)
    this.updateRealMoney()
  }

  // getPaymentMethodIconClass(type: string): string {
  //   switch (type) {
  //     case "BANK_TRANSFER":
  //       return "bg-gradient-to-r from-blue-400 to-blue-500 text-white"
  //     case "PAYPAL":
  //       return "bg-gradient-to-r from-indigo-400 to-purple-500 text-white"
  //     case "MOBILE_MONEY":
  //       return "bg-gradient-to-r from-green-400 to-emerald-500 text-white"
  //     default:
  //       return "bg-gradient-to-r from-gray-400 to-gray-500 text-white"
  //   }
  // }

  isFormValid(): boolean {
    return (
      this.coinAmount >= 100 &&
      this.coinAmount <= (this.balance?.availableCoins || 0)
    )
  }

  onClose(): void {
    this.close.emit()
  }

  onSubmit(): void {
    if (!this.isFormValid()) return

    this.isSubmitting = true

    const request: RedemptionRequest = {
      coinAmount: this.coinAmount,
      realMoneyAmount: this.realMoneyAmount,
    }

    this.redeem.emit(request)

    // Reset form after a delay to show loading state
    setTimeout(() => {
      this.isSubmitting = false
      this.coinAmount = 100
      this.realMoneyAmount = 10.0
      this.selectedPaymentMethodId = ""
      this.onClose()
    }, 2000)
  }
}
