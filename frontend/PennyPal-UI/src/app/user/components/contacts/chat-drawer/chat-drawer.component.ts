import { AfterViewChecked, Component, ElementRef, EventEmitter, Input, NgZone, OnChanges, OnInit, Output, SimpleChanges, ViewChild } from '@angular/core';
import { Contact } from '../../../models/money-flow.model';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ChatMessage } from '../../../models/contact.model';
import { WebsocketService } from '../../../../external-service/websocket.service';
import { ChatMessageDto } from '../../../models/chat.model';
import { ContactManagementService } from '../../../services/contact-management.service';

@Component({
  selector: 'app-chat-drawer',
  imports: [FormsModule, CommonModule],
  templateUrl: './chat-drawer.component.html',
  styleUrl: './chat-drawer.component.css'
})
export class ChatDrawerComponent implements OnInit, OnChanges, AfterViewChecked {
  @Input() isOpen = false;
  @Input() selectedContact: Contact | null = null;
  @Output() close = new EventEmitter<void>();
  @ViewChild('chatContainer') private chatContainer!: ElementRef;

  chatMessages: ChatMessageDto[] = [];
  newMessage = "";
  private shouldScroll = false;

  constructor(
    private ngZone: NgZone,
    private websocketService: WebsocketService,
    private contactService: ContactManagementService
  ) {}

  ngOnInit() {
    this.websocketService.getMessage().subscribe((data) => {
      this.chatMessages.push(data);
      this.shouldScroll = true;
    });
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['selectedContact'] && this.selectedContact) {
      this.loadMessageHistory();
    }
  }

  ngAfterViewChecked(): void {
    if (this.shouldScroll) {
      this.scrollToBottom();
      this.shouldScroll = false;
    }
  }

  private loadMessageHistory(): void {
    this.contactService.getMessageHistory(this.selectedContact!.id).subscribe((data) => {
      this.chatMessages = data;
      this.shouldScroll = true;
    });
  }

  sendMessage(): void {
    if (this.newMessage.trim() && this.selectedContact) {
      this.websocketService.sendChatMessage(this.selectedContact.id, this.newMessage);
      this.newMessage = "";
      this.shouldScroll = true;
    }
  }

  scrollToBottom(): void {
    this.ngZone.runOutsideAngular(() => {
      setTimeout(() => {
        const el = this.chatContainer?.nativeElement;
        if (el) {
          el.scrollTo({ top: el.scrollHeight, behavior: 'smooth' });
        }
      }, 0); // No delay needed here
    });
  }

  onClose(): void {
    this.close.emit();
  }
}
