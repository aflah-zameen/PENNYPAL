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

  constructor(private presenceService: PresenceService, private contactManagementService: ContactManagementService) {
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
  transferData: any = {}
  currentTransaction: Transaction | null = null
  selectedPaymentMethod: PaymentMethod | null = null
  failureReason = ""
  showReceipt = false

  ngOnInit() {
    // this.loadMockData();
    this.loadData();
    this.filteredContacts = this.contacts
  }

  // loadMockData() {
  //   // Mock contacts data
  //   this.contacts = [
  //     {
  //       id: "1",
  //       name: "Sarah Johnson",
  //       email: "sarah.johnson@email.com",
  //       avatar: "/placeholder.svg?height=64&width=64",
  //       isOnline: true,
  //     },
  //     {
  //       id: "2",
  //       name: "Michael Chen",
  //       email: "michael.chen@email.com",
  //       avatar: "/placeholder.svg?height=64&width=64",
  //       isOnline: false,
  //     },
  //     {
  //       id: "3",
  //       name: "Emily Rodriguez",
  //       email: "emily.rodriguez@email.com",
  //       avatar: "/placeholder.svg?height=64&width=64",
  //       isOnline: true,
  //     },
  //     {
  //       id: "4",
  //       name: "David Kim",
  //       email: "david.kim@email.com",
  //       avatar: "/placeholder.svg?height=64&width=64",
  //       isOnline: true,
  //     },
  //     {
  //       id: "5",
  //       name: "Lisa Thompson",
  //       email: "lisa.thompson@email.com",
  //       avatar: "/placeholder.svg?height=64&width=64",
  //       isOnline: false,
  //     },
  //     {
  //       id: "6",
  //       name: "James Wilson",
  //       email: "james.wilson@email.com",
  //       avatar: "/placeholder.svg?height=64&width=64",
  //       isOnline: true,
  //     },
  //   ]

  //   this.recentContacts = this.contacts.slice(0, 4)

  //   // Mock transactions data
  //   this.transactions = [
  //     {
  //       id: "1",
  //       senderId: "current-user",
  //       recipientId: "1",
  //       amount: 250,
  //       type: "sent",
  //       timestamp: new Date(Date.now() - 86400000),
  //       status: "completed",
  //     },
  //     {
  //       id: "2",
  //       senderId: "1",
  //       recipientId: "current-user",
  //       amount: 100,
  //       type: "received",
  //       timestamp: new Date(Date.now() - 172800000),
  //       status: "completed",
  //     },
  //     {
  //       id: "3",
  //       senderId: "current-user",
  //       recipientId: "2",
  //       amount: 500,
  //       type: "sent",
  //       timestamp: new Date(Date.now() - 259200000),
  //       status: "pending",
  //     },
  //     {
  //       id: "4",
  //       senderId: "3",
  //       recipientId: "current-user",
  //       amount: 75,
  //       type: "received",
  //       timestamp: new Date(Date.now() - 345600000),
  //       status: "completed",
  //     },
  //   ]

  //   // Mock payment methods
  //   this.paymentMethods = [
  //     {
  //       id: "wallet-1",
  //       type: "wallet",
  //       name: "Primary Wallet",
  //       displayName: "Primary Wallet",
  //       balance: 25000,
  //       icon: "wallet",
  //       isDefault: true,
  //       isActive: true,
  //     },
  //     {
  //       id: "card-1",
  //       type: "card",
  //       name: "HDFC Bank Credit Card",
  //       displayName: "HDFC Credit Card",
  //       balance: 50000,
  //       cardNumber: "1234567890123456",
  //       icon: "card",
  //       isDefault: false,
  //       isActive: true,
  //     },
  //     {
  //       id: "card-2",
  //       type: "card",
  //       name: "SBI Debit Card",
  //       displayName: "SBI Debit Card",
  //       balance: 15000,
  //       cardNumber: "9876543210987654",
  //       icon: "card",
  //       isDefault: false,
  //       isActive: true,
  //     },
  //     {
  //       id: "bank-1",
  //       type: "bank",
  //       name: "ICICI Bank Account",
  //       displayName: "ICICI Savings",
  //       balance: 75000,
  //       bankName: "ICICI Bank",
  //       icon: "bank",
  //       isDefault: false,
  //       isActive: true,
  //     },
  //     {
  //       id: "card-3",
  //       type: "card",
  //       name: "Axis Bank Credit Card",
  //       displayName: "Axis Credit Card",
  //       balance: 0,
  //       cardNumber: "5555444433332222",
  //       icon: "card",
  //       isDefault: false,
  //       isActive: false,
  //     },
  //   ]
  // }
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
    this.transferData = {}
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
  //       type: "sent",
  //     }

  //     // Update selected payment method balance
  //     this.selectedPaymentMethod.balance = (this.selectedPaymentMethod.balance || 0) - this.transferData.amount

  //     // Update the payment method in the array
  //     const methodIndex = this.paymentMethods.findIndex((m) => m.id === this.selectedPaymentMethod!.id)
  //     if (methodIndex !== -1) {
  //       this.paymentMethods[methodIndex] = { ...this.selectedPaymentMethod }
  //     }

  //     // Add transaction to history
  //     this.transactions.unshift(this.currentTransaction)

  //     this.currentStep = { step: "success" }
  //   } else {
  //     // Transaction failed
  //     this.failureReason = "Invalid PIN entered. Please try again."
  //     this.currentStep = { step: "failed" }
  //   }
  //   this.isTransactionStatusOpen= true;
  // }

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
    this.transferData = {}
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

  // getContactTransactions(contactId: string): Transaction[] {
  //   return this.transactions.filter((t) => t.recipientId === contactId || t.senderId === contactId)
  // }
}
