import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { Loan, RegisterCase } from '../../../models/lend.model';
import { LendingService } from '../../../services/lending-management.serive';
import { CommonModule } from '@angular/common';
import { FileCaseModalComponent } from '../../../modals/file-case-modal.ts/file-case-modal.ts.component';

@Component({
  selector: 'app-loans-to-collect',
  imports: [CommonModule, FileCaseModalComponent],
  templateUrl: './loans-to-collect.component.html',
  styleUrl: './loans-to-collect.component.css'
})
export class LoansToCollectComponent {
  loansToCollect$!: Observable<Loan[]>
  loading$!: Observable<boolean>

  selectedLoan : Loan | null= null;
  isFileCaseModalOpen : boolean = false;

  constructor(public lendingService: LendingService) {}

  ngOnInit() {
    this.loansToCollect$ = this.lendingService.loansToCollect$
    this.loading$ = this.lendingService.loading$
  }

  sendReminder(loanId: string) {
    this.lendingService.sendReminder(loanId).subscribe({
      next: (success) => {
        if (success) {
          console.log("Reminder sent successfully")
        }
      },
      error: (error) => {
        console.error("Error sending reminder:", error)
      },
    })
  }

  fileCase(register : RegisterCase) {
    this.lendingService.fileCase(register).subscribe({
      next: (success) => {
        if (success) {
          console.log("Case filed successfully")
        }
      },
      error: (error) => {
        console.error("Error filing case:", error)
      },
    })
  }

  getNextReminderTime(loan: Loan): string | null {
    if (!loan.lastReminderSentAt) return null;
    const nextTime = new Date(new Date(loan.lastReminderSentAt).getTime() + 24 * 60 * 60 * 1000);
    return nextTime.toLocaleString(); // Customize format if needed
  }


isReminderCooldownActive(loan: Loan): boolean {
  if (!loan.lastReminderSentAt) return false;
  const lastSent = new Date(loan.lastReminderSentAt).getTime();
  const now = Date.now();
  const diffInMs = now - lastSent;
  return diffInMs < 24 * 60 * 60 * 1000; // 24 hours
}

openFileCaseModal(loan : Loan){
  this.selectedLoan = loan;
  this.isFileCaseModalOpen = true;
}


}
