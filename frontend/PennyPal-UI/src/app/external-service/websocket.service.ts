import { Injectable, OnInit } from '@angular/core';
import { Client, IMessage, Stomp } from '@stomp/stompjs';
import { BehaviorSubject, Subject } from 'rxjs';
import SockJS from 'sockjs-client';
import { NotificationMessageDto } from '../models/notification';
import { AuthService } from '../public/auth/services/auth.service';
import { User } from '../models/User';
import { Roles } from '../models/Roles';
import { ChatMessageDto } from '../user/models/chat.model';

@Injectable({
  providedIn: 'root'
})
export class WebsocketService{
private stompClient!: Client ;
private connected = false;
private notificationsSubject = new Subject<NotificationMessageDto>();
private chatMessageSubject = new Subject<ChatMessageDto>();
public notifications$ = this.notificationsSubject.asObservable();
public chatMessages$ = this.chatMessageSubject.asObservable();

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
  const protocol = window.location.protocol === 'https:' ? 'https' : 'http';
  const host = window.location.host; // domain + port if needed
  const url = `${protocol}://${host}/ws`; // SockJS endpoint
    this.stompClient = new Client({
    webSocketFactory: () => new SockJS(url, null, { withCredentials: true } as any),
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

        this.stompClient.subscribe(`/user/queue/messages`,(msg : IMessage)=>{
          const message: ChatMessageDto = JSON.parse(msg.body);
          this.chatMessageSubject.next(message);
          console.log('📩 User Notification:', message);
        })
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

   sendChatMessage(payload: any): void {  
    if (this.stompClient?.active) {
      console.log(payload);
      this.stompClient.publish({
        destination: '/app/chat.send',
        body: JSON.stringify(payload),
      });
      console.log('📤 Sent chat over STOMP', payload);
    } else {
      console.warn('⚠️ STOMP client not active. Message not sent.');
    }
  }

sendDeleteMessage(messageId: string, forEveryone = false): void {
    if (this.stompClient?.active) {
      this.stompClient.publish({
        destination: '/app/chat.delete',
        body: JSON.stringify({ messageId, forEveryone }),
      });
      console.log('📤 Sent delete command', messageId, forEveryone);
    } else {
      console.warn('⚠️ STOMP client not active. Delete not sent.');
    }
  }

getMessage(){
  return this.chatMessages$;
}


}