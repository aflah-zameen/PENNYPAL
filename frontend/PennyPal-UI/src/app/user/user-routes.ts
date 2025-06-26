import { Routes } from "@angular/router";
import { UserLayoutComponent } from "./user-layout/user-layout/user-layout.component";
import { HomeComponent } from "./pages/home/home.component";

export const userRoutes : Routes =[{
    path:'',
    component : UserLayoutComponent,
    children : [{
        path : '',
        component : HomeComponent
    }]
}]