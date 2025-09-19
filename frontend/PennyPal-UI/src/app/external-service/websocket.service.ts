import { Injectable, OnInit } from '@angular/core';
import { Client, IMessage, Stomp } from '@stomp/stompjs';
import { BehaviorSubject, Subject } from 'rxjs';
import SockJS from 'sockjs-client';
import { NotificationMessageDto } from '../models/notification';
import { AuthService } from '../public/auth/services/auth.service';
import { User } from '../models/User';
import { Roles } from '../models/Roles';
import { ChatMessageDto } from '../user/models/chat.model';
import { OutgoingChatMessage } from '../user/components/contacts/chat-drawer/chat-drawer.component';

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
  const url = 'wss://api.sneakerheadaz.shop/ws';
  this.stompClient = new Client({
    brokerURL: url,
    reconnectDelay: 5000
  });

    this.stompClient.onConnect = () => {
      this.connected = true;

      // 🔔 Subscribe to notifications
      if (this.user?.roles.has(Roles.ADMIN)) {
        this.stompClient.subscribe('/topic/admin/notifications', (msg: IMessage) => {
          const notification: NotificationMessageDto = JSON.parse(msg.body);
          this.notificationsSubject.next(notification);
        });
      } else {
        this.stompClient.subscribe(`/user/queue/notifications`, (msg: IMessage) => {
          const notification: NotificationMessageDto = JSON.parse(msg.body);
          this.notificationsSubject.next(notification);
        });

        this.stompClient.subscribe(`/user/queue/messages`,(msg : IMessage)=>{
          const message: ChatMessageDto = JSON.parse(msg.body);
          this.chatMessageSubject.next(message);
        })
      }
    };

    this.stompClient.activate();
  }
}


  send(destination: string, body: unknown): void {
    if (this.connected && this.stompClient) {
      this.stompClient.publish({
        destination: destination,
        body: JSON.stringify(body),
      });
    }
  }

   sendChatMessage(payload: OutgoingChatMessage): void {
    if (this.stompClient?.active) {
      this.stompClient.publish({
        destination: '/app/chat.send',
        body: JSON.stringify(payload),
      });
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
    } else {
    }
  }

getMessage(){
  return this.chatMessages$;
}


}