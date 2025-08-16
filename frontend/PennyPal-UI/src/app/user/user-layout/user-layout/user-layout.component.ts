import { Component, OnInit } from '@angular/core';
import { SideNavbarComponent } from "../../components/side-navbar/side-navbar.component";
import { TopHeaderComponent } from "../../components/top-header/top-header.component";
import { RouterOutlet } from '@angular/router';
import { WebsocketService } from '../../../external-service/websocket.service';
import { NotificationService } from '../../../external-service/notification.service';
import { AuthService } from '../../../public/auth/services/auth.service';

@Component({
  selector: 'app-user-layout',
  imports: [SideNavbarComponent, TopHeaderComponent, RouterOutlet],
  templateUrl: './user-layout.component.html',
  styleUrl: './user-layout.component.css'
})
export class UserLayoutComponent implements OnInit {

  constructor(private webSocketService : WebsocketService,private notificationService: NotificationService,private authService: AuthService) { }

  ngOnInit(): void {
    this.authService.loadUserFromToken().subscribe();
    this.webSocketService.notifications$.subscribe(notification => {
      this.notificationService.addNotification(notification);
    });
  }

}
