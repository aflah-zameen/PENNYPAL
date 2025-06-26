import { CommonModule } from '@angular/common';
import { Component, HostListener } from '@angular/core';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-action-buttons',
  imports: [CommonModule,RouterModule],
  templateUrl: './action-buttons.component.html',
  styleUrl: './action-buttons.component.css'
})
export class ActionButtonsComponent {
  constructor (private router : Router){}
  isDropdownOpen = false;

  // Toggle dropdown visibility
  toggleDropdown() {
    this.isDropdownOpen = !this.isDropdownOpen;
  }
   @HostListener('document:click', ['$event'])
  onClickOutside(event: MouseEvent) {
    // If the click target is outside the dropdown or the button, close the dropdown
    const dropdown = document.querySelector('.dropDown');
    const button = document.querySelector('button');

    if (dropdown && !dropdown.contains(event.target as Node) && !button?.contains(event.target as Node)) {
      this.isDropdownOpen = false;
    }
  }

}
