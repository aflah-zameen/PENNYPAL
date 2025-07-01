import { Routes } from "@angular/router";
import { UserLayoutComponent } from "./user-layout/user-layout/user-layout.component";
import { HomeComponent } from "./pages/home/home.component";
import { UserProfileComponent } from "./pages/user-profile/user-profile.component";
import { IncomeManagementComponent } from "./pages/income-management/income-management.component";

export const userRoutes : Routes =[{
    path:'',
    component : UserLayoutComponent,
    children : [{
        path : '',
        component : HomeComponent
    },{
        path : 'user-profile',
        component : UserProfileComponent
    },{
        path : 'income-management',
        component : IncomeManagementComponent
    }]
}]