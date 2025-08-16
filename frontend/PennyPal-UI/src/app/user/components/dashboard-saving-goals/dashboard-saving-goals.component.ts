import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
export interface SavingGoal {
  id: string;
  name: string;
  description?: string;
  currentAmount: number;
  targetAmount: number;
  targetDate: string;
  progress: number;
  category: string;
  priority: 'high' | 'medium' | 'low';
  isCompleted: boolean;
  monthlyContribution?: number;
}
@Component({
  selector: 'app-dashboard-saving-goals',
  imports: [CommonModule],
  templateUrl: './dashboard-saving-goals.component.html',
  styleUrl: './dashboard-saving-goals.component.css'
})
export class DashboardSavingGoalsComponent {
    @Input() goals: SavingGoal[] = [];

  ngOnInit() {
    if (this.goals.length === 0) {
      this.goals = this.generateSampleGoals();
    }
  }

  private generateSampleGoals(): SavingGoal[] {
    return [
      {
        id: '1',
        name: 'Emergency Fund',
        description: 'Build a 6-month emergency fund',
        currentAmount: 8500,
        targetAmount: 10000,
        targetDate: '2024-06-01',
        progress: 85,
        category: 'Emergency',
        priority: 'high',
        isCompleted: false,
        monthlyContribution: 500
      },
      {
        id: '2',
        name: 'Vacation to Japan',
        description: 'Dream trip to Tokyo and Kyoto',
        currentAmount: 2400,
        targetAmount: 4000,
        targetDate: '2024-12-01',
        progress: 60,
        category: 'Travel',
        priority: 'medium',
        isCompleted: false,
        monthlyContribution: 300
      },
      {
        id: '3',
        name: 'New Laptop',
        description: 'MacBook Pro for work',
        currentAmount: 1500,
        targetAmount: 1500,
        targetDate: '2024-01-15',
        progress: 100,
        category: 'Technology',
        priority: 'medium',
        isCompleted: true
      },
      {
        id: '4',
        name: 'Car Down Payment',
        description: 'Down payment for new car',
        currentAmount: 5200,
        targetAmount: 15000,
        targetDate: '2024-09-01',
        progress: 35,
        category: 'Transportation',
        priority: 'low',
        isCompleted: false,
        monthlyContribution: 800
      }
    ];
  }

  getGoalIconClasses(goal: SavingGoal): string {
    if (goal.isCompleted) {
      return 'bg-green-100';
    }
    return 'bg-blue-100';
  }

  getPriorityClasses(priority: string): string {
    switch (priority) {
      case 'high':
        return 'bg-red-100 text-red-700';
      case 'medium':
        return 'bg-yellow-100 text-yellow-700';
      case 'low':
        return 'bg-green-100 text-green-700';
      default:
        return 'bg-gray-100 text-gray-700';
    }
  }

  getProgressIconClasses(progress: number): string {
    if (progress >= 100) {
      return 'text-green-600';
    } else if (progress >= 75) {
      return 'text-blue-600';
    } else if (progress >= 50) {
      return 'text-yellow-600';
    }
    return 'text-gray-600';
  }

  getProgressTextClasses(progress: number): string {
    if (progress >= 100) {
      return 'text-green-600';
    } else if (progress >= 75) {
      return 'text-blue-600';
    } else if (progress >= 50) {
      return 'text-yellow-600';
    }
    return 'text-gray-600';
  }

  getProgressBarClasses(progress: number): string {
    if (progress >= 100) {
      return 'bg-gradient-to-r from-green-400 to-green-500';
    } else if (progress >= 75) {
      return 'bg-gradient-to-r from-blue-400 to-blue-500';
    } else if (progress >= 50) {
      return 'bg-gradient-to-r from-yellow-400 to-yellow-500';
    }
    return 'bg-gradient-to-r from-gray-400 to-gray-500';
  }

  getRemainingAmount(goal: SavingGoal): number {
    return Math.max(0, goal.targetAmount - goal.currentAmount);
  }

  getTotalSaved(): number {
    return this.goals.reduce((sum, goal) => sum + goal.currentAmount, 0);
  }

  getTotalTarget(): number {
    return this.goals.reduce((sum, goal) => sum + goal.targetAmount, 0);
  }

  getAverageProgress(): number {
    if (this.goals.length === 0) return 0;
    const totalProgress = this.goals.reduce((sum, goal) => sum + goal.progress, 0);
    return Math.round(totalProgress / this.goals.length);
  }
}
