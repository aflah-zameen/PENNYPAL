import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Transaction } from '../../../models/transaction-management.model';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TransactionService } from '../../../services/transaction-management.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-transaction-detail',
  imports: [CommonModule,ReactiveFormsModule],
  templateUrl: './transaction-detail.component.html',
  styleUrl: './transaction-detail.component.css'
})
export class TransactionDetailComponent {
  @Input() transaction: Transaction | null = null
  @Input() isOpen = false
  @Output() closeDrawer = new EventEmitter<void>()

  notesForm: FormGroup
  showConfirmDialog = false
  confirmTitle = ""
  confirmMessage = ""
  confirmAction: (() => void) | null = null

  constructor(
    private fb: FormBuilder,
    public transactionService: TransactionService,
  ) {
    this.notesForm = this.fb.group({
      adminNotes: [""],
    })
  }

  ngOnInit() {
    if (this.transaction?.adminNotes) {
      this.notesForm.patchValue({
        adminNotes: this.transaction.adminNotes,
      })
    }
  }

  close() {
    this.closeDrawer.emit()
  }

  saveNotes() {
    if (this.transaction && this.notesForm.valid) {
      const notes = this.notesForm.value.adminNotes
      this.transactionService.addAdminNotes(this.transaction.id, notes).subscribe(() => {
        // Show success message or update UI
      })
    }
  }

  reverseTransaction() {
    this.showConfirmation(
      "Reverse Transaction",
      "Are you sure you want to reverse this transaction? This action cannot be undone.",
      () => {
        if (this.transaction) {
          this.transactionService.reverseTransaction(this.transaction.id).subscribe(() => {
            this.close()
          })
        }
      },
    )
  }

  retryTransaction() {
    if (this.transaction) {
      this.transactionService.retryTransaction(this.transaction.id).subscribe(() => {
        this.close()
      })
    }
  }

  markAsSuspicious() {
    this.showConfirmation("Mark as Suspicious", "Are you sure you want to mark this transaction as suspicious?", () => {
      if (this.transaction) {
        this.transactionService.markAsSuspicious(this.transaction.id).subscribe(() => {
          this.close()
        })
      }
    })
  }

  private showConfirmation(title: string, message: string, action: () => void) {
    this.confirmTitle = title
    this.confirmMessage = message
    this.confirmAction = action
    this.showConfirmDialog = true
  }

  cancelConfirm() {
    this.showConfirmDialog = false
    this.confirmAction = null
  }

  executeConfirm() {
    if (this.confirmAction) {
      this.confirmAction()
    }
    this.showConfirmDialog = false
    this.confirmAction = null
  }

  getTypeClass(type: string): string {
    const classes = {
      Income: "bg-green-100 text-green-800",
      Expense: "bg-red-100 text-red-800",
      Transfer: "bg-blue-100 text-blue-800",
      "Wallet Recharge": "bg-purple-100 text-purple-800",
      Lending: "bg-yellow-100 text-yellow-800",
      Saving: "bg-indigo-100 text-indigo-800",
    }
    return classes[type as keyof typeof classes] || "bg-gray-100 text-gray-800"
  }

  getStatusClass(status: string): string {
    const classes = {
      Success: "bg-green-100 text-green-800",
      Pending: "bg-yellow-100 text-yellow-800",
      Failed: "bg-red-100 text-red-800",
      Reversed: "bg-gray-100 text-gray-800",
    }
    return classes[status as keyof typeof classes] || "bg-gray-100 text-gray-800"
  }
}
