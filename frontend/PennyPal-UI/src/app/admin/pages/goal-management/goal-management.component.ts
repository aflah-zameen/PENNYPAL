import { Component } from '@angular/core';
import { Goal } from '../../models/goal-management.model';
import { CommonModule } from '@angular/common';
import { GoalStatsComponent } from "../../components/goal/goal-stats/goal-stats.component";
import { GoalAlertComponent } from "../../components/goal/goal-alert/goal-alert.component";
import { GoalFiltersComponent } from "../../components/goal/goal-filters/goal-filters.component";
import { GoalTableComponent } from "../../components/goal/goal-table/goal-table.component";
import { AdminCategoryService } from '../../services/category-management.service';
import { AdminCategory } from '../../models/category-management.model';

@Component({
  selector: 'app-goal-management',
  imports: [CommonModule, GoalStatsComponent, GoalAlertComponent, GoalFiltersComponent, GoalTableComponent],
  templateUrl: './goal-management.component.html',
  styleUrl: './goal-management.component.css'
})
export class GoalManagementComponent {
  selectedGoal: Goal | null = null
  categories : AdminCategory[] = [];
  constructor(private adminCategoryService : AdminCategoryService) {}

  ngOnInit() {
    this.categories = this.adminCategoryService.getCategories();
  }

  onGoalSelected(goal: Goal) {
    this.selectedGoal = goal
  }

  onWithdrawalReview(goal: Goal) {
    this.selectedGoal = goal
  }

  closeGoalDetails() {
    this.selectedGoal = null
  }

  formatCurrency(amount: number|undefined): string {
    if (amount === undefined) {
      return "$0.00"
    }
    return new Intl.NumberFormat("en-US", {
      style: "currency",
      currency: "USD",
    }).format(amount)
  }

  formatDateTime(dateString: string): string {
    return new Intl.DateTimeFormat("en-US", {
      year: "numeric",
      month: "short",
      day: "numeric",
      hour: "2-digit",
      minute: "2-digit",
    }).format(new Date(dateString))
  }

  getWithdrawalStatusClass(status: string): string {
    const classes = {
      Pending: "bg-yellow-100 text-yellow-800",
      Approved: "bg-green-100 text-green-800",
      Rejected: "bg-red-100 text-red-800",
    }
    return classes[status as keyof typeof classes] || "bg-gray-100 text-gray-800"
  }
}
