import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { FormsModule } from '@angular/forms';
interface HealthMetric {
  label: string;
  score: number;
  weight: number;
  description: string;
  trend: 'up' | 'down' | 'stable';
}

export interface FinancialHealthData {
  score: number;
  status: string;
  factors: string[];
  metrics: HealthMetric[];
  recommendations: string[];
}
@Component({
  selector: 'app-dashboard-financial-health-score',
  imports: [CommonModule,FormsModule],
  templateUrl: './dashboard-financial-health-score.component.html',
  styleUrl: './dashboard-financial-health-score.component.css'
})
export class DashboardFinancialHealthScoreComponent {
   @Input() healthData !: FinancialHealthData;

  ngOnInit() {
    if (this.healthData.metrics.length === 0) {
      this.healthData = this.generateSampleHealthData();
    }
  }

  private generateSampleHealthData(): FinancialHealthData {
    return {
      score: 78,
      status: 'Good',
      factors: ['Consistent savings', 'Low debt ratio', 'Regular income'],
      metrics: [
        {
          label: 'Spending Habits',
          score: 85,
          weight: 0.3,
          description: 'How well you manage expenses',
          trend: 'up'
        },
        {
          label: 'Savings Rate',
          score: 72,
          weight: 0.25,
          description: 'Percentage of income saved',
          trend: 'stable'
        },
        {
          label: 'Debt Management',
          score: 90,
          weight: 0.2,
          description: 'Debt-to-income ratio',
          trend: 'up'
        },
        {
          label: 'Goal Progress',
          score: 65,
          weight: 0.15,
          description: 'Achievement of financial goals',
          trend: 'down'
        },
        {
          label: 'Emergency Fund',
          score: 80,
          weight: 0.1,
          description: 'Months of expenses covered',
          trend: 'up'
        }
      ],
      recommendations: [
        'Increase your emergency fund to 6 months of expenses',
        'Set up automatic transfers to boost savings rate',
        'Review and optimize recurring subscriptions',
        'Consider investing surplus funds for better returns'
      ]
    };
  }

  getScoreColor(): string {
    if (this.healthData.score >= 80) return '#10B981';
    if (this.healthData.score >= 60) return '#F59E0B';
    return '#EF4444';
  }

  getScoreTextColor(): string {
    if (this.healthData.score >= 80) return 'text-green-600';
    if (this.healthData.score >= 60) return 'text-yellow-600';
    return 'text-red-600';
  }

  getScoreGradient(): string {
    if (this.healthData.score >= 80) return 'bg-gradient-to-r from-green-500 to-green-600';
    if (this.healthData.score >= 60) return 'bg-gradient-to-r from-yellow-500 to-yellow-600';
    return 'bg-gradient-to-r from-red-500 to-red-600';
  }

  getStatusIcon(): string {
    if (this.healthData.score >= 80) return 'M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z';
    if (this.healthData.score >= 60) return 'M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z';
    return 'M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z';
  }

  getScoreDescription(): string {
    if (this.healthData.score >= 80) return 'Excellent financial health! Keep up the great work.';
    if (this.healthData.score >= 60) return 'Good financial health with room for improvement.';
    return 'Your financial health needs attention. Follow our recommendations.';
  }

  getMetricIconBg(score: number): string {
    if (score >= 80) return 'bg-green-100';
    if (score >= 60) return 'bg-yellow-100';
    return 'bg-red-100';
  }

  getMetricIconColor(score: number): string {
    if (score >= 80) return 'text-green-600';
    if (score >= 60) return 'text-yellow-600';
    return 'text-red-600';
  }

  getMetricBarColor(score: number): string {
    if (score >= 80) return 'bg-green-500';
    if (score >= 60) return 'bg-yellow-500';
    return 'bg-red-500';
  }

  getTrendIcon(trend: string): string {
    switch (trend) {
      case 'up':
        return 'M13 7h8m0 0v8m0-8l-8 8-4-4-6 6';
      case 'down':
        return 'M13 17h8m0 0V9m0 8l-8-8-4 4-6-6';
      default:
        return 'M8 12h.01M12 12h.01M16 12h.01M21 12c0 4.418-4.03 8-9 8a9.863 9.863 0 01-4.255-.949L3 20l1.395-3.72C3.512 15.042 3 13.574 3 12c0-4.418 4.03-8 9-8s9 3.582 9 8z';
    }
  }

  getRecommendationBg(): string {
    if (this.healthData.score >= 80) return 'bg-green-50';
    if (this.healthData.score >= 60) return 'bg-yellow-50';
    return 'bg-red-50';
  }

  getRecommendationIconColor(): string {
    if (this.healthData.score >= 80) return 'text-green-600';
    if (this.healthData.score >= 60) return 'text-yellow-600';
    return 'text-red-600';
  }

  getRecommendationTextColor(): string {
    if (this.healthData.score >= 80) return 'text-green-800';
    if (this.healthData.score >= 60) return 'text-yellow-800';
    return 'text-red-800';
  }

  getRecommendationTitle(): string {
    if (this.healthData.score >= 80) return 'Keep it up! Here are some advanced tips:';
    if (this.healthData.score >= 60) return 'Good progress! Try these improvements:';
    return 'Priority actions to improve your score:';
  }
}
