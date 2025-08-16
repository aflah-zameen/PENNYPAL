import { Component, OnInit } from '@angular/core';
import { UserGreetingComponent } from "./user-greeting/user-greeting.component";
import { BalanceDisplayComponent } from "./balance-display/balance-display.component";
import { NotificationIconComponent } from "./notification-icon/notification-icon.component";
import { UserProfileIconComponent } from "./user-profile-icon/user-profile-icon.component";
import { User } from '../../../models/User';
import { Observable } from 'rxjs';
import { AuthService } from '../../../public/auth/services/auth.service';
import { AsyncPipe } from '@angular/common';
import { NotificationService } from '../../../external-service/notification.service';
import { NotificationMessageDto } from '../../../models/notification';
import { NotificationDropdownComponent } from "../notification-dropdown/notification-dropdown.component";

@Component({
  selector: 'app-top-header',
  imports: [BalanceDisplayComponent,
    UserProfileIconComponent, AsyncPipe, NotificationDropdownComponent],
  templateUrl: './top-header.component.html',
  styleUrl: './top-header.component.css'
})
export class TopHeaderComponent implements OnInit {

  user$! : Observable<User|null> ;
  notifications$!: Observable<NotificationMessageDto[]>;
  
  constructor(private authService : AuthService,private notificationService: NotificationService) {
  }

  ngOnInit(): void {
    this.user$ = this.authService.user$;
    this.notifications$ = this.notificationService.notifications$;
    this.notificationService.loadNotifications().subscribe();
  }

  onMarkAsRead(notificationId: string): void { 
    this.notificationService.markAsRead(notificationId);
    this.notificationService.markAsReadBackend(notificationId).subscribe();
   }

 
}
