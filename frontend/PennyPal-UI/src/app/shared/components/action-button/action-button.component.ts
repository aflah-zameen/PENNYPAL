import { CommonModule } from '@angular/common';
import { Component,EventEmitter,Input,Output } from '@angular/core';

@Component({
  selector: 'app-action-button',
  imports: [CommonModule],
  templateUrl: './action-button.component.html',
  styleUrl: './action-button.component.css'
})
export class ActionButtonComponent {
  @Input() text = '';
  @Input() type: 'button' | 'submit' = 'button';
  @Input() disabled : boolean = false;
  @Output() onClick = new EventEmitter<void>();
}
