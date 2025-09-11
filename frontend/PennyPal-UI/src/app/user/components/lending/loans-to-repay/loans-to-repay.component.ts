import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { Loan } from '../../../models/lend.model';
import { LendingService } from '../../../services/lending-management.serive';
import { AsyncPipe, CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PinConfirmationModalComponent } from "../../money-flow/pin-confirmation-modal/pin-confirmation-modal.component";
import { SendLentMoneyComponent } from "../../../modals/send-lent-money/send-lent-money.component";
import { PaymentMethod, TransferRequest } from '../../../models/money-flow.model';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { ContactManagementService } from '../../../services/contact-management.service';
import { RewardModalComponent } from "../../../modals/reward-modal/reward-modal.component";

@Component({
  selector: 'app-loans-to-repay',
  imports: [CommonModule, FormsModule,  PinConfirmationModalComponent, SendLentMoneyComponent, RewardModalComponent],
  templateUrl: './loans-to-repay.component.html',
  styleUrl: './loans-to-repay.component.css',
  providers:[AsyncPipe]
})
export class LoansToRepayComponent {
  loansToRepay$!: Observable<Loan[]>
  loading$!: Observable<boolean>
  repaymentAmounts: { [key: string]: number } = {}

  isRewardModalOpen : boolean = false;
  rewardCoins : number =  0 ;

  constructor(
    public lendingService: LendingService,
    private spinner : NgxSpinnerService,
    private toastr : ToastrService,
    private cardService : ContactManagementService
  ) {}

  paymentErrors: { [loanId: string]: string } = {};

    isAddMoneyModalOpen = false
    isPinConfirmationOpen = false
    amount : number = 0;
    notes : string = '';
    pin : string = '';
    
    selectedPaymentMethod: PaymentMethod | null = null
    paymentMethods: PaymentMethod[] = []
    selectedLoan : Loan | null = null

  ngOnInit() {
    this.loansToRepay$ = this.lendingService.loansToPay$
    this.loading$ = this.lendingService.loading$
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
        this.notes = data.note;
      }
  
      onPinConfirmed(pin: string) {
      this.isPinConfirmationOpen = false;
      this.pin = pin;
      
      if(this.amount != 0 && this.selectedPaymentMethod != null && pin != '' && this.selectedLoan){
        this.spinner.show();
        const form : TransferRequest={
          recipientId : this.selectedLoan.lenderId,
          amount : this.amount,
          note : this.notes,
          pin : this.pin,
          paymentMethodId : this.selectedPaymentMethod.id
        }
        this.lendingService.makePayment(form,this.selectedLoan.id).subscribe({
        next : (coins) => {
          this.spinner.hide();
          if(coins){
            this.rewardCoins = coins;
            this.isRewardModalOpen  = true;
          }
          this.toastr.success("Payment approved successfully");
        },
        error :(err)=>{
          this.spinner.hide();
          this.toastr.error("Error approving payment");
        }
      }
      );
      }
    }
  makePayment(loan : Loan,amount :number): void {
    this.amount = amount;
    this.selectedLoan = loan;
    this.isAddMoneyModalOpen = true;
  }



  isDisabled(loanId: string): boolean {
  const amount = this.repaymentAmounts[loanId];
  return  !amount || amount <= 0;
 }

 validateAmount(loan: Loan): void {
  const amount = this.repaymentAmounts[loan.id];
  if (amount > loan.remainingAmount) {
    this.paymentErrors[loan.id] = `Amount exceeds remaining balance of ${this.lendingService.formatCurrency(loan.remainingAmount)}.`;
  } else {
    this.paymentErrors[loan.id] = '';
  }
}




}
