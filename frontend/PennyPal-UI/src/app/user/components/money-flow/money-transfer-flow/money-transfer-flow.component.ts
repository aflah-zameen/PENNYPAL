import { Component } from '@angular/core';
import { Contact, PaymentMethod, Transaction, TransferStep, UserBalance } from '../../../models/money-flow.model';
import { ContactCardComponent } from "../../contacts/contact-card/contact-card.component";
import { SendMoneyModalComponent } from "../send-money-modal/send-money-modal.component";
import { PinConfirmationModalComponent } from "../pin-confirmation-modal/pin-confirmation-modal.component";
import { TransactionStatusModalComponent } from "../transaction-status-modal/transaction-status-modal.component";
import { TransactionReceiptComponent } from "../transaction-receipt/transaction-receipt.component";
import { DecimalPipe } from '@angular/common';

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
  transferData: any = {}
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
    console.log("Message:", contact.name)
    // Implement messaging functionality
  }

  onAmountConfirmed(data: { amount: number; note: string; paymentMethod: PaymentMethod }) {
    this.transferData = { ...this.transferData, amount: data.amount, note: data.note }
    this.selectedPaymentMethod = data.paymentMethod
    this.currentStep = { step: "pin" }
  }

  onPinConfirmed(pin: string) {
    this.transferData.pin = pin
    this.currentStep = { step: "processing" }

    // Simulate transaction processing
    // setTimeout(() => {
    //   this.processTransaction()
    // }, 2000)
  }

  // processTransaction() {
  //   // Simulate PIN validation (90% success rate for demo)
  //   const isValidPin = Math.random() > 0.1

  //   if (isValidPin && this.selectedPaymentMethod) {
  //     // Create successful transaction
  //     this.currentTransaction = {
  //       id: "TXN" + Date.now(),
  //       senderId: "current-user",
  //       recipientId: this.selectedContact!.id,
  //       amount: this.transferData.amount,
  //       note: this.transferData.note,
  //       timestamp: new Date(),
  //       status: "completed",
  //     }

  //     // Update selected payment method balance
  //     this.selectedPaymentMethod.balance = (this.selectedPaymentMethod.balance || 0) - this.transferData.amount

  //     // Update the payment method in the array
  //     const methodIndex = this.paymentMethods.findIndex((m) => m.id === this.selectedPaymentMethod!.id)
  //     if (methodIndex !== -1) {
  //       this.paymentMethods[methodIndex] = { ...this.selectedPaymentMethod }
  //     }

  //     this.currentStep = { step: "success" }
  //   } else {
  //     // Transaction failed
  //     this.failureReason = "Invalid PIN entered. Please try again."
  //     this.currentStep = { step: "failed" }
  //   }
  // }

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
