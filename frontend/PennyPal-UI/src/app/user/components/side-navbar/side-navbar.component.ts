import { Component } from '@angular/core';
import { NavBarComponent } from '../../../shared/components/headers/public-header/nav-bar/nav-bar.component';
import { NavItemComponent } from './nav-item/nav-item.component';
import { NavSectionComponent } from './nav-section/nav-section.component';
import { LogoComponent } from './logo/logo.component';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-side-navbar',
  imports: [NavSectionComponent,NavItemComponent,LogoComponent,CommonModule],
  templateUrl: './side-navbar.component.html',
  styleUrl: './side-navbar.component.css'
})
export class SideNavbarComponent {

  constructor(private router: Router) {}

  navItems = [
    { label: 'Home', icon: '...', route: '/user' },
    { label: 'Contacts', route: '/user/contacts', paddingLeft: true },
    { label: 'Cards', icon: '...', route: '/user/cards' },
    { label: 'Expense Category', icon: '...', route: '/user/expense-management' },
    { label: 'Income', icon: '...', route: '/user/income-management' },
    { label: 'Goals', icon: '...', route: '/user/goal-management' },
    { label: 'Spend Activity', route: '/user/spend-activity', paddingLeft: true },
    { label: 'Transactions', icon: '...', route: '/user/transactions' },
    { label: 'Lent money', icon: '...', route: '/user/lent-money' },
    { label: 'Money to pay', icon: '...', route: '/user/money-to-pay' },
    { label: 'Requested Money', icon: '...', route: '/user/requested-money' },
    { label: 'Rewards', icon: '...', route: '/user/rewards' }
  ];

  isRouteActive(route: string): boolean {
    return this.router.url === route;
  }
}
