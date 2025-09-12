import { Routes } from "@angular/router";
import { UserLayoutComponent } from "./user-layout/user-layout/user-layout.component";
import { HomeComponent } from "./pages/home/home.component";
import { UserProfileComponent } from "./pages/user-profile/user-profile.component";
import { IncomeManagementComponent } from "./pages/income-management/income-management.component";
import { ExpenseManagementComponent } from "./pages/expense-management/expense-management.component";
import { DashboardComponent } from "./pages/dashboard/dashboard.component";

export const userRoutes : Routes =[{
    path:'',
    component : UserLayoutComponent,
    children : [{
        path : '',
        component : DashboardComponent
    },{
        path : 'user-profile',
        component : UserProfileComponent
    },{
        path : 'income-management',
        component : IncomeManagementComponent
    },{
        path : 'expense-management',
        component : ExpenseManagementComponent
    },{
        path : 'goal-management',
        loadComponent: () => import('./pages/goal-management/goal-management.component').then(m => m.GoalManagementComponent)
    },{
        path : 'spend-activity',
        loadComponent: () => import('./pages/spend-activity/spend-activity.component').then(m => m.SpendActivityComponent)
    },{
        path : 'card-management',
        loadComponent: () => import('./pages/card-management/card-management.component').then(m => m.CardManagementComponent)
    },{
        path : 'contacts',
        loadComponent: () => import('./pages/contact-management/contact-management.component').then(m => m.ContactManagementComponent)
    },{
        path : 'wallet-management',
        loadComponent : () => import('./pages/wallet-management/wallet-management.component').then(m => m.WalletManagementComponent)
    },{
        path : 'lending-management',
        loadComponent : () => import('./pages/lending-management/lending-management.component').then(m => m.LendingManagementComponent)
    },{
        path : 'borrowing-management',
        loadComponent : () => import('./pages/borrowing-management/borrowing-management.component').then(m=>m.BorrowingManagementComponent)
    },{
        path : 'plans',
        loadComponent : ()=> import('./pages/subscription-plans/subscription-plans.component').then(m => m.SubscriptionPlansComponent)
    },{
        path : 'rewards',
        loadComponent : () => import('./pages/reward-management/reward-management.component').then(m => m .RewardManagementComponent)
    },{
        path : 'messages',
        loadComponent : () => import('./pages/message-management/message-management.component').then(m => m.MessageManagementComponent)
    }]
}]