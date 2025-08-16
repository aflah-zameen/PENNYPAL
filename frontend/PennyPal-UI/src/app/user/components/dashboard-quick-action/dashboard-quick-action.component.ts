import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-dashboard-quick-action',
  imports: [CommonModule],
  templateUrl: './dashboard-quick-action.component.html',
  styleUrl: './dashboard-quick-action.component.css'
})
export class DashboardQuickActionComponent {
  @Output() actionClicked = new EventEmitter<string>();

  quickActions = [
    {
      id: 'add-income',
      title: 'Add Income',
      description: 'Record new income',
      iconBg: 'bg-gradient-to-r from-green-500 to-green-600',
      iconPath: 'M12 6v6m0 0v6m0-6h6m-6 0H6'
    },
    {
      id: 'add-expense',
      title: 'Add Expense',
      description: 'Log new expense',
      iconBg: 'bg-gradient-to-r from-red-500 to-red-600',
      iconPath: 'M20 12H4'
    },
    {
      id: 'create-goal',
      title: 'Create Goal',
      description: 'Set savings target',
      iconBg: 'bg-gradient-to-r from-purple-500 to-purple-600',
      iconPath: 'M9 12l2 2 4-4M7.835 4.697a3.42 3.42 0 001.946-.806 3.42 3.42 0 014.438 0 3.42 3.42 0 001.946.806 3.42 3.42 0 013.138 3.138 3.42 3.42 0 00.806 1.946 3.42 3.42 0 010 4.438 3.42 3.42 0 00-.806 1.946 3.42 3.42 0 01-3.138 3.138 3.42 3.42 0 00-1.946.806 3.42 3.42 0 01-4.438 0 3.42 3.42 0 00-1.946-.806 3.42 3.42 0 01-3.138-3.138 3.42 3.42 0 00-.806-1.946 3.42 3.42 0 010-4.438 3.42 3.42 0 00.806-1.946 3.42 3.42 0 013.138-3.138z'
    },
    {
      id: 'lend-money',
      title: 'Lend Money',
      description: 'Transfer to friend',
      iconBg: 'bg-gradient-to-r from-blue-500 to-blue-600',
      iconPath: 'M8 7h12m0 0l-4-4m4 4l-4 4m0 6H4m0 0l4 4m-4-4l4-4'
    },
    {
      id: 'add-recurring',
      title: 'Add Recurring',
      description: 'Set up auto entry',
      iconBg: 'bg-gradient-to-r from-yellow-500 to-yellow-600',
      iconPath: 'M4 4v5h.582m15.356 2A8.001 8.001 0 004.582 9m0 0H9m11 11v-5h-.581m0 0a8.003 8.003 0 01-15.357-2m15.357 2H15'
    }
  ];

  onActionClick(actionId: string) {
    this.actionClicked.emit(actionId);
  }
}
