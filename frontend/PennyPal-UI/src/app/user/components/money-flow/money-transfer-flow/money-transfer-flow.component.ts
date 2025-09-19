import { Component } from '@angular/core';
import { Contact, PaymentMethod, Transaction, TransferStep, UserBalance } from '../../../models/money-flow.model';
import { ContactCardComponent } from "../../contacts/contact-card/contact-card.component";
import { SendMoneyModalComponent } from "../send-money-modal/send-money-modal.component";
import { PinConfirmationModalComponent } from "../pin-confirmation-modal/pin-confirmation-modal.component";
import { TransactionStatusModalComponent } from "../transaction-status-modal/transaction-status-modal.component";
import { TransactionReceiptComponent } from "../transaction-receipt/transaction-receipt.component";
import { DecimalPipe } from '@angular/common';
export interface TransferData {
  amount?: number;
  note?: string;
  pin?: string;
}
@Component({
  selector: 'app-money-transfer-flow',
  imports: [ContactCardComponent, SendMoneyModalComponent, PinConfirmationModalComponent, TransactionStatusModalComponent, TransactionReceiptComponent,DecimalPipe],
  templateUrl: './money-transfer-flow.component.html',
  styleUrl: './money-transfer-flow.component.css'
})
export class MoneyTransferFlowComponent {
  contacts: Contact[] = []
  selectedContact: Contact | null = null

  currentStep: TransferStep = { step: "amount" }
  transferData: TransferData = {}
  currentTransaction: Transaction | null = null
  failureReason = ""
  showReceipt = false

  paymentMethods: PaymentMethod[] = []
  selectedPaymentMethod: PaymentMethod | null = null

  ngOnInit() {
  }

  onSendMoney(contact: Contact) {
    this.selectedContact = contact
    this.currentStep = { step: "amount" }
    this.transferData = {}
  }

  onMessage(contact: Contact) {
    
  }

  onAmountConfirmed(data: { amount: number; note: string; paymentMethod: PaymentMethod }) {
    this.transferData = { ...this.transferData, amount: data.amount, note: data.note }
    this.selectedPaymentMethod = data.paymentMethod
    this.currentStep = { step: "pin" }
  }

  onPinConfirmed(pin: string) {
    this.transferData.pin = pin
    this.currentStep = { step: "processing" }
  }

 

  onRetryTransfer() {
    this.currentStep = { step: "pin" }
    this.failureReason = ""
  }

  onTransactionDone() {
    this.onCloseModal()
  }

  onViewReceipt(transaction: Transaction) {
    this.showReceipt = true
  }

  onCloseReceipt() {
    this.showReceipt = false
  }

  onCloseModal() {
    this.currentStep = { step: "amount" }
    this.selectedContact = null
    this.transferData = {}
    this.currentTransaction = null
    this.failureReason = ""
  }

  getStatusModalStatus(): "processing" | "success" | "failed" {
    if (this.currentStep.step === "processing") return "processing"
    if (this.currentStep.step === "success") return "success"
    if (this.currentStep.step === "failed") return "failed"
    return "processing"
  }
}
