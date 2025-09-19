import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';

export interface Suggestion {
  id: string;
  type: 'warning' | 'success' | 'info' | 'tip';
  title: string;
  message: string;
  action?: string;
  priority: 'high' | 'medium' | 'low';
  category: string;
}


@Component({
  selector: 'app-dashboard-smart-suggestions',
  imports: [CommonModule],
  templateUrl: './dashboard-smart-suggestions.component.html',
  styleUrl: './dashboard-smart-suggestions.component.css'
})
export class DashboardSmartSuggestionsComponent {
  @Input() suggestions: Suggestion[] = [];

  ngOnInit() {
    if (this.suggestions.length === 0) {
      this.suggestions = this.generateSampleSuggestions();
    }
  }

  private generateSampleSuggestions(): Suggestion[] {
    return [
      {
        id: '1',
        type: 'warning',
        title: 'Overspending Alert',
        message: 'You\'ve spent 23% more on dining out this month compared to your average. Consider cooking at home more often to save money.',
        action: 'View Dining Expenses',
        priority: 'high',
        category: 'Spending'
      },
      {
        id: '2',
        type: 'success',
        title: 'Savings Goal Progress',
        message: 'Excellent work! You\'re 85% towards your emergency fund goal. You\'re on track to complete it by next month.',
        action: 'Add More to Goal',
        priority: 'medium',
        category: 'Goals'
      },
      {
        id: '3',
        type: 'info',
        title: 'Investment Opportunity',
        message: 'You have $500 sitting idle in your checking account. Consider moving it to a high-yield savings account or investment.',
        action: 'Explore Options',
        priority: 'medium',
        category: 'Investment'
      },
      {
        id: '4',
        type: 'tip',
        title: 'Budget Optimization',
        message: 'Based on your spending patterns, you could save $150/month by switching to a different phone plan and streaming service.',
        action: 'See Recommendations',
        priority: 'low',
        category: 'Optimization'
      }
    ];
  }

  getSuggestionClasses(type: string): string {
    const baseClasses = 'border-l-4 ';
    switch (type) {
      case 'warning':
        return baseClasses + 'bg-yellow-50 border-yellow-400 border-yellow-200';
      case 'success':
        return baseClasses + 'bg-green-50 border-green-400 border-green-200';
      case 'info':
        return baseClasses + 'bg-blue-50 border-blue-400 border-blue-200';
      case 'tip':
        return baseClasses + 'bg-purple-50 border-purple-400 border-purple-200';
      default:
        return baseClasses + 'bg-gray-50 border-gray-400 border-gray-200';
    }
  }

  getIconClasses(type: string): string {
    switch (type) {
      case 'warning':
        return 'bg-yellow-100 text-yellow-600';
      case 'success':
        return 'bg-green-100 text-green-600';
      case 'info':
        return 'bg-blue-100 text-blue-600';
      case 'tip':
        return 'bg-purple-100 text-purple-600';
      default:
        return 'bg-gray-100 text-gray-600';
    }
  }

  getIconPath(type: string): string {
    switch (type) {
      case 'warning':
        return 'M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z';
      case 'success':
        return 'M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z';
      case 'info':
        return 'M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z';
      case 'tip':
        return 'M9.663 17h4.673M12 3v1m6.364 1.636l-.707.707M21 12h-1M4 12H3m3.343-5.657l-.707-.707m2.828 9.9a5 5 0 117.072 0l-.548.547A3.374 3.374 0 0014 18.469V19a2 2 0 11-4 0v-.531c0-.895-.356-1.754-.988-2.386l-.548-.547z';
      default:
        return 'M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z';
    }
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

  getActionButtonClasses(type: string): string {
    switch (type) {
      case 'warning':
        return 'bg-yellow-100 text-yellow-700 hover:bg-yellow-200';
      case 'success':
        return 'bg-green-100 text-green-700 hover:bg-green-200';
      case 'info':
        return 'bg-blue-100 text-blue-700 hover:bg-blue-200';
      case 'tip':
        return 'bg-purple-100 text-purple-700 hover:bg-purple-200';
      default:
        return 'bg-gray-100 text-gray-700 hover:bg-gray-200';
    }
  }

  onActionClick(suggestion: Suggestion) {
  }
}
