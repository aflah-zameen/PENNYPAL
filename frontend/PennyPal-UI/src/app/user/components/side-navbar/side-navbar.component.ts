import { Component } from '@angular/core';
import { NavBarComponent } from '../../../shared/components/headers/public-header/nav-bar/nav-bar.component';
import { NavItemComponent } from './nav-item/nav-item.component';
import { NavSectionComponent } from './nav-section/nav-section.component';
import { LogoComponent } from './logo/logo.component';

@Component({
  selector: 'app-side-navbar',
  imports: [NavSectionComponent,NavItemComponent,LogoComponent],
  templateUrl: './side-navbar.component.html',
  styleUrl: './side-navbar.component.css'
})
export class SideNavbarComponent {

}
