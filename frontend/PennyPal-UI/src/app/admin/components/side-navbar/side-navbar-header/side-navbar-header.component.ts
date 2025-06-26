import { Component,Input } from '@angular/core';

@Component({
  selector: 'app-side-navbar-header',
  imports: [],
  templateUrl: './side-navbar-header.component.html',
  styleUrl: './side-navbar-header.component.css'
})
export class SideNavbarHeaderComponent {
   @Input() title: string = 'Main Navigation';
}
