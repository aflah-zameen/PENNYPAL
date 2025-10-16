import { CommonModule } from '@angular/common';
import { Component, ElementRef, EventEmitter, Output, ViewChild } from '@angular/core';
import {gsap} from 'gsap'

@Component({
  selector: 'app-hero-section',
  imports: [CommonModule],
  templateUrl: './hero-section.component.html',
  styleUrl: './hero-section.component.css'
})
export class HeroSectionComponent {
  @Output() demoLogin = new EventEmitter<"user" | "admin">()
  showDropdown = false;
  triggerDemo(role: "user" | "admin") {
    this.showDropdown = false
    this.demoLogin.emit(role)
  }
}
