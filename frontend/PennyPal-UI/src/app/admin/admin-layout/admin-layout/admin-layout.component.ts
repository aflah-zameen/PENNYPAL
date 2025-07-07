import { Component } from '@angular/core';
import { SideNavbarComponent } from "../../../admin/components/side-navbar/side-navbar.component";
import { MainHeaderComponent } from "../../components/main-header/main-header.component";
import { ActivatedRoute, NavigationEnd, Router, RouterLinkActive, RouterOutlet } from '@angular/router';
import { filter } from 'rxjs';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-admin-layout',
  imports: [SideNavbarComponent,RouterOutlet,CommonModule],
  templateUrl: './admin-layout.component.html',
  styleUrl: './admin-layout.component.css'
})
export class AdminLayoutComponent {
  currentRoute = ""

  constructor(private router: Router) {}

  ngOnInit() {
    // Listen to route changes to update active navigation
    this.router.events.pipe(filter((event) => event instanceof NavigationEnd)).subscribe((event: NavigationEnd) => {
      this.currentRoute = event.urlAfterRedirects
    })

    // Set initial route
    this.currentRoute = this.router.url
  }
}
