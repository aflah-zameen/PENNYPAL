import { Component } from '@angular/core';
import { HeroSectionComponent } from '../../components/hero-section/hero-section.component';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { PublicFooterComponent } from '../../../../shared/components/footers/public-footer/public-footer.component';
import { NavBarComponent } from '../../../../shared/components/headers/public-header/nav-bar/nav-bar.component';

@Component({
  selector: 'app-landing-page',
  imports: [HeroSectionComponent],
  templateUrl: './landing-page.component.html',
  styleUrl: './landing-page.component.css'
})
export class LandingPageComponent {

}
