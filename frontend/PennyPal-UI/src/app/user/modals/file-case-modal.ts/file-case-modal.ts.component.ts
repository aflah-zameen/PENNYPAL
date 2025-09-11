import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ModalOverlayComponent } from "../modal-overlay/modal-overlay.component";
import { Loan } from '../../models/lend.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { LendingService } from '../../services/lending-management.serive';

@Component({
  selector: 'app-file-case-modal',
  imports: [ModalOverlayComponent,CommonModule,FormsModule],
  templateUrl: './file-case-modal.ts.component.html',
  styleUrl: './file-case-modal.ts.component.css'
})
export class FileCaseModalComponent {
@Input() isOpen = false;
@Input() loan!: Loan;
@Output() close = new EventEmitter<void>();
@Output() caseSubmitted = new EventEmitter<{ loanId: string; reason: string }>();

constructor(protected lendingService : LendingService){}

reason = '';
reasonError = '';
isSubmitting = false;

submitCase() {
  if (!this.reason || this.reason.trim().length < 10) {
    this.reasonError = 'Please provide a meaningful reason (at least 10 characters).';
    return;
  }

  this.isSubmitting = true;
  this.reasonError = '';

  setTimeout(() => {
    this.caseSubmitted.emit({ loanId: this.loan.id, reason: this.reason.trim() });
    this.isSubmitting = false;
    this.onClose();
  }, 1000); // Simulate async call
}

onClose() {
  this.close.emit();
  this.reason = '';
  this.reasonError = '';
  this.isSubmitting = false;
}

}
