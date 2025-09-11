import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CoinBalance } from '../../../models/reward-coin-model';

@Component({
  selector: 'app-coin-balance-card',
  imports: [CommonModule],
  templateUrl: './coin-balance-card.component.html',
  styleUrl: './coin-balance-card.component.css'
})
export class CoinBalanceCardComponent {
  @Input() balance: CoinBalance | null = null
  @Output() redeemClick = new EventEmitter<void>()

  getRealMoneyValue(): string {
    if (!this.balance) return "0.00"
    return (this.balance.availableCoins * 0.1).toFixed(2)
  }

  canRedeem(): boolean {
    return (this.balance?.availableCoins || 0) >= 100
  }

  onRedeemClick(): void {
    if (this.canRedeem()) {
      this.redeemClick.emit()
    }
  }
}
