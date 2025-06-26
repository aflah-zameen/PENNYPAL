import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-nav-links',
  imports: [CommonModule,RouterLink],
  templateUrl: './nav-links.component.html',
  styleUrl: './nav-links.component.css'
})
export class NavLinksComponent {

}
