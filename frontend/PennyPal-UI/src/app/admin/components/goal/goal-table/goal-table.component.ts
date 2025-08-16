import { Component, EventEmitter, Output } from '@angular/core';
import { Goal } from '../../../models/goal-management.model';
import { Observable } from 'rxjs';
import { GoalDashboardService } from '../../../services/goal-management.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-goal-table',
  imports: [CommonModule],
  templateUrl: './goal-table.component.html',
  styleUrl: './goal-table.component.css'
})
export class GoalTableComponent {
  @Output() goalSelected = new EventEmitter<Goal>()
  @Output() withdrawalReview = new EventEmitter<Goal>()

  filteredGoals$!: Observable<Goal[]>
  pagination$!: Observable<any>

  activeDropdown: string | null = null
  isRefreshing = false
  isExporting = false
  showConfirmModal = false
  confirmTitle = ""
  confirmMessage = ""
  confirmAction: (() => void) | null = null

  constructor(public goalDashboardService: GoalDashboardService) {}

  ngOnInit() {
    this.filteredGoals$ = this.goalDashboardService.goals$
    this.pagination$ = this.goalDashboardService.pagination$

    // Close dropdown when clicking outside
    document.addEventListener("click", () => {
      this.activeDropdown = null
    })
  }

  trackByGoalId(index: number, goal: Goal): string {
    return goal.id
  }

  toggleDropdown(goalId: string) {
    this.activeDropdown = this.activeDropdown === goalId ? null : goalId
  }

  viewGoalDetails(goal: Goal) {
    this.goalSelected.emit(goal)
  }

  reviewWithdrawals(goal: Goal) {
    this.withdrawalReview.emit(goal)
  }

  // hasPendingWithdrawals(goal: Goal): boolean {
  //   return goal.withdrawalRequests.some((wr) => wr.status === "Pending")
  // }

  // resetWallet(goal: Goal) {
  //   this.showConfirmation(
  //     "Reset Goal Wallet",
  //     `Are you sure you want to reset the wallet for "${goal.goalName}"? This will set the contributed amount to $0 and cannot be undone.`,
  //     () => {
  //       this.goalDashboardService.resetGoalWallet(goal.id).subscribe(() => {
  //         console.log("Wallet reset successfully")
  //       })
  //     },
  //   )
  // }

  pauseGoal(goal: Goal) {
    // Implementation for pausing goal
    console.log("Pause goal:", goal.id)
  }

  activateGoal(goal: Goal) {
    // Implementation for activating goal
    console.log("Activate goal:", goal.id)
  }

  refreshData() {
    this.isRefreshing = true
    this.goalDashboardService.refreshData();
  }

  // exportData() {
  //   this.isExporting = true
  //   this.goalDashboardService.exportGoalsData().subscribe((csvData: string) => {
  //     this.downloadCSV(csvData, "goals-export.csv")
  //     this.isExporting = false
  //   })
  // }

  private downloadCSV(csvData: string, filename: string) {
    const blob = new Blob([csvData], { type: "text/csv" })
    const url = window.URL.createObjectURL(blob)
    const link = document.createElement("a")
    link.href = url
    link.download = filename
    link.click()
    window.URL.revokeObjectURL(url)
  }

  private showConfirmation(title: string, message: string, action: () => void) {
    this.confirmTitle = title
    this.confirmMessage = message
    this.confirmAction = action
    this.showConfirmModal = true
  }

  cancelConfirm() {
    this.showConfirmModal = false
    this.confirmAction = null
  }

  executeConfirm() {
    if (this.confirmAction) {
      this.confirmAction()
    }
    this.showConfirmModal = false
    this.confirmAction = null
  }

  getStatusClass(status: string): string {
    const classes = {
      Active: "bg-green-100 text-green-800",
      Completed: "bg-blue-100 text-blue-800",
      Canceled: "bg-red-100 text-red-800",
      Paused: "bg-yellow-100 text-yellow-800",
    }
    return classes[status as keyof typeof classes] || "bg-gray-100 text-gray-800"
  }

  // getPendingWithdrawalsCount(goal : Goal): number {
  //   return goal?.withdrawalRequests?.filter(wr => wr.status === 'Pending').length || 0;
  // }

  // getWithdrawalText(goal: Goal): string {
  //   const count = this.getPendingWithdrawalsCount(goal);
  //   return count === 1 ? 'withdrawal' : 'withdrawals';
  // }
}
