import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { LogoComponent } from './logo/logo.component';
import { NavLinksComponent } from './nav-links/nav-links.component';
import { ActionButtonsComponent } from './action-buttons/action-buttons.component';

@Component({
  selector: 'app-nav-bar',
  imports: [CommonModule,LogoComponent,NavLinksComponent,ActionButtonsComponent],
  templateUrl: './nav-bar.component.html',
  styleUrl: './nav-bar.component.css'
})
export class NavBarComponent {

}
