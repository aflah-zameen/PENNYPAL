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
import { AuthGuard } from "../guards/auth.guard";

export const publicRoutes : Routes= [{
    path : '',
    component : PublicLayoutComponent,
    children : [
        {
            path : '', 
            component : LandingPageComponent,
        },{
            path : 'features',
            component : FeatureSectionComponent
        },{
            path : 'contact',
            component : ContactSectionComponent
        },{
            path : "login",
            component : LoginSectionComponent,
        },{
            path : "signup",
            component : SignupSectionComponent
        },{
            path : "forget-password",
            component : ForgetPasswordComponent
        },{
            path : "otp-section",
            component : OtpSectionComponent
        },{
            path : "set-new-password",
            component : SetNewPasswordComponent
        },{
            path:"admin-login",
            component : AdminLoginComponent
        }
    ]
}]