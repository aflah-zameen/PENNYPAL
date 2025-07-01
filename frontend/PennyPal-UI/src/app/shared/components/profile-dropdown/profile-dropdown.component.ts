import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { ProfileDropdown } from '../../../user/models/profile-dropdown';

@Component({
  selector: 'app-profile-dropdown',
  imports: [CommonModule  ],
  templateUrl: './profile-dropdown.component.html',
  styleUrl: './profile-dropdown.component.css'
})
export class ProfileDropdownComponent {
  @Input() items : ProfileDropdown[] = [];
}
