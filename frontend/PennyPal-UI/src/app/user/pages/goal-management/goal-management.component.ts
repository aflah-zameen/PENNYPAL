import { Component } from '@angular/core';
import { AddGoalButtonComponent } from "./add-goal-button/add-goal-button.component";
import { Goal, GoalFilterOptions, GoalFormData, GoalStats } from '../../models/goal.model';
import { StatsCardComponent } from "./stats-card/stats-card.component";
import { SectionHeaderComponent } from "./section-header/section-header.component";
import { GoalCardComponent } from "./goal-card/goal-card.component";
import { CommonModule } from '@angular/common';
import { AddGoalModalComponent } from "../../modals/add-goal-modal/add-goal-modal.component";
import { EditGoalModalComponent } from "../../modals/edit-goal-modal/edit-goal-modal.component";
import { FeatureButtonComponent } from "../../components/feature-button/feature-button.component";

@Component({
  selector: 'app-goal-management',
  imports: [ StatsCardComponent, SectionHeaderComponent, GoalCardComponent, CommonModule, AddGoalModalComponent, EditGoalModalComponent, FeatureButtonComponent],
  templateUrl: './goal-management.component.html',
  styleUrl: './goal-management.component.css'
})
export class GoalManagementComponent {
  viewMode = "grid"
  searchQuery = ""
  filterOptions: GoalFilterOptions = {
    category: "",
    progress: "",
    sort: "title",
  }
stats: GoalStats = {
    totalActiveGoals: 5,
    totalSaved: 101410,
    completedGoals: 12
  };

  goals: Goal[] = [
    {
      id: 1,
      title: 'Apartment',
      currentAmount: 180000,
      targetAmount: 200000,
      startDate: '2024-04-02',
      endDate: '2026-04-02',
      category: 'apartment',
      color: '#3B82F6',
      contributions: [
        { id: 1, amount: 5000, date: "2024-01-15", notes: "Initial savings transfer" },
        { id: 2, amount: 3000, date: "2024-02-01", notes: "Monthly salary bonus" },
        { id: 3, amount: 2000, date: "2024-02-15" },
        { id: 4, amount: 5000, date: "2024-03-01", notes: "Tax refund" },
      ],
    },
    {
      id: 2,
      title: 'Car',
      currentAmount: 80000,
      targetAmount: 100000,
      startDate: '2024-04-02',
      endDate: '2026-04-02',
      category: 'car',
      color: '#8B5CF6'
    }
  ];

  isModalOpen: boolean = false;
  showEditModal: boolean = false;
  selectedGoal: Goal | null = null;
  ngOnInit() {}

  get savingPlanGoals(): Goal[] {
    return this.goals;
  }

  get upcomingDeadlineGoals(): Goal[] {
    return this.goals.sort((a, b) => new Date(a.endDate).getTime() - new Date(b.endDate).getTime());
  }

  formatCurrency(amount: number): string {
    return new Intl.NumberFormat('en-US', {
      style: 'currency',
      currency: 'USD',
      minimumFractionDigits: 0,
      maximumFractionDigits: 0
    }).format(amount);
  }

  // Modal Methods
  openAddModal(): void {
    this.isModalOpen = true;
  }

  closeAddModal(): void {
    this.isModalOpen = false;
  }

  openEditModal(goal: Goal): void {
    this.selectedGoal = goal;
    this.showEditModal = true;
  }

  closeEditModal(): void {
    this.showEditModal = false;
    this.selectedGoal = null;
  }

    // Goal Management
  onGoalAdded(goalData: GoalFormData): void {
    const newGoal: Goal = {
      id: this.goals.length + 1,
      title: goalData.name,
      currentAmount: 0,
      targetAmount: goalData.amount,
      startDate: goalData.startDate,
      endDate: goalData.endDate,
      category: goalData.category,
      color: this.getColorForCategory(goalData.category)
    };

    this.goals.push(newGoal);
    this.updateStats();
    console.log('Goal added:', newGoal);
  }

  onGoalUpdated(event: { id: number; data: GoalFormData }): void {
    const goalIndex = this.goals.findIndex(g => g.id === event.id);
    if (goalIndex !== -1) {
      this.goals[goalIndex] = {
        ...this.goals[goalIndex],
        title: event.data.name,
        targetAmount: event.data.amount,
        startDate: event.data.startDate,
        endDate: event.data.endDate,
        category: event.data.category,
        color: this.getColorForCategory(event.data.category)
      };
      this.updateStats();
      console.log('Goal updated:', this.goals[goalIndex]);
    }
  }

  private getColorForCategory(category: string): string {
    const colors: { [key: string]: string } = {
      apartment: '#3B82F6',
      car: '#8B5CF6',
      vacation: '#10B981',
      education: '#F59E0B',
      other: '#6B7280'
    };
    return colors[category] || colors['other'];
  }

  private updateStats(): void {
    this.stats.totalActiveGoals = this.goals.length;
    this.stats.totalSaved = this.goals.reduce((sum, goal) => sum + goal.currentAmount, 0);
  }


  onDeleteGoal(goalId: number): void {
    this.goals = this.goals.filter(goal => goal.id !== goalId);
    console.log('Delete goal:', goalId);
  }

  onSeeAllSavingPlans(): void {
    console.log('See all saving plans');
  }

  onSeeAllDeadlines(): void {
    console.log('See all deadlines');
  }

  //filters and serach
  onViewModeChange(mode: string): void {
    this.viewMode = mode
  }


  clearAllFilters(): void {
    this.searchQuery = ""
    this.filterOptions = {
      category: "",
      progress: "",
      sort: "title",
    }
  }

  onSearch(query: string): void {
    this.searchQuery = query
  }

  onFilterChange(filters: GoalFilterOptions): void {
    this.filterOptions = { ...filters }
  }

  get hasActiveFilters(): boolean {
    return !!(this.filterOptions.category || this.filterOptions.progress || this.filterOptions.sort !== "title")
  }

  trackByGoal(index: number, goal: Goal): number {
    return goal.id
  }

  get filteredGoals(): Goal[] {
    let filtered = [...this.goals]

    // Apply search filter
    if (this.searchQuery) {
      const query = this.searchQuery.toLowerCase()
      filtered = filtered.filter(
        (goal) => goal.title.toLowerCase().includes(query) || goal.category.toLowerCase().includes(query),
      )
    }

    // Apply category filter
    if (this.filterOptions.category) {
      filtered = filtered.filter((goal) => goal.category === this.filterOptions.category)
    }

    // Apply progress filter
    if (this.filterOptions.progress) {
      const [min, max] = this.filterOptions.progress.split("-").map(Number)
      filtered = filtered.filter((goal) => {
        const progress = (goal.currentAmount / goal.targetAmount) * 100
        return progress >= min && progress <= max
      })
    }

    // Apply sorting
    filtered.sort((a, b) => {
      switch (this.filterOptions.sort) {
        case "progress":
          return b.currentAmount / b.targetAmount - a.currentAmount / a.targetAmount
        case "target":
          return b.targetAmount - a.targetAmount
        case "deadline":
          return new Date(a.endDate).getTime() - new Date(b.endDate).getTime()
        default:
          return a.title.localeCompare(b.title)
      }
    })

    return filtered
  }

   getSearchSummary(): string {
    const parts = []
    if (this.searchQuery) parts.push(`matching "${this.searchQuery}"`)
    if (this.filterOptions.category) parts.push(`in ${this.filterOptions.category}`)
    if (this.filterOptions.progress) parts.push(`with ${this.filterOptions.progress}% progress`)
    return parts.join(", ") || "showing all goals"
  }

}
