import { Routes } from "@angular/router";
import { AdminLayoutComponent } from "./admin-layout/admin-layout/admin-layout.component";
import { DashboardComponent } from "./pages/dashboard/dashboard.component";
import { UserManagementComponent } from "./pages/user-management/user-management.component";
import { CategoryManagementComponent } from "./pages/category-management/category-management.component";

export const adminRoutes : Routes =[{
    path:'',
    component : AdminLayoutComponent,
    children : [{
        path : '',
        component : DashboardComponent
    },{
        path: 'user-management',
        component : UserManagementComponent
    },{
        path: 'category-management',
        component : CategoryManagementComponent
    }]
}]