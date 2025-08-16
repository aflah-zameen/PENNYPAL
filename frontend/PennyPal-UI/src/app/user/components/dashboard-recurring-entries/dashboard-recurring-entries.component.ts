import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
export interface RecurringEntry {
  id: string;
  type: 'income' | 'expense';
  description: string;
  amount: number;
  frequency: 'daily' | 'weekly' | 'monthly' | 'yearly';
  nextDate: string;
  daysUntil: number;
  category: string;
  isActive: boolean;
}
@Component({
  selector: 'app-dashboard-recurring-entries',
  imports: [CommonModule],
  templateUrl: './dashboard-recurring-entries.component.html',
  styleUrl: './dashboard-recurring-entries.component.css'
})
export class DashboardRecurringEntriesComponent {
   @Input() entries: RecurringEntry[] = [];
  
  activeFilter = 'all';
  filterOptions = [
    { label: 'All', value: 'all' },
    { label: 'Income', value: 'income' },
    { label: 'Expenses', value: 'expense' },
    { label: 'Due Soon', value: 'due-soon' }
  ];

  ngOnInit() {
    if (this.entries.length === 0) {
      this.entries = this.generateSampleEntries();
    }
  }

  private generateSampleEntries(): RecurringEntry[] {
    return [
      {
        id: '1',
        type: 'income',
        description: 'Monthly Salary',
        amount: 5000,
        frequency: 'monthly',
        nextDate: '2024-02-01',
        daysUntil: 3,
        category: 'Salary',
        isActive: true
      },
      {
        id: '2',
        type: 'expense',
        description: 'Rent Payment',
        amount: 1200,
        frequency: 'monthly',
        nextDate: '2024-01-31',
        daysUntil: 1,
        category: 'Housing',
        isActive: true
      },
      {
        id: '3',
        type: 'expense',
        description: 'Netflix Subscription',
        amount: 15.99,
        frequency: 'monthly',
        nextDate: '2024-02-05',
        daysUntil: 7,
        category: 'Entertainment',
        isActive: true
      },
      {
        id: '4',
        type: 'income',
        description: 'Freelance Project',
        amount: 800,
        frequency: 'weekly',
        nextDate: '2024-02-02',
        daysUntil: 4,
        category: 'Freelance',
        isActive: true
      },
      {
        id: '5',
        type: 'expense',
        description: 'Gym Membership',
        amount: 49.99,
        frequency: 'monthly',
        nextDate: '2024-02-10',
        daysUntil: 12,
        category: 'Health',
        isActive: true
      }
    ];
  }

  setActiveFilter(filter: string) {
    this.activeFilter = filter;
  }

  getFilteredEntries(): RecurringEntry[] {
    switch (this.activeFilter) {
      case 'income':
        return this.entries.filter(entry => entry.type === 'income');
      case 'expense':
        return this.entries.filter(entry => entry.type === 'expense');
      case 'due-soon':
        return this.entries.filter(entry => entry.daysUntil <= 3);
      default:
        return this.entries;
    }
  }

  getEntryClasses(entry: RecurringEntry): string {
    if (entry.daysUntil <= 1) {
      return 'border-red-200 bg-red-50';
    } else if (entry.daysUntil <= 3) {
      return 'border-yellow-200 bg-yellow-50';
    }
    return '';
  }

  getDaysUntilClasses(daysUntil: number): string {
    if (daysUntil <= 1) {
      return 'text-red-600 font-medium';
    } else if (daysUntil <= 3) {
      return 'text-yellow-600 font-medium';
    }
    return 'text-gray-500';
  }

  getDaysUntilText(daysUntil: number): string {
    if (daysUntil === 0) {
      return 'Due today';
    } else if (daysUntil === 1) {
      return 'Due tomorrow';
    } else {
      return `Due in ${daysUntil} days`;
    }
  }

  getMonthlyIncome(): number {
    return this.entries
      .filter(entry => entry.type === 'income' && entry.frequency === 'monthly')
      .reduce((sum, entry) => sum + entry.amount, 0);
  }

  getMonthlyExpenses(): number {
    return this.entries
      .filter(entry => entry.type === 'expense' && entry.frequency === 'monthly')
      .reduce((sum, entry) => sum + entry.amount, 0);
  }
}
