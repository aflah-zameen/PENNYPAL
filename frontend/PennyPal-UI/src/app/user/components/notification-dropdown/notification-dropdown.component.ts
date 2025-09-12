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
    console.log(timestamp);
    
    if (!timestamp) return '';
  // Keep only first 3 digits of fractional seconds
  const safeTimestamp = timestamp.replace(/\.(\d{3})\d+Z$/, '.$1Z')

  const now = new Date()
  const messageTime = new Date(safeTimestamp)

  const diffInMinutes = Math.floor((now.getTime() - messageTime.getTime()) / (1000 * 60))

  if (diffInMinutes < 1) return "Just now"
  if (diffInMinutes < 60) return `${diffInMinutes}m ago`

  const diffInHours = Math.floor(diffInMinutes / 60)
  if (diffInHours < 24) return `${diffInHours}h ago`

  const diffInDays = Math.floor(diffInHours / 24)
  if (diffInDays < 7) return `${diffInDays}d ago`

  return messageTime.toLocaleDateString()
}


  get unreadCount(): number {    
    return this.notifications ? this.notifications.filter(n => !n.read).length : 0;
  }

  onMarkAsRead(notificationId: string){
    this.markAsRead.emit(notificationId);
  }
}
