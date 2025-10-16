import { Component, ViewChild } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { NavBarComponent } from '../../../../shared/components/headers/public-header/nav-bar/nav-bar.component';
import { PublicFooterComponent } from '../../../../shared/components/footers/public-footer/public-footer.component';
import { HeroSectionComponent } from "../../components/hero-section/hero-section.component";
import { FeatureSectionComponent } from "../../components/feature-section/feature-section.component";
import { ContactSectionComponent } from "../../components/contact-section/contact-section.component";
import { AuthService } from '../../../auth/services/auth.service';
import { ToastrService } from 'ngx-toastr';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-public-layout',
  imports: [NavBarComponent, RouterOutlet, PublicFooterComponent, HeroSectionComponent, FeatureSectionComponent, ContactSectionComponent],
  templateUrl: './public-layout.component.html',
  styleUrl: './public-layout.component.css'
})

export class PublicLayoutComponent {
   @ViewChild("landingShell", { static: true }) landingShell: any
  constructor(private router: Router,private authService : AuthService,
    private toastr : ToastrService,private spinner : NgxSpinnerService) {}

  ngAfterViewInit(): void {
    // basic fade-in of the whole page on mount
    gsap.from(this.landingShell?.nativeElement, {
      opacity: 0,
      duration: 0.6,
      ease: "power2.out",
    })
  }

  handleDemoLogin(role: "user" | "admin") {
    // Navigate to login with query params to prefill
    const presets =
      role === "user"
        ? { email: "aflahzameen3@gmail.com", password: "Aflah@123" }
        : { email: "superadmin@gmail.com", password: "Super@123" };

        this.spinner.show();
      this.authService.login(presets).subscribe(
        {
          next: (response) => {
        this.toastr.success('Login successful', 'Success');
        if(role === 'admin')
          this.router.navigate(['/admin/user-management']);
        else
          this.router.navigate(['/user']);
      },
      error : ()=>{
        this.spinner.hide();
      }
      ,
      complete : ()=>{
        this.spinner.hide();
      }
        }
      )
  }

}
