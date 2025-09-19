import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { LendingRequest, RequestApproveForm, SentLentRequest } from '../../../models/lend.model';
import { Observable } from 'rxjs';
import { LendingService } from '../../../services/lending-management.serive';
import { SendMoneyModalComponent } from "../../money-flow/send-money-modal/send-money-modal.component";
import { PinConfirmationModalComponent } from "../../money-flow/pin-confirmation-modal/pin-confirmation-modal.component";
import { PaymentMethod } from '../../../models/money-flow.model';
import { ContactManagementService } from '../../../services/contact-management.service';
import { ToastrService } from 'ngx-toastr';
import { NgxSpinnerService } from 'ngx-spinner';
import { SendLentMoneyComponent } from "../../../modals/send-lent-money/send-lent-money.component";

@Component({
  selector: 'app-lending-requests-received',
  imports: [CommonModule,  PinConfirmationModalComponent, SendLentMoneyComponent],
  templateUrl: './lending-requests-received.component.html',
  styleUrl: './lending-requests-received.component.css'
})
export class LendingRequestsReceivedComponent {
  requestsReceived$!: Observable<LendingRequest[]>
  loading$!: Observable<boolean>
  isAddMoneyModalOpen = false
  isPinConfirmationOpen = false
  amount : number = 0;
  notes : string = '';
  pin : string = '';
  

  selectedPaymentMethod: PaymentMethod | null = null
  paymentMethods: PaymentMethod[] = []
  selectedRequest : LendingRequest | null = null

  constructor(public lendingService: LendingService,private cardService : ContactManagementService,private toastr : ToastrService,private spinner : NgxSpinnerService) {}

  ngOnInit() {
    this.requestsReceived$ = this.lendingService.lendingRequestReceived$
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
      this.amount = data.amount;
      this.notes = data.note;
    }

    onPinConfirmed(pin: string) {
    this.isPinConfirmationOpen = false;
    this.pin = pin;
    if(this.amount != 0 && this.selectedPaymentMethod != null && pin != '' && this.selectedRequest){
      this.spinner.show();
      const form : RequestApproveForm={
        recipientId : this.selectedRequest.senderId,
        amount : this.selectedRequest.amount,
        note : this.notes,
        pin : this.pin,
        paymentMethodId : this.selectedPaymentMethod.id
      }
      this.lendingService.approveRequest(form,this.selectedRequest.id).subscribe({
      next : (res) => {
        this.spinner.hide();
        this.toastr.success("Request approved successfully");
      },
      error :(err)=>{
        this.spinner.hide();
        this.toastr.error("Error approving request");
      }
    }
    )
    }
  }

  approveRequest(request: LendingRequest) {
   this.selectedRequest = request;
   this.amount = this.selectedRequest.amount
   this.isAddMoneyModalOpen = true;
  }

  rejectRequest(requestId: string) {
    this.lendingService.rejectRequest(requestId).subscribe({
      next: (success) => {
        if (success) {
        }
      },
      error: (error) => {
        console.error("Error rejecting request:", error)
      },
    })
  }
}
