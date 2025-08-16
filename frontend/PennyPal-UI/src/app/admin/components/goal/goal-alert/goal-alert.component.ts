import { Component, OnInit } from '@angular/core';
import { GoalAlert, WithdrawalRequest } from '../../../models/goal-management.model';
import { map, Observable, of } from 'rxjs';
import { GoalDashboardService } from '../../../services/goal-management.service';
import { CommonModule } from '@angular/common';
import { ToastrService } from 'ngx-toastr';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmDialogComponent } from '../../../../shared/components/confirm-dialog/confirm-dialog.component';

@Component({
  selector: 'app-goal-alert',
  imports: [CommonModule],
  templateUrl: './goal-alert.component.html',
  styleUrl: './goal-alert.component.css'
})
export class GoalAlertComponent implements OnInit {
  requests$: Observable<WithdrawalRequest[]> = of([]); // Default to empty array

  constructor(public goalDashboardService: GoalDashboardService,private toastr  : ToastrService,private matDialog: MatDialog) {}

  ngOnInit() {
    this.requests$ = this.goalDashboardService.withdrawalRequests$
  }

  trackByAlertId(index: number, withdrawalRequest: WithdrawalRequest): string {
    return withdrawalRequest.id
  }



  approveWithdrawal(withdrawalId : string) {
    if (withdrawalId) {
      this.matDialog.open(ConfirmDialogComponent, {
        width: '400px',
        data: {
          title: 'Confirm Approval',
          message: `Are you sure you want to approve this withdrawal request?`,
          confirmText: 'Approve',
          cancelText: 'Cancel'
        }
      }).afterClosed().subscribe((confirmed) => {
        if (confirmed) {
          this.goalDashboardService.approveWithdrawal(withdrawalId, "Approved via alert").subscribe((flag) => {
            if (flag) {
              this.toastr.success("Withdrawal request approved successfully.");
            } else {
              this.toastr.error("Failed to approve withdrawal request.");
            }
          });
        }
      });
    }
  }

  rejectWithdrawal(withdrawalId : string) {
    if (withdrawalId) {
      this.matDialog.open(ConfirmDialogComponent, {
        width: '400px',
        data: {
          title: 'Confirm Rejection',
          message: `Are you sure you want to reject this withdrawal request?`,
          confirmText: 'Reject',
          cancelText: 'Cancel'
        }
      }).afterClosed().subscribe((confirmed) => {
        if (confirmed) {
          this.goalDashboardService.rejectWithdrawal(withdrawalId, "Rejected via alert").subscribe((flag)=>{
              if(flag) {
                this.toastr.success("Withdrawal request rejected successfully.");
              } else {
                this.toastr.error("Failed to reject withdrawal request.");
              }
            });
        }
      });
    }
  }

  viewGoalDetails(withdrawalId: string) {
    console.log("View goal details:", withdrawalId)
  }


}
