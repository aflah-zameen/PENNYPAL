import { Component } from '@angular/core';
import { LendingSummaryComponent } from "../../components/lending/lending-summary/lending-summary.component";
import { LendingRequestsSentComponent } from "../../components/lending/lending-requests-sent/lending-requests-sent.component";
import { LendingRequestsReceivedComponent } from "../../components/lending/lending-requests-received/lending-requests-received.component";
import { LoansToRepayComponent } from "../../components/lending/loans-to-repay/loans-to-repay.component";
import { LoansToCollectComponent } from "../../components/lending/loans-to-collect/loans-to-collect.component";
import { LendingService } from '../../services/lending-management.serive';
import { LendingSummary } from '../../models/lend.model';
import { LendingSummaryCardTemplateComponent } from "../../components/lending-summary-card-template/lending-summary-card-template.component";

@Component({
  selector: 'app-lending-management',
  imports: [LendingSummaryComponent, LendingRequestsSentComponent, LendingRequestsReceivedComponent, LoansToRepayComponent, LoansToCollectComponent, LendingSummaryCardTemplateComponent],
  templateUrl: './lending-management.component.html',
  styleUrl: './lending-management.component.css'
})
export class LendingManagementComponent {
  summary!: LendingSummary;

  constructor(private lendingService: LendingService) {}

  ngOnInit() {
    this.lendingService.fetchLendingSummary().subscribe(data => {
      this.summary = data;
    });

    this.lendingService.fetchLendingRequestsSent().subscribe();
    this.lendingService.fetchLoansToCollect().subscribe();
  }
}
