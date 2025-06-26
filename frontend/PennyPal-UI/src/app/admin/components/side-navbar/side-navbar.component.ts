import { Component } from '@angular/core';
import { SideNavbarHeaderComponent } from "./side-navbar-header/side-navbar-header.component";
import { SideNavbarItemComponent } from "./side-navbar-item/side-navbar-item.component";
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-side-navbar',
  imports: [SideNavbarHeaderComponent, SideNavbarItemComponent,CommonModule],
  templateUrl: './side-navbar.component.html',
  styleUrl: './side-navbar.component.css'
})
export class SideNavbarComponent {
  navigationTitle = 'Main Navigation';

  menuItems: any[] = [
    {
      text: 'Dashboard',
      allowWrap: false
    },
    {
      text: 'Manage Accounts',
      allowWrap: false
    },
    {
      text: 'Transaction Management',
      allowWrap: false
    },
    {
      text: 'Lending & Borrowing',
      allowWrap: true
    },
    {
      text: 'Referral & Reward',
      allowWrap: true
    },
    {
      text: 'Offers & Promotions',
      allowWrap: true,
      hasCustomPadding: true
    },
    {
      text: 'Change Password',
      allowWrap: true,
      hasCustomPadding: true
    }
  ];

  trackByText(index: number, item: any): string {
    return item.text;
  }
}
