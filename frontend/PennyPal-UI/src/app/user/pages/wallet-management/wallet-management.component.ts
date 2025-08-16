import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { WalletBalanceComponent } from "../../components/wallet-balance/wallet-balance.component";
import { WalletTransactionListComponent } from "../../components/wallet-transaction-list/wallet-transaction-list.component";
import { SendMoneyModalComponent } from "../../components/money-flow/send-money-modal/send-money-modal.component";
import { WalletService } from '../../services/wallet-management.service';
import { PaymentMethod } from '../../models/money-flow.model';
import { ToastrService } from 'ngx-toastr';
import { NgxSpinnerService } from 'ngx-spinner';
import { PinConfirmationModalComponent } from "../../components/money-flow/pin-confirmation-modal/pin-confirmation-modal.component";
import { ContactManagementService } from '../../services/contact-management.service';

@Component({
  selector: 'app-wallet-management',
  imports: [CommonModule, WalletBalanceComponent, WalletTransactionListComponent, SendMoneyModalComponent, PinConfirmationModalComponent],
  templateUrl: './wallet-management.component.html',
  styleUrl: './wallet-management.component.css'
})
export class WalletManagementComponent implements OnInit {
  isAddMoneyModalOpen = false
  isPinConfirmationOpen = false
  showSuccessToast = false
  amount : number = 0;
  notes : string = '';
  pin : string = '';

  selectedPaymentMethod: PaymentMethod | null = null

  paymentMethods: PaymentMethod[] = []

  constructor(private walletService : WalletService,private cardService : ContactManagementService,  private toastr : ToastrService,private spinner : NgxSpinnerService) {}

  ngOnInit(): void {
    this.walletService.getWalletStats().subscribe();
    this.walletService.getWallet().subscribe();
    this.walletService.getWalletTransactions().subscribe();
    this.cardService.getPaymentMethods().subscribe((data)=>{
        this.paymentMethods = data;
    });
  }

  openAddMoneyModal() {
    this.isAddMoneyModalOpen = true
  }

  closeAddMoneyModal() {
    this.isAddMoneyModalOpen = false
  }

  onAmountConfirmed(data: { amount: number; note: string; paymentMethod: PaymentMethod }) {
      this.isAddMoneyModalOpen = false;
      this.isPinConfirmationOpen = true;    
      this.selectedPaymentMethod = data.paymentMethod
      this.amount = data.amount;
      this.notes = data.note;
    }

    onPinConfirmed(pin: string) {
    this.isPinConfirmationOpen = false;
    this.pin = pin;
    if(this.amount != 0 && this.selectedPaymentMethod != null && pin != ''){
      this.spinner.show();
      this.walletService.addMoney({
      amount : this.amount,
      pin : this.pin,
      cardId : this.selectedPaymentMethod.id,
      notes : this.notes

    }).subscribe({
      next : (res) => {
        this.spinner.hide();
        this.toastr.success("Amount added successfully");
      },
      error :(err)=>{
        this.spinner.hide();
        this.toastr.error("Error while updating amount");
      }
    }
    )
    }
  }

}