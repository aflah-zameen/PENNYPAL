import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Message } from '../../models/message.model';
import { MessageItemComponent } from "../message-item/message-item.component";

@Component({
  selector: 'app-message-list',
  imports: [CommonModule, MessageItemComponent],
  templateUrl: './message-list.component.html',
  styleUrl: './message-list.component.css'
})
export class MessageListComponent {
  @Input() messages: Message[] = []
  @Input() emptyStateMessage = "You don't have any messages yet. When you receive messages, they'll appear here."

  @Output() markAsRead = new EventEmitter<string>()
  @Output() deleteMessage = new EventEmitter<string>()
  @Output() messageClick = new EventEmitter<Message>()

  trackByMessageId(index: number, message: Message): string {
    return message.id
  }

  // onMarkAsRead(messageId: string): void {
  //   this.markAsRead.emit(messageId)
  // }

  // onDeleteMessage(messageId: string): void {
  //   this.deleteMessage.emit(messageId)
  // }

  // onMessageClick(message: Message): void {
  //   this.messageClick.emit(message)
  // }
}
