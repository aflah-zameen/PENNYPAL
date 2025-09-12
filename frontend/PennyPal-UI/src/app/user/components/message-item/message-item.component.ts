import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { Message } from '../../models/message.model';

@Component({
  selector: 'app-message-item',
  imports: [CommonModule],
  templateUrl: './message-item.component.html',
  styleUrl: './message-item.component.css'
})
export class MessageItemComponent {
  @Input() message!: Message

  // onMessageClick(): void {
  //   this.messageClick.emit(this.message)
  //   if (!this.message.read) {
  //     this.markAsRead.emit(this.message.id)
  //   }
  // }

  // onMarkAsRead(event: Event): void {
  //   event.stopPropagation()
  //   this.markAsRead.emit(this.message.id)
  // }

  // onDelete(event: Event): void {
  //   event.stopPropagation()
  //   this.deleteMessage.emit(this.message.id)
  // }

  // getTypeIconClasses(): string {
  //   const classes: Record<NotificationType, string> = {
  //     [NotificationType.INFO]: "bg-blue-100 text-blue-600",
  //     [NotificationType.SUCCESS]: "bg-green-100 text-green-600",
  //     [NotificationType.WARNING]: "bg-yellow-100 text-yellow-600",
  //     [NotificationType.ERROR]: "bg-red-100 text-red-600",
  //     [NotificationType.SYSTEM]: "bg-gray-100 text-gray-600",
  //     [NotificationType.PROMOTION]: "bg-purple-100 text-purple-600",
  //     [NotificationType.REMINDER]: "bg-orange-100 text-orange-600",
  //   }
  //   return classes[this.message.type] || classes[NotificationType.INFO]
  // }

  // getTypeBadgeClasses(): string {
  //   const classes: Record<NotificationType, string> = {
  //     [NotificationType.INFO]: "bg-blue-100 text-blue-800",
  //     [NotificationType.SUCCESS]: "bg-green-100 text-green-800",
  //     [NotificationType.WARNING]: "bg-yellow-100 text-yellow-800",
  //     [NotificationType.ERROR]: "bg-red-100 text-red-800",
  //     [NotificationType.SYSTEM]: "bg-gray-100 text-gray-800",
  //     [NotificationType.PROMOTION]: "bg-purple-100 text-purple-800",
  //     [NotificationType.REMINDER]: "bg-orange-100 text-orange-800",
  //   }
  //   return classes[this.message.type] || classes[NotificationType.INFO]
  // }

  // getIconPath(): string {
  //   const paths: Record<NotificationType, string> = {
  //     [NotificationType.INFO]: "M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z",
  //     [NotificationType.SUCCESS]: "M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z",
  //     [NotificationType.WARNING]:
  //       "M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-2.5L13.732 4c-.77-.833-1.732-.833-2.464 0L3.34 16.5c-.77.833.192 2.5 1.732 2.5z",
  //     [NotificationType.ERROR]: "M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z",
  //     [NotificationType.SYSTEM]:
  //       "M10.325 4.317c.426-1.756 2.924-1.756 3.35 0a1.724 1.724 0 002.573 1.066c1.543-.94 3.31.826 2.37 2.37a1.724 1.724 0 001.065 2.572c1.756.426 1.756 2.924 0 3.35a1.724 1.724 0 00-1.066 2.573c.94 1.543-.826 3.31-2.37 2.37a1.724 1.724 0 00-2.572 1.065c-.426 1.756-2.924 1.756-3.35 0a1.724 1.724 0 00-2.573-1.066c-1.543.94-3.31-.826-2.37-2.37a1.724 1.724 0 00-1.065-2.572c-1.756-.426-1.756-2.924 0-3.35a1.724 1.724 0 001.066-2.573c-.94-1.543.826-3.31 2.37-2.37.996.608 2.296.07 2.572-1.065z",
  //     [NotificationType.PROMOTION]:
  //       "M7 4V2a1 1 0 011-1h8a1 1 0 011 1v2m-9 0h10m-10 0l1 16a1 1 0 001 1h8a1 1 0 001-1L18 4M9 9v6m6-6v6",
  //     [NotificationType.REMINDER]: "M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z",
  //   }
  //   return paths[this.message.type] || paths[NotificationType.INFO]
  // }

  // getTypeLabel(): string {
  //   const labels: Record<NotificationType, string> = {
  //     [NotificationType.INFO]: "Info",
  //     [NotificationType.SUCCESS]: "Success",
  //     [NotificationType.WARNING]: "Warning",
  //     [NotificationType.ERROR]: "Error",
  //     [NotificationType.SYSTEM]: "System",
  //     [NotificationType.PROMOTION]: "Promotion",
  //     [NotificationType.REMINDER]: "Reminder",
  //   }
  //   return labels[this.message.type] || "Info"
  // }

  getFormattedTime(): string {
    const now = new Date()
    const messageTime = new Date(this.message.timeStamp)
    const diffInMinutes = Math.floor((now.getTime() - messageTime.getTime()) / (1000 * 60))

    if (diffInMinutes < 1) return "Just now"
    if (diffInMinutes < 60) return `${diffInMinutes}m ago`

    const diffInHours = Math.floor(diffInMinutes / 60)
    if (diffInHours < 24) return `${diffInHours}h ago`

    const diffInDays = Math.floor(diffInHours / 24)
    if (diffInDays < 7) return `${diffInDays}d ago`

    return messageTime.toLocaleDateString()
  }
}
