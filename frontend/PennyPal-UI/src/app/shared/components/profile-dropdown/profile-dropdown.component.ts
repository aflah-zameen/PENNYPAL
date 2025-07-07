import { CommonModule } from '@angular/common';
import { Component, EventEmitter, HostListener, Input, Output } from '@angular/core';
import { ProfileDropdown } from '../../../user/models/profile-dropdown';

@Component({
  selector: 'app-profile-dropdown',
  imports: [CommonModule  ],
  templateUrl: './profile-dropdown.component.html',
  styleUrl: './profile-dropdown.component.css'
})
export class ProfileDropdownComponent {
  @Input() items: ProfileDropdown[] = []
  @Output() itemClick = new EventEmitter<ProfileDropdown>()
  @Output() clickOutside = new EventEmitter<void>()

  @HostListener("document:click", ["$event"])
  onDocumentClick(event: Event): void {
    this.clickOutside.emit()
  }

  onItemClick(item: ProfileDropdown): void {
    this.itemClick.emit(item)
  }

  trackByItem(index: number, item: ProfileDropdown): string {
    return item.label
  }

  getIconBgClass(icon: string): string {
    const classes: { [key: string]: string } = {
      user: "from-blue-100 to-blue-200",
      settings: "from-gray-100 to-gray-200",
      help: "from-green-100 to-green-200",
      logout: "from-red-100 to-red-200",
    }
    return classes[icon] || classes['user']
  }

  getIconClass(icon: string): string {
    const classes: { [key: string]: string } = {
      user: "text-blue-600",
      settings: "text-gray-600",
      help: "text-green-600",
      logout: "text-red-600",
    }
    return classes[icon] || classes['user']
  }
}
