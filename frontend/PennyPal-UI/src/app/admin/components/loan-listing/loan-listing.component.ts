import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { Loan } from '../../../user/models/lend.model';
import { LendingService } from '../../../user/services/lending-management.serive';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { LoanCase } from '../../models/admin-lending-model';
import { CaseService } from '../../services/case-management-service';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmDialogComponent } from '../../../shared/components/confirm-dialog/confirm-dialog.component';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
export interface HistoryFilters {
  searchTerm: string
  status: string
  sortOrder: "asc" | "desc"
}
@Component({
  selector: 'app-loan-listing',
  imports: [CommonModule,FormsModule],
  templateUrl: './loan-listing.component.html',
  styleUrl: './loan-listing.component.css'
})
export class LoanListingComponent {
  activeTab = 'history'
  loading = false

  // History data and filters
  loanHistory$!: Observable<Loan[]>
  filteredHistory$!: Observable<Loan[]>
  historyFilters: HistoryFilters = {
    searchTerm: '',
    status: '',
    sortOrder: 'desc',
  }

  // Cases data and filters
  cases$!: Observable<LoanCase[]>
  caseSearchTerm = ''
  selectedCaseStatus = ''

  tabs = [
    { id: 'history', label: 'History', count: 0 },
    { id: 'cases', label: 'Cases', count: 0 },
  ]

  constructor(
    public lendingService: LendingService,
    public caseService: CaseService,
    private dialog : MatDialog,
    private spinner : NgxSpinnerService,
    private toastr : ToastrService
  ) {}

  ngOnInit() {
    // --- History ---
    this.loanHistory$ = this.lendingService.getLoanHistory()
    this.filteredHistory$ = this.loanHistory$

    this.loanHistory$.subscribe((loans) => {
      this.tabs[0].count = loans.length
    })

    // --- Cases ---
    this.cases$ = this.caseService.cases$
    this.caseService.isLoading$.subscribe((isLoading) => {
      this.loading = isLoading
    })

    this.cases$.subscribe((cases) => {
      this.tabs[1].count = cases.length
    })

    // Initial case fetch
    this.caseService.updateFilters({
      keyword: this.caseSearchTerm,
      status: this.selectedCaseStatus,
    })
  }

  // History filter methods
  applyHistoryFilters() {
    this.filteredHistory$ = this.lendingService.getFilteredHistory(this.historyFilters)
  }

  clearHistoryFilters() {
    this.historyFilters = {
      searchTerm: '',
      status: '',
      sortOrder: 'desc',
    }
    this.applyHistoryFilters()
  }

  // Case filter methods
  applyCaseFilters() {
    this.caseService.updateFilters({
      keyword: this.caseSearchTerm,
      status: this.selectedCaseStatus,
    })
  }

  clearCaseFilters() {
    this.caseSearchTerm = ''
    this.selectedCaseStatus = ''
    this.applyCaseFilters()
  }

  // Case actions

  confirmAction(
  title: string,
  message: string,
  confirmText: string,
  cancelText: string,
  onConfirm: () => void
): void {
  const ref = this.dialog.open(ConfirmDialogComponent, {
    width: '400px',
    data: { title, message, confirmText, cancelText }
  });

  ref.afterClosed().subscribe({
    next: (confirmed) => {
      if (confirmed) {
        this.spinner.show();
        onConfirm();
      }
    }
  });
}

  cancelCase(caseId: string): void {
  this.confirmAction(
    'Cancel Case',
    `Are you sure you want to cancel Case #${caseId}?`,
    'Cancel Case',
    'Back',
    () => {
      this.caseService.cancelCase(caseId).subscribe({
        next: () => {
          this.spinner.hide();
          this.toastr.success('Case cancelled', 'SUCCESS', { timeOut: 2000 });
        },
        error: () => this.spinner.hide()
      });
    }
  );
}


  remindUser(loanId: string): void {
  this.confirmAction(
    'Send Reminder',
    `Send another reminder for Loan #${loanId}?`,
    'Send Reminder',
    'Cancel',
    () => {
      this.caseService.sendReminder(loanId).subscribe({
        next: () => {
          this.spinner.hide();
          this.toastr.success('Reminder sent', 'SUCCESS', { timeOut: 2000 });
        },
        error: () => this.spinner.hide()
      });
    }
  );
}


  suspendUser(userName: string, caseId: string, userId : string): void {
  this.confirmAction(
    'Suspend Account',
    `Suspend ${userName}'s account for Case #${caseId}?`,
    'Suspend',
    'Cancel',
    () => {
      this.caseService.suspendUser(userId,caseId).subscribe({
        next: () => {
          this.spinner.hide();
          this.toastr.success('User suspended', 'SUCCESS', { timeOut: 2000 });
        },
        error: () => this.spinner.hide()
      });
    }
  );
}


  // Export functionality
  exportData() {
    if (this.activeTab === 'history') {
      this.exportHistory()
    } else if (this.activeTab === 'cases') {
      this.exportCases()
    }
  }



  private exportHistory() {
    this.filteredHistory$.subscribe((loans) => {
      const csvContent = this.convertHistoryToCSV(loans)
      this.downloadCSV(csvContent, 'loan-history.csv')
    })
  }

  private exportCases() {
    this.cases$.subscribe((cases) => {
      const csvContent = this.convertCasesToCSV(cases)
      this.downloadCSV(csvContent, 'legal-cases.csv')
    })
  }

  private convertHistoryToCSV(loans: Loan[]): string {
    const headers = ['Type', 'Name', 'Amount', 'Loan Date', 'Due Date', 'Status']
    const rows = loans.map((loan) => [
      loan.type || 'N/A',
      loan.type === 'lent' ? loan.borrowerName : loan.lenderName,
      loan.amount.toString(),
      loan.loanDate,
      loan.repaymentDeadline,
      loan.repaymentStatus,
    ])
    return [headers, ...rows].map((row) => row.join(',')).join('\n')
  }

  private convertCasesToCSV(cases: LoanCase[]): string {
    const headers = ['Case ID', 'Borrower', 'Lender', 'Amount', 'Status', 'Filed Date', 'Reason']
    const rows = cases.map((c) => [
      c.id,
      c.borrowerName,
      c.lenderName,
      c.amount.toString(),
      c.status,
      c.filedDate,
      c.reason,
    ])
    return [headers, ...rows].map((row) => row.join(',')).join('\n')
  }

  private downloadCSV(content: string, filename: string) {
    const blob = new Blob([content], { type: 'text/csv' })
    const url = window.URL.createObjectURL(blob)
    const link = document.createElement('a')
    link.href = url
    link.download = filename
    link.click()
    window.URL.revokeObjectURL(url)
  }
}
