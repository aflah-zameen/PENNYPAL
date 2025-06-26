import { Component,Input } from '@angular/core';

@Component({
  selector: 'app-notification-icon',
  imports: [],
  templateUrl: './notification-icon.component.html',
  styleUrl: './notification-icon.component.css'
})
export class NotificationIconComponent {
  @Input() count: number = 0;
}
