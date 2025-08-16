import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-stat-card',
  imports: [CommonModule],
  templateUrl: './stat-card.component.html',
  styleUrl: './stat-card.component.css'
})
export class StatCardComponent {
  @Input() title!: string
  @Input() amount!: number
  @Input() changePercent!: string
  @Input() trend: "up" | "down" = "up"

  get chartColor(): string {
    return this.trend === "up" ? "text-green-500" : "text-red-500"
  }

  get trendColor(): string {
    return this.trend === "up" ? "text-green-600" : "text-red-600"
  }
}
