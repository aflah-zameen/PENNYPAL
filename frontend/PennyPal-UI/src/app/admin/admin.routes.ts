import { Routes } from "@angular/router";
import { AdminLayoutComponent } from "./admin-layout/admin-layout/admin-layout.component";

export const adminRoutes: Routes = [
  {
    path: '',
    component: AdminLayoutComponent,
    children: [
      {
        path: '',
        loadComponent: () => import('./pages/dashboard/dashboard.component').then(m => m.DashboardComponent)
      },
      {
        path: 'user-management',
        loadComponent: () => import('./pages/user-management/user-management.component').then(m => m.UserManagementComponent)
      },
      {
        path: 'category-management',
        loadComponent: () => import('./pages/category-management/category-management.component').then(m => m.CategoryManagementComponent)
      },
      {
        path: 'transaction-management',
        loadComponent: () => import('./pages/transaction-management/transaction-management.component').then(m => m.TransactionManagementComponent),
        children: [
          {
            path: '',
            loadComponent: () => import('./components/transaction/transaction-list/transaction-list.component').then(m => m.TransactionListComponent) // Default view
          },
          {
            path: 'flagged',
            loadComponent: () => import('./components/transaction/flagged-transaction/flagged-transaction.component').then(m => m.FlaggedTransactionComponent)
          }
        ]
      },{
        path : "settings/profile",
        loadComponent: () => import('./pages/admin-profile/admin-profile.component').then(m => m.AdminProfileComponent)
      },{
        path : "goal-management",
        loadComponent: () => import('./pages/goal-management/goal-management.component').then(m => m.GoalManagementComponent)
      }
    ]
  }
];