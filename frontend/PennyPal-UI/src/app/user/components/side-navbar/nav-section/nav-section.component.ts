import { Component,Input } from '@angular/core';

@Component({
  selector: 'app-nav-section',
  imports: [],
  templateUrl: './nav-section.component.html',
  styleUrl: './nav-section.component.css'
})
export class NavSectionComponent {
  @Input() title!: string;
  @Input() isFirst = false;
  @Input() isLast = false;
}
