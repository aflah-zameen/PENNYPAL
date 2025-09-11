import { Component } from '@angular/core';
import { LendingSummary } from '../../models/lend.model';
import { LendingService } from '../../services/lending-management.serive';
import { LendingSummaryCardTemplateComponent } from "../../components/lending-summary-card-template/lending-summary-card-template.component";
import { LendingRequestsReceivedComponent } from "../../components/lending/lending-requests-received/lending-requests-received.component";
import { LoansToRepayComponent } from "../../components/lending/loans-to-repay/loans-to-repay.component";

@Component({
  selector: 'app-borrowing-management',
  imports: [LendingSummaryCardTemplateComponent, LendingRequestsReceivedComponent, LoansToRepayComponent],
  templateUrl: './borrowing-management.component.html',
  styleUrl: './borrowing-management.component.css'
})
export class BorrowingManagementComponent {
  summary!: LendingSummary;

  constructor(private lendingService: LendingService) {}

  ngOnInit() {
    this.lendingService.fetchLendingSummary().subscribe(data => {
      this.summary = data;
    });

    this.lendingService.fetchLendingRequestsReceived().subscribe();
    this.lendingService.fetchLoansToPay().subscribe();
  }
}
