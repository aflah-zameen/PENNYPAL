import { CommonModule } from '@angular/common';
import { Component,Input } from '@angular/core';

@Component({
  selector: 'app-notification-icon',
  imports: [CommonModule],
  templateUrl: './notification-icon.component.html',
  styleUrl: './notification-icon.component.css'
})
export class NotificationIconComponent {
  @Input() count: number = 0;
}
