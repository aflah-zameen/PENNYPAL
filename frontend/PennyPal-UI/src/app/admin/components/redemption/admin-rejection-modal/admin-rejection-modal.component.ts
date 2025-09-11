import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-admin-rejection-modal',
  imports: [FormsModule,CommonModule],
  templateUrl: './admin-rejection-modal.component.html',
  styleUrl: './admin-rejection-modal.component.css'
})
export class AdminRejectionModalComponent {
  @Input() isOpen = false
  @Input() isProcessing = false
  @Output() close = new EventEmitter<void>()
  @Output() confirm = new EventEmitter<string>()

  rejectionReason = ""

  onClose() {
    if (!this.isProcessing) {
      this.rejectionReason = ""
      this.close.emit()
    }
  }

  onConfirm() {
    if (this.rejectionReason.trim() && !this.isProcessing) {
      this.confirm.emit(this.rejectionReason.trim())
    }
  }
}
