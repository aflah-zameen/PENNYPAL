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
  @ViewChild('chatContainer') private chatContainer!: ElementRef<HTMLDivElement>;

  chatMessages: ChatMessageDto[] = [];
  newMessage = '';
  private shouldScroll = false;

  // reply & file state
  replyingTo: ChatMessageDto | null = null;
  selectedFile: File | null = null;
  selectedFilePreviewUrl: string | null = null;

  constructor(
    private ngZone: NgZone,
    private websocketService: WebsocketService,
    private contactService: ContactManagementService
  ) {}

  ngOnInit() {
    // subscribe to incoming messages
    this.websocketService.getMessage().subscribe((data) => {
      this.upsertMessage(data);
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
    if (!this.selectedContact) return;
    this.contactService.getMessageHistory(this.selectedContact.id).subscribe((data) => {
      this.chatMessages = data;
      this.shouldScroll = true;
    });
  }

  // upsert by chatId (server might send updates like deleted or edited)
  private upsertMessage(msg: ChatMessageDto) {
    const idx = this.chatMessages.findIndex(m => m.chatId === msg.chatId);
    if (idx >= 0) {
      this.chatMessages[idx] = msg;
    } else {
      this.chatMessages.push(msg);
    }
  }

  async sendMessage(): Promise<void> {
    if ((!this.newMessage.trim() && !this.selectedFile) || !this.selectedContact) return;

    const payload: any = {
      receiverId: this.selectedContact.id,
      content: this.newMessage || null,
      replyToMessageId: this.replyingTo ? this.replyingTo.chatId : null,
    };

    if (this.selectedFile) {
      // convert to base64 and send as file object
      try {
        const base64 = await this.fileToBase64(this.selectedFile);
        payload.file = {
          filename: this.selectedFile.name,
          mediaType: this.selectedFile.type || 'application/octet-stream',
          base64: base64
        };
      } catch (err) {
        console.error('Failed to convert file to base64', err);
        return;
      }
    }

    this.websocketService.sendChatMessage(payload);

    // optimistic UI - will be updated if server echoes real chatId
    // Optionally push a temporary message: simpler approach we rely on echo back
    this.newMessage = '';
    this.replyingTo = null;
    this.selectedFile = null;
    this.selectedFilePreviewUrl = null;
    this.shouldScroll = true;
  }

  // pick file from input change
  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.selectedFile = input.files[0];

      // preview (data url)
      const reader = new FileReader();
      reader.onload = () => {
        this.selectedFilePreviewUrl = reader.result as string;
      };
      reader.readAsDataURL(this.selectedFile);
    }
  }

  // reply helpers
  setReply(message: ChatMessageDto) {
    this.replyingTo = message;
  }
  cancelReply() {
    this.replyingTo = null;
  }

  // delete (soft delete)
  deleteMessage(message: ChatMessageDto, forEveryone = false) {
    if (!message || !message.chatId) return;
    this.websocketService.sendDeleteMessage(message.chatId, forEveryone);
  }

  scrollToBottom(): void {
    this.ngZone.runOutsideAngular(() => {
      setTimeout(() => {
        const el = this.chatContainer?.nativeElement;
        if (el) {
          el.scrollTo({ top: el.scrollHeight, behavior: 'smooth' });
        }
      }, 0);
    });
  }

  onClose(): void {
    this.close.emit();
  }

  private  fileToBase64(file: File): Promise<string> {
  return new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.onerror = () => reject(new Error('Failed to read file'));
    reader.onload = () => {
      const result = reader.result as string;
      // result is like "data:<mime>;base64,<base64data>"
      // we return just the base64 part (or you can send full data URL if backend expects)
      const commaIndex = result.indexOf(',');
      resolve(commaIndex >= 0 ? result.slice(commaIndex + 1) : result);
    };
    reader.readAsDataURL(file);
  });
}
}
