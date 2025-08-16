import { Component, ElementRef, EventEmitter, HostListener, Input, Output } from '@angular/core';
import { NotificationMessageDto } from '../../../models/notification';
import { NotificationIconComponent } from "../top-header/notification-icon/notification-icon.component";
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-notification-dropdown',
  imports: [NotificationIconComponent,CommonModule],
  templateUrl: './notification-dropdown.component.html',
  styleUrl: './notification-dropdown.component.css'
})
export class NotificationDropdownComponent {
  @Input() notifications: NotificationMessageDto[] = [];
  
  // Output: Emits when an individual notification is marked as read
  @Output() markAsRead = new EventEmitter<string>();
  
  // Output: Emits when all notifications should be marked as read
  @Output() markAllAsRead = new EventEmitter<void>();

  isOpen = false;

  constructor(private el: ElementRef) { }

  ngOnInit(): void {
    
  }
  
  // Method to be triggered by the notification icon's output event
  toggleDropdown(): void {
    
    this.isOpen = !this.isOpen;
  }

  // HostListener to close the dropdown when clicking anywhere else
  @HostListener('document:click', ['$event'])
  onClick(event: Event) {
    if (!this.el.nativeElement.contains(event.target)) {
      this.isOpen = false;
    }
  }

  // ... (rest of your existing methods like onNotificationClick, onMarkAllAsRead, formatTimestamp) ...
  onNotificationClick(notification: NotificationMessageDto): void {
    if (!notification.read) {
      this.markAsRead.emit(notification.id);
    }
  }
  
  onMarkAllAsRead(): void {
    this.markAllAsRead.emit();
  }

  formatTimestamp(timestamp: string): string {
    // ... your timestamp formatting logic here ...
    return "";
  }

  get unreadCount(): number {    
    return this.notifications ? this.notifications.filter(n => !n.read).length : 0;
  }

  onMarkAsRead(notificationId: string){
    this.markAsRead.emit(notificationId);
  }
}
