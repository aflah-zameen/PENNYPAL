import { Route, Routes } from "@angular/router";
import { PublicLayoutComponent } from "./landing-page/layouts/public-layout/public-layout.component";
import { LandingPageComponent } from "./landing-page/pages/landing-page/landing-page.component";
import { FeatureSectionComponent } from "./landing-page/components/feature-section/feature-section.component";
import { ContactSectionComponent } from "./landing-page/components/contact-section/contact-section.component";
import { LoginSectionComponent } from "./auth/components/login-section/login-section.component";
import { SignupSectionComponent } from "./auth/components/signup-section/signup-section.component";
import { ForgetPasswordComponent } from "./auth/components/forget-password/forget-password.component";
import { OtpSectionComponent } from "./auth/components/otp-section/otp-section.component";
import { SetNewPasswordComponent } from "./auth/components/set-new-password/set-new-password.component";
import { AdminLoginComponent } from "./auth/components/admin-login/admin-login.component";

export const publicRoutes : Routes= [{
    path : '',
    component : PublicLayoutComponent,
    children : [
        {
            path : '', 
            component : LandingPageComponent,
        }]
},

{
    path : 'auth',
    loadComponent : () => import('./auth/auth-layout/auth-layout.component').then(m => m.AuthLayoutComponent),
    children : [
        {
            path : 'login',
            loadComponent : () => import('./auth/components/login-section/login-section.component').then(m => m.LoginSectionComponent)
        },
        {
            path : 'signup',
            loadComponent : () => import('./auth/components/signup-section/signup-section.component').then(m => m.SignupSectionComponent)
        },
        {
            path : 'forget-password',
            loadComponent : () => import('./auth/components/forget-password/forget-password.component').then(m => m.ForgetPasswordComponent)
        },
        {
            path : 'otp-section',
            loadComponent : () => import('./auth/components/otp-section/otp-section.component').then(m => m.OtpSectionComponent)
        },
        {
            path : 'set-new-password',
            loadComponent : () => import('./auth/components/set-new-password/set-new-password.component').then(m => m.SetNewPasswordComponent)
        },
        {
            path : 'admin-login',
            loadComponent : () => import('./auth/components/admin-login/admin-login.component').then(m => m.AdminLoginComponent)
        }
    ]
}]