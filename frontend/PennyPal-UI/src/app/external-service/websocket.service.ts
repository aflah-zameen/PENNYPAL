import { Injectable, OnInit } from '@angular/core';
import { Client, IMessage, Stomp } from '@stomp/stompjs';
import { Subject } from 'rxjs';
import SockJS from 'sockjs-client';
import { NotificationMessageDto } from '../models/notification';
import { AuthService } from '../public/auth/services/auth.service';
import { User } from '../models/User';
import { Roles } from '../models/Roles';

@Injectable({
  providedIn: 'root'
})
export class WebsocketService{
private stompClient!: Client ;
private connected = false;
private notificationsSubject = new Subject<NotificationMessageDto>();
public notifications$ = this.notificationsSubject.asObservable();
private user: User | null = null;

  constructor(private authService: AuthService) {
    this.authService.user$.subscribe(user => {
      this.user = user;
      if(user) {
        this.connect();
      }
    });
  }

  connect(): void {
  if (this.user != null) {
    this.stompClient = new Client({
      webSocketFactory: () => new SockJS('http://localhost:8080/ws', null, {
        withCredentials: true,
      } as any),
      reconnectDelay: 5000,
      debug: (str) => console.log(str),
    });

    this.stompClient.onConnect = () => {
      console.log('✅ WebSocket connected');
      this.connected = true;

      // 🔔 Subscribe to notifications
      if (this.user?.roles.has(Roles.ADMIN)) {
        this.stompClient.subscribe('/topic/admin/notifications', (msg: IMessage) => {
          const notification: NotificationMessageDto = JSON.parse(msg.body);
          console.log('📩 Admin Notification:', notification);
          this.notificationsSubject.next(notification);
        });
      } else {
        this.stompClient.subscribe(`/user/queue/notifications`, (msg: IMessage) => {
          const notification: NotificationMessageDto = JSON.parse(msg.body);
          console.log('📩 User Notification:', notification);
          this.notificationsSubject.next(notification);
        });
      }
    };

    this.stompClient.activate();
  }
}


  send(destination: string, body: any): void {
    if (this.connected && this.stompClient) {
      this.stompClient.publish({
        destination: destination,
        body: JSON.stringify(body),
      });
    }
  }

}