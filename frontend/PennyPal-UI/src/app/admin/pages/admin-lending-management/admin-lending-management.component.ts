import { Component } from '@angular/core';
import { LoanSummaryComponent } from "../../components/loan-summary/loan-summary.component";
import { LoanListingComponent } from "../../components/loan-listing/loan-listing.component";


@Component({
  selector: 'app-admin-lending-management',
  imports: [LoanSummaryComponent, LoanListingComponent],
  templateUrl: './admin-lending-management.component.html',
  styleUrl: './admin-lending-management.component.css'
})
export class AdminLendingManagementComponent {
}
