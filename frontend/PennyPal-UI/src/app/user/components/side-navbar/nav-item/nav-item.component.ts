import { CommonModule } from '@angular/common';
import { Component,Input } from '@angular/core';

@Component({
  selector: 'app-nav-item',
  imports: [CommonModule],
  templateUrl: './nav-item.component.html',
  styleUrl: './nav-item.component.css'
})
export class NavItemComponent {
  @Input() icon?: string;
  @Input() label!: string;
  @Input() isActive = false;
  @Input() paddingLeft = false;
}
