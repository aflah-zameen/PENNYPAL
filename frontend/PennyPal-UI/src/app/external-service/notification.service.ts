// notification.service.ts
import { Injectable, OnInit } from '@angular/core';
import { BehaviorSubject, map, Observable, tap } from 'rxjs';
import { NotificationMessageDto } from '../models/notification';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { ApiResponse } from '../models/ApiResponse';
import { WebsocketService } from './websocket.service';

@Injectable({ providedIn: 'root' })
export class NotificationService {
  private notifications: NotificationMessageDto[] = [];
  private notificationsSubject = new BehaviorSubject<NotificationMessageDto[]>([]);
  public notifications$ = this.notificationsSubject.asObservable();

  private apiURL = `${environment.apiBaseUrl}/api/private/notification`
  constructor(private http: HttpClient,private webSocketService: WebsocketService) {
    this.webSocketService.notifications$.subscribe(dto => {
      this.addNotification(dto);
    });
  }


  addNotification(dto: NotificationMessageDto): void {
  if (!this.notifications.some(n => n.id === dto.id)) {
    this.notifications.unshift({ ...dto, read: dto.read ?? false });
    this.notificationsSubject.next([...this.notifications]);
  }
}


  markAsRead(notificationId: string): void {
    this.notifications = this.notifications.map(n =>
      n.id === notificationId ? { ...n, read: true } : n
    );    
    this.notificationsSubject.next([...this.notifications]);
  }

  markAllAsRead(): void {
    this.notifications.forEach(n => n.read = true);
    this.notificationsSubject.next([...this.notifications]);
  }

  getUnreadCount(): number {
    return this.notifications.filter(n => !n.read).length;
  }

  markAsReadBackend(id: string): Observable<void> {
  return this.http.put<void>(`${this.apiURL}/read`,{}, {withCredentials: true, params: { notificationId: id }});
  }

  loadNotifications(): Observable<NotificationMessageDto[]> {
     return this.http.get<ApiResponse<NotificationMessageDto[]>>(`${this.apiURL}/user`, { withCredentials: true })
      .pipe(
        map(response => response.data),
        tap((data) => {
          this.notifications = data;
          this.notificationsSubject.next(this.notifications);
        })

      );
  }

  loadAdminNotifications(): Observable<NotificationMessageDto[]> {    
    return this.http.get<ApiResponse<NotificationMessageDto[]>>(`${this.apiURL}/admin`, { withCredentials: true })
      .pipe(
        map(response => response.data),
        tap((data) => {
          this.notifications = data;
          this.notificationsSubject.next(this.notifications);
        })
      );
  }


}
