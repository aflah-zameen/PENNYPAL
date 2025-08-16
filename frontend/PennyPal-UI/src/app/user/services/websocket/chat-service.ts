// // chat.service.ts
// import { Injectable } from '@angular/core';
// import { Subject, Observable } from 'rxjs';

// @Injectable({ providedIn: 'root' })
// export class ChatService {
//   private chatSocket: WebSocket|null = null;
//   private messageSubject = new Subject<{ from: string; to: string; content: string }>();

//   connect(userId: string) {
//     this.chatSocket = new WebSocket(`ws://localhost:8080/ws/chat?userId=${userId}`);

//     this.chatSocket.onmessage = (event) => {
//       const data = JSON.parse(event.data);
//       this.messageSubject.next(data);
//     };
//   }

//   sendMessage(to: string, content: string) {
//     if(!this.chatSocket || this.chatSocket.readyState !== WebSocket.OPEN) {
//       console.error('WebSocket is not connected');
//       return;
//     }
//     const payload = {
//       type: 'message',
//       to,
//       content
//     };
//     this.chatSocket.send(JSON.stringify(payload));
//   }

//   getMessages(): Observable<{ from: string; to: string; content: string }> {
//     return this.messageSubject.asObservable();
//   }
// }
