import { Component } from '@angular/core';
import { Subject } from 'rxjs';
import { Message } from '../../models/message.model';
import { MessageService } from '../../services/message-management.service';
import { MessageHeaderComponent } from "../../components/message-header/message-header.component";
import { MessageListComponent } from "../../components/message-list/message-list.component";

@Component({
  selector: 'app-message-management',
  imports: [MessageHeaderComponent, MessageListComponent],
  templateUrl: './message-management.component.html',
  styleUrl: './message-management.component.css'
})
export class MessageManagementComponent {

  messages: Message[] = []

  constructor(private messageService: MessageService) {
    this.messageService.getAllMessages();
  }

  ngOnInit(): void {
    this.messageService.messages$.subscribe({
      next: (messages) => this.messages = messages,
      error: (error) => console.error("Error loading messages:", error),
    });
  }
}
