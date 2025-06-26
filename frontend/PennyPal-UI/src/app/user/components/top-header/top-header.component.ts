import { Component } from '@angular/core';
import { UserGreetingComponent } from "./user-greeting/user-greeting.component";
import { BalanceDisplayComponent } from "./balance-display/balance-display.component";
import { NotificationIconComponent } from "./notification-icon/notification-icon.component";
import { UserProfileIconComponent } from "./user-profile-icon/user-profile-icon.component";

@Component({
  selector: 'app-top-header',
  imports: [UserGreetingComponent, BalanceDisplayComponent, NotificationIconComponent, UserProfileIconComponent],
  templateUrl: './top-header.component.html',
  styleUrl: './top-header.component.css'
})
export class TopHeaderComponent {
  userInfo: { name: string; balance: number; notificationCount: number } = {
    name: 'Robert',
    balance: 345.34,
    notificationCount: 3
  };
}
