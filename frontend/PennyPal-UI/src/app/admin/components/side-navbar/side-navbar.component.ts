import { Component, Input, SimpleChanges } from '@angular/core';
import { SideNavbarHeaderComponent } from "./side-navbar-header/side-navbar-header.component";
import { SideNavbarItemComponent } from "./side-navbar-item/side-navbar-item.component";
import { CommonModule } from '@angular/common';
import { NavigationEnd, Router } from '@angular/router';
import { MenuItem } from '../../models/side-navbar.model';
import { UserProfileComponent } from "../../../admin/components/user-profile/user-profile.component";
import { filter, Subscription } from 'rxjs';
import { AdminUser } from '../../models/admin.model';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmDialogComponent } from '../../../shared/components/confirm-dialog/confirm-dialog.component';
import { NgxSpinnerService } from 'ngx-spinner';
import { AuthService } from '../../../public/auth/services/auth.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-side-navbar',
  imports: [SideNavbarHeaderComponent, SideNavbarItemComponent, CommonModule, UserProfileComponent],
  templateUrl: './side-navbar.component.html',
  styleUrl: './side-navbar.component.css'
})
export class SideNavbarComponent {
   @Input() activeRoute = ""

  navigationTitle = "Admin Dashboard"
  currentUser: AdminUser | null = null
  isLogoutModalOpen = false

  private subscription = new Subscription()

  menuItems: MenuItem[] = [
    {
      text: "Dashboard",
      icon: "dashboard",
      route: "/admin/dashboard",
      isActive: false,
    },
    {
      text: "Manage Accounts",
      icon: "users",
      route: "/admin/user-management",
      badge: "12",
    },
    {
      text: "Transaction Management",
      icon: "credit-card",
      route: "/admin/transactions",
    },
    {
      text: "Lending & Borrowing",
      icon: "trending-up",
      route: "/admin/lending",
    },
    {
      text: "Referral & Rewards",
      icon: "gift",
      route: "/admin/referrals",
      badge: "New",
    },
    {
      text: "Category Management",
      icon: "folder",
      route: "/admin/category-management",
    },
    {
      text: "Offers & Promotions",
      icon: "megaphone",
      route: "/admin/offers",
    },
    {
      text: "Reports & Analytics",
      icon: "chart-bar",
      route: "/admin/reports",
      subItems: [
        { text: "Financial Reports", icon: "document-report", route: "/admin/reports/financial" },
        { text: "User Analytics", icon: "chart-pie", route: "/admin/reports/users" },
      ],
    },
    {
      text: "Settings",
      icon: "cog",
      route: "/admin/settings",
      subItems: [
        { text: "System Settings", icon: "adjustments", route: "/admin/settings/system" },
        { text: "Security", icon: "shield-check", route: "/admin/settings/security" },
        { text: "Profile Settings", icon: "key", route: "/admin/settings/profile" },
        { text: "Notifications", icon: "bell", route: "/admin/settings/notifications" },
      ],
    },
    {
      text: "Income Dashboard",
      icon: "trending-up",
      route: "/admin/income",
    },
    {
      text: "Goals Dashboard",
      icon: "target",
      route: "/admin/goals",
    },
  ]

  constructor(private router: Router,private dialog : MatDialog,private spinner : NgxSpinnerService,
    private authService: AuthService,private toster: ToastrService
  ) {
    // Mock current user - in real app, get from auth service
    this.currentUser = {
      id: "1",
      name: "John Anderson",
      email: "john.anderson@company.com",
      role: "Super Administrator",
      avatar: "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=150&h=150&fit=crop&crop=face",
      lastLogin: new Date().toISOString(),
      status: "online",
      permissions: ["read", "write", "delete", "admin"],
    }
  }

  ngOnInit() {
    // Subscribe to router events to update active menu item
    this.subscription.add(
      this.router.events.pipe(filter((event) => event instanceof NavigationEnd)).subscribe((event: NavigationEnd) => {
        this.updateActiveState(event.url)
      }),
    )

    // Set initial active state
    this.updateActiveState(this.activeRoute || this.router.url)
  }

  ngOnChanges(changes: SimpleChanges) {
    // Update active state when activeRoute input changes
    if (changes["activeRoute"] && changes["activeRoute"].currentValue) {
      this.updateActiveState(changes["activeRoute"].currentValue)
    }
  }

  ngOnDestroy() {
    this.subscription.unsubscribe()
  }

  trackByRoute(index: number, item: MenuItem): string {
    return item.route
  }

  onItemClick(item: MenuItem): void {
    // Navigate to route
    this.router.navigate([item.route])
  }

  onProfileClick(): void {
    this.router.navigate(["/admin/settings/profile"])
  }

  onSettingsClick(): void {
    this.router.navigate(["/admin/settings/system"])
  }

  // showLogoutConfirmation(): void {
  //   this.isLogoutModalOpen = true
  // }

  // confirmLogout(): void {
  //   // Clear user session/tokens
  //   localStorage.removeItem("admin_token")
  //   sessionStorage.clear()

  //   this.isLogoutModalOpen = false
  //   this.router.navigate(["/admin/login"])

  //   console.log("Admin logged out successfully")
  // }

  // cancelLogout(): void {
  //   this.isLogoutModalOpen = false
  // }

  userLogout(): void {
    this.dialog.open(ConfirmDialogComponent, {
       width : '400px',
      data : {
        title: 'Confirm Logout',
        message: `Are you sure you want to Logout ?`,
        confirmText: `Logout`,
        cancelText: 'Cancel'
      }}).afterClosed().subscribe((confirmed) => {
      if (confirmed) {
        this.spinner.show();
        this.authService.logout().subscribe({
          next: () => {
            this.toster.success("Logged out successfully", "Success", {
              timeOut: 3000,
              positionClass: 'toast-top-right',
            });
            this.spinner.hide();
            this.router.navigate(["/admin-login"]);
          },
          error: (err) => {
            this.spinner.hide();
            console.error("Logout failed", err);
          }
        });
      }
    })
  }

  private updateActiveState(currentUrl: string): void {
    this.menuItems.forEach((item) => {
      // Check main item
      item.isActive = this.isRouteActive(currentUrl, item.route)

      // Check sub items
      if (item.subItems) {
        item.subItems.forEach((subItem) => {
          subItem.isActive = this.isRouteActive(currentUrl, subItem.route)
        })
      }
    })
  }

  private isRouteActive(currentUrl: string, itemRoute: string): boolean {
    // Remove query parameters and fragments for comparison
    const cleanCurrentUrl = currentUrl.split("?")[0].split("#")[0]
    const cleanItemRoute = itemRoute.split("?")[0].split("#")[0]

    // Exact match or starts with for parent routes
    return (
      cleanCurrentUrl === cleanItemRoute ||
      (cleanCurrentUrl.startsWith(cleanItemRoute + "/") && cleanItemRoute !== "/admin")
    )
  }
}
