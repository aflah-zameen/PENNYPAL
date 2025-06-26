import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NavBarComponent } from '../../../../shared/components/headers/public-header/nav-bar/nav-bar.component';
import { PublicFooterComponent } from '../../../../shared/components/footers/public-footer/public-footer.component';

@Component({
  selector: 'app-public-layout',
  imports: [NavBarComponent, RouterOutlet, PublicFooterComponent],
  templateUrl: './public-layout.component.html',
  styleUrl: './public-layout.component.css'
})
export class PublicLayoutComponent {

}
