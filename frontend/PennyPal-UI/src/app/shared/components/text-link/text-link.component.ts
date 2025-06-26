import { Component,Input } from '@angular/core';

@Component({
  selector: 'app-text-link',
  imports: [],
  templateUrl: './text-link.component.html',
  styleUrl: './text-link.component.css'
})
export class TextLinkComponent {
  @Input() text: string = '';
  @Input() href: string = '#';
}
