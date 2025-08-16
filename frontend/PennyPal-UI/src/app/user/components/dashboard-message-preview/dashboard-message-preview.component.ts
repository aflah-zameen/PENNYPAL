import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
export interface Message {
  id: string;
  senderName: string;
  senderAvatar?: string;
  message: string;
  timestamp: string;
  isRead: boolean;
  messageType: 'lending' | 'borrowing' | 'general';
  amount?: number;
}
@Component({
  selector: 'app-dashboard-message-preview',
  imports: [CommonModule],
  templateUrl: './dashboard-message-preview.component.html',
  styleUrl: './dashboard-message-preview.component.css'
})
export class DashboardMessagePreviewComponent {
  @Input() latestMessage: Message | null = null;
  @Input() messages: Message[] = [];

  ngOnInit() {
    if (!this.latestMessage && this.messages.length === 0) {
      this.messages = this.generateSampleMessages();
      this.latestMessage = this.messages[0];
    }
  }

  private generateSampleMessages(): Message[] {
    return [
      {
        id: '1',
        senderName: 'Sarah Johnson',
        message: 'Hey! Just wanted to confirm the $200 I borrowed last week. I can pay you back this Friday if that works?',
        timestamp: '5 minutes ago',
        isRead: false,
        messageType: 'borrowing',
        amount: 200
      },
      {
        id: '2',
        senderName: 'Mike Chen',
        message: 'Thanks for lending me the money for the concert tickets! Had an amazing time 🎵',
        timestamp: '2 hours ago',
        isRead: true,
        messageType: 'lending',
        amount: 150
      },
      {
        id: '3',
        senderName: 'Emma Wilson',
        message: 'Could you help me split the dinner bill from last night? It was $80 total.',
        timestamp: '1 day ago',
        isRead: false,
        messageType: 'general',
        amount: 40
      },
      {
        id: '4',
        senderName: 'Alex Rodriguez',
        message: 'Just transferred the $300 for the group gift. Let me know when you receive it!',
        timestamp: '2 days ago',
        isRead: true,
        messageType: 'lending',
        amount: 300
      }
    ];
  }

  getInitials(name: string): string {
    return name
      .split(' ')
      .map(n => n.charAt(0))
      .join('')
      .toUpperCase()
      .substring(0, 2);
  }

  getAvatarClasses(messageType: string): string {
    switch (messageType) {
      case 'lending':
        return 'bg-gradient-to-r from-green-400 to-green-500';
      case 'borrowing':
        return 'bg-gradient-to-r from-blue-400 to-blue-500';
      default:
        return 'bg-gradient-to-r from-purple-400 to-purple-500';
    }
  }

  getMessageTypeClasses(messageType: string): string {
    switch (messageType) {
      case 'lending':
        return 'bg-green-100 text-green-700';
      case 'borrowing':
        return 'bg-blue-100 text-blue-700';
      default:
        return 'bg-purple-100 text-purple-700';
    }
  }

  getUnreadCount(): number {
    return this.messages.filter(m => !m.isRead).length;
  }

  getRecentMessages(): Message[] {
    return this.messages.slice(1, 4); // Skip the latest message as it's shown separately
  }

  getPendingRequests(): number {
    return this.messages.filter(m => m.messageType === 'borrowing' && !m.isRead).length;
  }

  getActiveLoans(): number {
    return this.messages.filter(m => m.messageType === 'lending').length;
  }
}
