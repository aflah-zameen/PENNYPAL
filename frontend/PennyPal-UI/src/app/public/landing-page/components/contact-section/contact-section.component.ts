import { Component, ElementRef, ViewChild } from '@angular/core';
import { ContactHeroSectionComponent } from "./contact-hero-section/contact-hero-section.component";


@Component({
  selector: 'app-contact-section',
  imports: [],
  templateUrl: './contact-section.component.html',
  styleUrl: './contact-section.component.css'
})
export class ContactSectionComponent {
   @ViewChild("contact", { static: true }) contact!: ElementRef<HTMLElement>
  ngAfterViewInit(): void {
    gsap.from(this.contact.nativeElement, {
      opacity: 0,
      y: 24,
      duration: 0.6,
      ease: "power2.out",
      scrollTrigger: { trigger: this.contact.nativeElement, start: "top 80%" },
    })
  }
}
