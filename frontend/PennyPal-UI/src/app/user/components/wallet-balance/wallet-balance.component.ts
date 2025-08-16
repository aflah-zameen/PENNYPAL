import { Component, EventEmitter, Output } from '@angular/core';
import { Observable } from 'rxjs';
import { WalletBalance, WalletStats } from '../../models/wallet.model';
import { WalletService } from '../../services/wallet-management.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-wallet-balance',
  imports: [CommonModule],
  templateUrl: './wallet-balance.component.html',
  styleUrl: './wallet-balance.component.css'
})
export class WalletBalanceComponent {
  @Output() addMoneyClicked = new EventEmitter<void>()
  @Output() sendMoneyClicked = new EventEmitter<void>()

  walletBalance$!: Observable<WalletBalance | null>
  walletStats$!: Observable<WalletStats>
  isRefreshing = false
  error$!: Observable<string | null>;

  constructor(public walletService: WalletService) {}

  ngOnInit() {
    this.walletBalance$ = this.walletService.walletBalance$
    this.walletStats$ = this.walletService.getWalletStats()
  }

  onAddMoney() {
    console.log("heyy");
    
    this.addMoneyClicked.emit()
  }

  refreshBalance() {
  }
}
