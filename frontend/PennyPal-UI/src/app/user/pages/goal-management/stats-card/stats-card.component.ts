import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-stats-card',
  imports: [CommonModule],
  templateUrl: './stats-card.component.html',
  styleUrl: './stats-card.component.css'
})
export class StatsCardComponent {
  @Input() title: string = '';
  @Input() value: string = '';
  @Input() subtitle?: string;
  @Input() iconBgClass: string = 'bg-blue-100';
  @Input() valueClass: string = 'text-gray-900';
  @Input() trend?: string;
  @Input() trendClass: string = 'text-green-600';
}
