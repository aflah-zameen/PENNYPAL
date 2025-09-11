import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-lending-summary-card',
  imports: [CommonModule],
  templateUrl: './lending-summary-card.component.html',
  styleUrl: './lending-summary-card.component.css'
})
export class LendingSummaryCardComponent {
  @Input() title!: string;
  @Input() label!: string;
  @Input() icon!: 'plus' | 'arrow-down' | 'warning' | 'clock';
  @Input() color!: 'green' | 'blue' | 'red' | 'yellow';
  @Input() value!: string | number;
}
