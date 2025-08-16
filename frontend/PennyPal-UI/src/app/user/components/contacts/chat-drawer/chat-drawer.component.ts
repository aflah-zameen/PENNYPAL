import { Component, ElementRef, EventEmitter, Input, NgZone, OnInit, Output, ViewChild } from '@angular/core';
import { Contact } from '../../../models/money-flow.model';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ChatMessage } from '../../../models/contact.model';

@Component({
  selector: 'app-chat-drawer',
  imports: [FormsModule, CommonModule],
  templateUrl: './chat-drawer.component.html',
  styleUrl: './chat-drawer.component.css'
})
export class ChatDrawerComponent implements OnInit {
  @Input() isOpen = false
  @Input() selectedContact: Contact | null = null
  @Output() close = new EventEmitter<void>()
  @ViewChild('chatContainer') private chatContainer!: ElementRef;


  chatMessages: ChatMessage[] = []
  newMessage = ""

  constructor(private ngZone: NgZone) {}

  ngOnInit() {
    // Mock chat messages
    this.chatMessages = [
      {
        id: "1",
        contactId: this.selectedContact?.id || "",
        message: "Hey! How are you doing?",
        timestamp: new Date(Date.now() - 3600000),
        isFromUser: false,
      },
      {
        id: "2",
        contactId: this.selectedContact?.id || "",
        message: "I'm doing great! Thanks for asking.",
        timestamp: new Date(Date.now() - 3000000),
        isFromUser: true,
      },
      {
        id: "3",
        contactId: this.selectedContact?.id || "",
        message: "Did you receive the payment I sent yesterday?",
        timestamp: new Date(Date.now() - 1800000),
        isFromUser: false,
      },
      {
        id: "4",
        contactId: this.selectedContact?.id || "",
        message: "Yes, I got it. Thank you so much!",
        timestamp: new Date(Date.now() - 1200000),
        isFromUser: true,
      },
    ]
  }

  onClose() {
    this.close.emit()
  }

  sendMessage() {
    if (this.newMessage.trim() && this.selectedContact) {
      const message: ChatMessage = {
        id: Date.now().toString(),
        contactId: this.selectedContact.id,
        message: this.newMessage.trim(),
        timestamp: new Date(),
        isFromUser: true,
      }

      this.chatMessages.push(message)
      this.newMessage = ""

      // Simulate response after 1 second
      setTimeout(() => {
        const response: ChatMessage = {
          id: (Date.now() + 1).toString(),
          contactId: this.selectedContact!.id,
          message: "Thanks for your message!",
          timestamp: new Date(),
          isFromUser: false,
        }
        this.chatMessages.push(response)
        this.scrollToBottom();
      }, 1000)
      this.scrollToBottom();
    }
  }

  scrollToBottom(): void {
    this.ngZone.runOutsideAngular(() => {
      setTimeout(() => {
        const el = this.chatContainer?.nativeElement;
        if (el) {
          el.scrollTo({ top: el.scrollHeight, behavior: 'smooth' });
        }
      }, 100); // wait for DOM update
    });
  }
}
