import { Component } from '@angular/core';
import { LendingSummary } from '../../../user/models/lend.model';
import { LendingService } from '../../../user/services/lending-management.serive';
import { Observable } from 'rxjs';
import { CommonModule } from '@angular/common';
import { CaseService } from '../../services/case-management-service';
export interface SummaryStats {
  totalLent: number
  totalBorrowed: number
  overdueCount: number
  pendingCases: number
}

@Component({
  selector: 'app-loan-summary',
  imports: [CommonModule],
  templateUrl: './loan-summary.component.html',
  styleUrl: './loan-summary.component.css'
})
export class LoanSummaryComponent {
  summaryStats$!: Observable<SummaryStats>

  constructor(
    public lendingService: LendingService,
    private caseService: CaseService,
  ) {}

  ngOnInit() {
    this.summaryStats$ = this.lendingService.getSummaryStats()
  }
}
