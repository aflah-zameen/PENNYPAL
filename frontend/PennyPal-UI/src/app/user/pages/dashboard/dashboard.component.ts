import { Component } from '@angular/core';
import { DashboardFinancialSummaryComponent } from "../../components/dashboard-financial-summary/dashboard-financial-summary.component";
import { DashboardIncomeExpenseChartComponent } from "../../components/dashboard-income-expense-chart/dashboard-income-expense-chart.component";
import { DashboardExpenseBreakdownComponent } from "../../components/dashboard-expense-breakdown/dashboard-expense-breakdown.component";
import { DashboardSmartSuggestionsComponent, Suggestion } from "../../components/dashboard-smart-suggestions/dashboard-smart-suggestions.component";
import { DashboardRecurringEntriesComponent, RecurringEntry } from "../../components/dashboard-recurring-entries/dashboard-recurring-entries.component";
import { DashboardSavingGoalsComponent, SavingGoal } from "../../components/dashboard-saving-goals/dashboard-saving-goals.component";
import { DashboardRecentTransactionsComponent, Transaction } from "../../components/dashboard-recent-transactions/dashboard-recent-transactions.component";
import { DashboardMessagePreviewComponent, Message } from "../../components/dashboard-message-preview/dashboard-message-preview.component";
import { DashboardFinancialHealthScoreComponent, FinancialHealthData } from "../../components/dashboard-financial-health-score/dashboard-financial-health-score.component";
import { DashboardQuickActionComponent } from "../../components/dashboard-quick-action/dashboard-quick-action.component";
import { Observable, of } from 'rxjs';
import { AuthService } from '../../../public/auth/services/auth.service';
import { CommonModule } from '@angular/common';
import { TransactionService } from '../../../admin/services/transaction-management.service';
import { DashboardSumary, ExpenseData } from '../../models/dashboard.model';
import { UserTransactionService } from '../../services/user-transactions.service';
import { User } from '../../../models/User';

@Component({
  selector: 'app-dashboard',
  imports: [DashboardFinancialSummaryComponent,
     DashboardIncomeExpenseChartComponent,
     DashboardExpenseBreakdownComponent,
     DashboardSmartSuggestionsComponent,
     DashboardRecurringEntriesComponent,
     DashboardSavingGoalsComponent,
     DashboardRecentTransactionsComponent,
     DashboardMessagePreviewComponent,
     DashboardFinancialHealthScoreComponent,
     CommonModule,
     DashboardQuickActionComponent],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {
  user$! : Observable<User | null>;
  dashboardSummary$! : Observable<DashboardSumary>;
  chartData$! :  Observable<{ date: string; income: number; expense: number }[]>; 
  expenseData$! : Observable<ExpenseData[]>;

  constructor(private authService: AuthService,
    private transactionService: UserTransactionService,
  ) {
  }

  ngOnInit() {
    this.user$ = this.authService.user$;
    this.dashboardSummary$ = this.transactionService.getDashboardSummary();
    this.chartData$ = this.transactionService.getIncomeExpenseChartData();
    this.expenseData$ = this.transactionService.getExpenseBreakdownData();
  }
  notificationCount = 3;
  
  financialSummary = {
    walletBalance: 5420.50,
    totalExpenses: 2340.75,
    totalIncome: 6500.00,
    netSavings: 4159.25
  };

  incomeExpenseData = [
    { date: '2024-01-01', income: 200, expense: 150 },
    { date: '2024-01-02', income: 0, expense: 80 },
    { date: '2024-01-03', income: 500, expense: 200 },
    // Add more data points
  ];

  expenseBreakdownData = [
    { category: 'Food', amount: 800, percentage: 34 },
    { category: 'Rent', amount: 1200, percentage: 51 },
    { category: 'Transport', amount: 200, percentage: 9 },
    { category: 'Others', amount: 140, percentage: 6 }
  ];

aiSuggestions : Suggestion[] = [
  {
    id: 'suggestion-001',
    type: 'warning',
    title: 'Overspending Alert',
    message: 'You\'ve spent 15% more on food this month compared to last month.',
    action: 'View Details',
    priority: 'high',
    category: 'expenses'
  },
  {
    id: 'suggestion-002',
    type: 'success',
    title: 'Goal Progress',
    message: 'Great job! You\'re 80% towards your vacation savings goal.',
    action: 'Add More',
    priority: 'medium',
    category: 'goals'
  },
  {
    id: 'suggestion-003',
    type: 'info',
    title: 'Upcoming Recurring Expense',
    message: 'Your rent of ₹8,000 is scheduled for tomorrow.',
    action: 'Review',
    priority: 'medium',
    category: 'recurring'
  },
  {
    id: 'suggestion-004',
    type: 'tip',
    title: 'Optimize Budget',
    message: 'Consider reducing your dining out budget to increase your savings.',
    action: 'Adjust Budget',
    priority: 'low',
    category: 'budget'
  }
];

  financialHealthScore: FinancialHealthData = {
  score: 78,
  status: 'Good',
  factors: ['Consistent savings', 'Low debt ratio', 'Regular income'],
  metrics: [
    {
      label: 'Savings Behavior',
      score: 85,
      weight: 0.3,
      description: 'Measures how regularly and how much you save from your income.',
      trend: 'up'
    },
    {
      label: 'Debt Management',
      score: 70,
      weight: 0.25,
      description: 'Indicates your ability to manage and repay debts on time.',
      trend: 'stable'
    },
    {
      label: 'Income Stability',
      score: 90,
      weight: 0.2,
      description: 'Shows consistency and reliability of income over time.',
      trend: 'up'
    },
    {
      label: 'Expense Control',
      score: 65,
      weight: 0.15,
      description: 'Tracks how well you keep your spending within budget.',
      trend: 'down'
    },
    {
      label: 'Goal Achievement',
      score: 75,
      weight: 0.1,
      description: 'Reflects progress toward financial goals like saving or debt repayment.',
      trend: 'up'
    }
  ],
  recommendations: [
    'Consider reducing unnecessary expenses to improve expense control.',
    'Maintain regular savings contributions to keep your momentum.',
    'Keep monitoring goal progress and adjust contributions as needed.'
  ]
};

savingGoals: SavingGoal[] = [
  {
    id: 'goal-001',
    name: 'Emergency Fund',
    description: 'To cover unexpected medical or home expenses',
    currentAmount: 3500,
    targetAmount: 10000,
    targetDate: '2025-12-31',
    progress: 35,
    category: 'safety',
    priority: 'high',
    isCompleted: false,
    monthlyContribution: 1500
  },
  {
    id: 'goal-002',
    name: 'Vacation',
    description: 'Trip to Goa in December',
    currentAmount: 1200,
    targetAmount: 1500,
    targetDate: '2025-12-15',
    progress: 80,
    category: 'travel',
    priority: 'medium',
    isCompleted: false,
    monthlyContribution: 300
  },
  {
    id: 'goal-003',
    name: 'New Laptop',
    description: 'MacBook Air M3 for development',
    currentAmount: 80000,
    targetAmount: 120000,
    targetDate: '2026-03-01',
    progress: 66,
    category: 'electronics',
    priority: 'medium',
    isCompleted: false,
    monthlyContribution: 5000
  },
  {
    id: 'goal-004',
    name: 'Debt Repayment',
    description: 'Clear student loan',
    currentAmount: 50000,
    targetAmount: 50000,
    targetDate: '2025-10-01',
    progress: 100,
    category: 'loans',
    priority: 'high',
    isCompleted: true
  }
];

  recurringEntries: RecurringEntry[] = [
  {
    id: 'rec-001',
    type: 'income',
    description: 'Salary',
    amount: 5000,
    frequency: 'monthly',
    nextDate: '2024-02-01',
    daysUntil: 3,
    category: 'salary',
    isActive: true
  },
  {
    id: 'rec-002',
    type: 'expense',
    description: 'Rent',
    amount: 1200,
    frequency: 'monthly',
    nextDate: '2024-01-31',
    daysUntil: 1,
    category: 'housing',
    isActive: true
  },
  {
    id: 'rec-003',
    type: 'expense',
    description: 'Netflix Subscription',
    amount: 499,
    frequency: 'monthly',
    nextDate: '2024-02-05',
    daysUntil: 7,
    category: 'entertainment',
    isActive: true
  },
  {
    id: 'rec-004',
    type: 'income',
    description: 'Freelance Payout',
    amount: 2500,
    frequency: 'weekly',
    nextDate: '2024-02-02',
    daysUntil: 4,
    category: 'freelance',
    isActive: false
  }
];

  recentTransactions: Transaction[] = [
  {
    id: 'txn-001',
    type: 'expense',
    category: 'Food',
    description: 'Grocery Shopping',
    amount: 85.50,
    timestamp: '2025-07-29T10:15:00Z',
    merchant: 'Big Bazaar',
    paymentMethod: 'credit_card',
    status: 'completed'
  },
  {
    id: 'txn-002',
    type: 'income',
    category: 'Freelance',
    description: 'Web Development Project',
    amount: 500.00,
    timestamp: '2025-07-28T14:30:00Z',
    merchant: 'Upwork',
    paymentMethod: 'bank_transfer',
    status: 'completed'
  },
  {
    id: 'txn-003',
    type: 'expense',
    category: 'Entertainment',
    description: 'Netflix Subscription',
    amount: 499.00,
    timestamp: '2025-07-27T08:00:00Z',
    merchant: 'Netflix',
    paymentMethod: 'upi',
    status: 'completed'
  },
  {
    id: 'txn-004',
    type: 'income',
    category: 'Salary',
    description: 'Monthly Salary Credit',
    amount: 30000.00,
    timestamp: '2025-07-25T00:00:00Z',
    merchant: 'Company Pvt Ltd',
    paymentMethod: 'bank_transfer',
    status: 'pending'
  },
  {
    id: 'txn-005',
    type: 'expense',
    category: 'Bills',
    description: 'Electricity Bill',
    amount: 1200.00,
    timestamp: '2025-07-24T18:45:00Z',
    merchant: 'KSEB',
    paymentMethod: 'debit_card',
    status: 'failed'
  }
];

latestMessage: Message = {
  id: 'msg-001',
  senderName: 'Alice Johnson',
  senderAvatar: 'https://example.com/avatars/alice.jpg',
  message: 'Thanks for lending me $200. I\'ll pay you back next week!',
  timestamp: '2025-07-29T13:30:00Z',
  isRead: false,
  messageType: 'lending',
  amount: 200
};

  handleQuickAction(action: string) {
    console.log('Quick action clicked:', action);
    // Handle different quick actions
    switch(action) {
      case 'add-income':
        // Navigate to add income form
        break;
      case 'add-expense':
        // Navigate to add expense form
        break;
      case 'create-goal':
        // Navigate to create goal form
        break;
      case 'lend-money':
        // Navigate to lend money form
        break;
      case 'add-recurring':
        // Navigate to add recurring entry form
        break;
    }
  }
}
