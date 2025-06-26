import { Component } from '@angular/core';
import { ContactHeroSectionComponent } from "./contact-hero-section/contact-hero-section.component";


@Component({
  selector: 'app-contact-section',
  imports: [ContactHeroSectionComponent],
  templateUrl: './contact-section.component.html',
  styleUrl: './contact-section.component.css'
})
export class ContactSectionComponent {

}
