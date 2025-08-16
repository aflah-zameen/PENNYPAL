import { Component } from '@angular/core';
import { SideNavbarComponent } from "../../../admin/components/side-navbar/side-navbar.component";
import { MainHeaderComponent } from "../../components/main-header/main-header.component";
import { ActivatedRoute, NavigationEnd, Router, RouterLinkActive, RouterOutlet } from '@angular/router';
import { filter } from 'rxjs';
import { CommonModule } from '@angular/common';
import { WebsocketService } from '../../../external-service/websocket.service';
import { NotificationService } from '../../../external-service/notification.service';
import { NotificationDropdownComponent } from "../../../user/components/notification-dropdown/notification-dropdown.component";
import { NotificationMessageDto } from '../../../models/notification';
import { NotificationIconComponent } from "../../../user/components/top-header/notification-icon/notification-icon.component";

@Component({
  selector: 'app-admin-layout',
  imports: [SideNavbarComponent, RouterOutlet, CommonModule, NotificationDropdownComponent, NotificationIconComponent],
  templateUrl: './admin-layout.component.html',
  styleUrl: './admin-layout.component.css'
})
export class AdminLayoutComponent {
  currentRoute = "";
  notifications : NotificationMessageDto[] = [];


  constructor(private router: Router,private websocketService: WebsocketService, private notificationService: NotificationService) {}

  ngOnInit() {
    // Listen to route changes to update active navigation
    this.router.events.pipe(filter((event) => event instanceof NavigationEnd)).subscribe((event: NavigationEnd) => {
      this.currentRoute = event.urlAfterRedirects
    })

    // Set initial route
    this.currentRoute = this.router.url
    this.websocketService.notifications$.subscribe(notification => {
      this.notificationService.addNotification(notification);
    });

    this.notificationService.notifications$.subscribe(notifications => {
      this.notifications = notifications;
    });    
    this.notificationService.loadAdminNotifications().subscribe();

  }

  onMarkAsRead(notificationId: string): void {
    this.notificationService.markAsRead(notificationId);
    this.notificationService.markAsReadBackend(notificationId).subscribe();
  }
  onMarkAllAsRead(): void {
    // this.notificationService.markAllAsRead();
    // this.notificationService.markAllAsReadBackend().subscribe();
  }
}
