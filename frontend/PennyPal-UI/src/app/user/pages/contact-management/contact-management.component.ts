import { Component, OnInit } from '@angular/core';
import { Contact } from '../../models/money-flow.model';
import { RecentContactsComponent } from "../../components/contacts/recent-contacts/recent-contacts.component";
import { ContactCardComponent } from "../../components/contacts/contact-card/contact-card.component";
import { ChatDrawerComponent } from "../../components/contacts/chat-drawer/chat-drawer.component";
import { SearchBarComponent } from "../../components/contacts/search-bar/search-bar.component";
import { CommonModule } from '@angular/common';
import { PaymentMethod, Transaction, TransferStep, UserBalance } from '../../models/money-flow.model';
import { SendMoneyModalComponent } from "../../components/money-flow/send-money-modal/send-money-modal.component";
import { PinConfirmationModalComponent } from "../../components/money-flow/pin-confirmation-modal/pin-confirmation-modal.component";
import { TransactionStatusModalComponent } from "../../components/money-flow/transaction-status-modal/transaction-status-modal.component";
import { TransactionReceiptComponent } from "../../components/money-flow/transaction-receipt/transaction-receipt.component";
import { PresenceService } from '../../services/websocket/presence-service';
import { ContactManagementService } from '../../services/contact-management.service';
import { CreditCard } from '../../models/CreditCard.model';
import { SentLentRequest } from '../../models/lend.model';
import { LendingService } from '../../services/lending-management.serive';
import { NgxSpinner, NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
interface TransferData {
  amount: number;
  note: string;
  pin?: string;
}
@Component({
  selector: 'app-contact-management',
  imports: [RecentContactsComponent, ContactCardComponent, ChatDrawerComponent, SearchBarComponent, CommonModule, SendMoneyModalComponent, PinConfirmationModalComponent, TransactionStatusModalComponent, TransactionReceiptComponent],
  templateUrl: './contact-management.component.html',
  styleUrl: './contact-management.component.css'
})
export class ContactManagementComponent implements OnInit {
  contacts: Contact[] = []
  // recentContacts: Contact[] = []
  filteredContacts: Contact[] = []
  // transactions: Transaction[] = []
  paymentMethods: PaymentMethod[] = []

  constructor(
    private presenceService: PresenceService,
    private contactManagementService: ContactManagementService,
    private lendingService : LendingService,
    private spinner : NgxSpinnerService,
    private toastr : ToastrService
  ) {
   this.presenceService.getPresenceUpdates().subscribe((presenceUpdates) => {
    presenceUpdates.forEach(update => {
        const contact = this.contacts.find(c => c.id === update.userId);
        if (contact) {
          contact.isOnline = update.online;
        }
      });

      this.filteredContacts = [...this.contacts]; // Refresh the filtered list
    });
  }

  isChatOpen = false
  isMoneyFlowOpen = false
  isPinConfirmationOpen = false
  isTransactionStatusOpen = false
  isTransactionReceiptOpen = false
  selectedContact: Contact | null = null
  searchTerm = ""

  // Money Transfer Flow Properties
  userBalance: UserBalance = { available: 25000, currency: "INR" }
  currentStep: TransferStep = { step: "amount" }
  transferData: TransferData = {amount : 0, note : ""}
  currentTransaction: Transaction | null = null
  selectedPaymentMethod: PaymentMethod | null = null
  failureReason = ""
  showReceipt = false

  ngOnInit() {
    this.loadData();
    this.filteredContacts = this.contacts
  }

  loadData(){
    this.contactManagementService.getContacts().subscribe({
      next: (contacts) => {
        this.contacts = contacts;
        this.filteredContacts = contacts;
      },
      error: (err) => {
        console.error("Failed to load contacts", err);
      }
    });

    this.contactManagementService.getPaymentMethods().subscribe({
      next: (methods) => {
        this.paymentMethods = methods;
      },
      error: (err) => {
        console.error("Failed to load payment methods", err);
      }
    });
  }

  onSearchChange(searchTerm: string) {
    this.searchTerm = searchTerm
    if (searchTerm.trim()) {
      this.filteredContacts = this.contacts.filter(
        (contact) =>
          contact.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
          contact.email.toLowerCase().includes(searchTerm.toLowerCase()),
      )
    } else {
      this.filteredContacts = this.contacts
    }
  }

  // Money Transfer Flow Methods
  onSendMoney(contact: Contact) {
    this.isMoneyFlowOpen = true;
    this.selectedContact = contact
    this.currentStep = { step: "amount" }
    this.transferData = {amount : 0, note : ""}
    this.selectedPaymentMethod = null
  }

  onAmountConfirmed(data: { amount: number; note: string; paymentMethod: PaymentMethod }) {
    this.isMoneyFlowOpen = false;
    this.isPinConfirmationOpen = true;    
    this.transferData = { ...this.transferData, amount: data.amount, note: data.note }
    this.selectedPaymentMethod = data.paymentMethod
  }

  onPinConfirmed(pin: string) {
    this.transferData.pin = pin
    this.isPinConfirmationOpen = false;
console.log(
  "Contact:", this.selectedContact,
  "Payment Method:", this.selectedPaymentMethod,
  "Amount:", this.transferData.amount,
  "Note:", this.transferData.note,
  "PIN:", this.transferData.pin,
  "Full Transfer Data:", this.transferData
);
    
    if(!this.selectedContact || !this.selectedPaymentMethod || !this.transferData.amount || this.transferData.note == null || !this.transferData.pin) {
      this.failureReason = "Missing transfer details.";
      this.currentStep = { step: "failed" };
      this.isTransactionStatusOpen = true;
      
      return;
    }
    this.contactManagementService.transferMoney(
      this.selectedContact!.id,
      this.transferData.amount,
      this.transferData.note,
      this.selectedPaymentMethod!.id,
      this.transferData.pin
    ).subscribe({
      next: (transaction) => {
        this.currentTransaction = transaction;
        this.currentStep = { step: "processing" };
        this.isTransactionStatusOpen = true;
        this.isMoneyFlowOpen = false;

        // Update selected payment method balance
        if (this.selectedPaymentMethod) {
          this.selectedPaymentMethod.balance = (this.selectedPaymentMethod.balance || 0) - this.transferData.amount;
          // Update the payment method in the array
          const methodIndex = this.paymentMethods.findIndex((m) => m.id === this.selectedPaymentMethod!.id);
          if (methodIndex !== -1) {
            this.paymentMethods[methodIndex] = { ...this.selectedPaymentMethod };
          }
        }
      }
      ,
      error: (err) => {
        console.error("Transfer failed", err);
        this.failureReason = "Transfer failed. Please try again.";
        this.currentStep = { step: "failed" };
        this.isTransactionStatusOpen = true;
      }
    });
  }

  onRetryTransfer() {
    this.currentStep = { step: "pin" }
    this.failureReason = ""
  }

  onTransactionDone() {
    this.onCloseTransferModal()
  }

  onViewReceipt(transaction: Transaction) {
    this.isTransactionReceiptOpen = true
  }

  onCloseReceipt() {
    this.isTransactionReceiptOpen = false
  }

  onCloseTransferModal() {
    this.isTransactionReceiptOpen = false
    this.isTransactionStatusOpen = false;
    this.selectedContact = null
    this.transferData = {amount : 0, note : ""}
    this.currentTransaction = null
    this.selectedPaymentMethod = null
    this.failureReason = ""
  }

  getStatusModalStatus(): "processing" | "success" | "failed" {
    if (this.currentStep.step === "processing") return "processing"
    if (this.currentStep.step === "success") return "success"
    if (this.currentStep.step === "failed") return "failed"
    return "processing"
  }

  // Chat Methods
  onMessage(contact: Contact) {
    this.selectedContact = contact
    this.isChatOpen = true
  }

  onCloseChat() {
    this.isChatOpen = false
    this.selectedContact = null
  }

  moneyRequest(form : SentLentRequest){
    this.spinner.show();
    this.lendingService.sentMoneyRequest(form).subscribe(()=>{
      this.spinner.hide();
      this.toastr.success("The request sent successfully");
    })
  }

  // getContactTransactions(contactId: string): Transaction[] {
  //   return this.transactions.filter((t) => t.recipientId === contactId || t.senderId === contactId)
  // }
}
