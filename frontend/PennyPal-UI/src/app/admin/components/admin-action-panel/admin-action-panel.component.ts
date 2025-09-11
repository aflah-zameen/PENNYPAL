import { Component, Input } from '@angular/core';
import { AdminAction } from '../../models/admin-lending-model';
import { Observable } from 'rxjs';
import { CaseService } from '../../services/case-management-service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-admin-action-panel',
  imports: [CommonModule,FormsModule],
  templateUrl: './admin-action-panel.component.html',
  styleUrl: './admin-action-panel.component.css'
})
export class AdminActionPanelComponent {
  @Input() isAdmin = true

  adminActions$!: Observable<AdminAction[]>
  loading$!: Observable<boolean>

  showSuspendModal = false
  showBlockModal = false

  suspendForm = {
    userId: "",
    duration: 30,
    reason: "",
  }

  blockForm = {
    userId: "",
    reason: "",
  }

  constructor(public caseService: CaseService) {}

  ngOnInit() {
    this.adminActions$ = this.caseService.adminActions$
    this.loading$ = this.caseService.loading$
  }

  openSuspendModal() {
    this.showSuspendModal = true
  }

  closeSuspendModal() {
    this.showSuspendModal = false
    this.suspendForm = { userId: "", duration: 30, reason: "" }
  }

  openBlockModal() {
    this.showBlockModal = true
  }

  closeBlockModal() {
    this.showBlockModal = false
    this.blockForm = { userId: "", reason: "" }
  }

  openBanModal() {
    console.log("Opening ban modal...")
    // Implementation for ban modal
  }

  suspendUser() {
    if (this.suspendForm.userId && this.suspendForm.reason) {
      this.caseService
        .suspendUser(this.suspendForm.userId, this.suspendForm.reason, this.suspendForm.duration)
        .subscribe({
          next: (success) => {
            if (success) {
              this.closeSuspendModal()
              console.log("User suspended successfully")
            }
          },
          error: (error) => {
            console.error("Error suspending user:", error)
          },
        })
    }
  }

  blockUser() {
    if (this.blockForm.userId && this.blockForm.reason) {
      this.caseService.blockUser(this.blockForm.userId, this.blockForm.reason).subscribe({
        next: (success) => {
          if (success) {
            this.closeBlockModal()
            console.log("User blocked successfully")
          }
        },
        error: (error) => {
          console.error("Error blocking user:", error)
        },
      })
    }
  }
}
