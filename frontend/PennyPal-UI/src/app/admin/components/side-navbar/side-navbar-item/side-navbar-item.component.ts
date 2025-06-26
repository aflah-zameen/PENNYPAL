import { Component,Input } from '@angular/core';

@Component({
  selector: 'app-side-navbar-item',
  imports: [],
  templateUrl: './side-navbar-item.component.html',
  styleUrl: './side-navbar-item.component.css'
})
export class SideNavbarItemComponent {
  @Input() text: string = '';
  @Input() allowWrap: boolean = false;
  @Input() hasCustomPadding: boolean = false;

  get formattedText(): string {
    if (this.allowWrap) {
      return this.text.replace(/&/g, '&<br />');
    }
    return this.text;
  }
}
