import { Component } from '@angular/core';
import { NavBarComponent } from '../../../shared/components/headers/public-header/nav-bar/nav-bar.component';
import { NavItemComponent } from './nav-item/nav-item.component';
import { NavSectionComponent } from './nav-section/nav-section.component';
import { LogoComponent } from './logo/logo.component';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
interface NavItem {
  label: string
  icon: string
  route: string
  paddingLeft?: boolean
}
@Component({
  selector: 'app-side-navbar',
  imports: [NavSectionComponent,NavItemComponent,LogoComponent,CommonModule],
  templateUrl: './side-navbar.component.html',
  styleUrl: './side-navbar.component.css'
})
export class SideNavbarComponent {


  navItems: NavItem[] = [
    { label: "Home", icon: "home", route: "/user" },
    { label: "Contacts", icon: "users", route: "/user/contacts" },
    { label: "Cards", icon: "credit-card", route: "/user/card-management" },
    { label: "Wallet", icon: "wallet", route: "/user/wallet-management" },
    { label: "Expense", icon: "trending-down", route: "/user/expense-management" },
    { label: "Income", icon: "trending-up", route: "/user/income-management" },
    { label: "Goals", icon: "target", route: "/user/goal-management" },
    { label: "Spend Activity", icon: "activity", route: "/user/spend-activity" },
    { label: "Lent money", icon: "hand-coins", route: "/user/lending-management" },
    { label: "Money to pay", icon: "dollar-sign", route: "/user/borrowing-management" },
    // { label: "Requested Money", icon: "message-square", route: "/user/requested-money" },
    { label: "Rewards", icon: "gift", route: "/user/rewards" },
  ]

  informationItems: NavItem[] = [
    { label: "Messages", icon: "message-circle", route: "/messages" },
    { label: "Offers", icon: "tag", route: "/offers" },
  ]

  otherItems: NavItem[] = [
    { label: "Help & Support", icon: "help-circle", route: "/help" },
    { label: "Feedback", icon: "message-circle", route: "/feedback" },
  ]

  constructor(private router: Router) {}

  isRouteActive(route: string): boolean {
    return this.router.url === route
  }
}
