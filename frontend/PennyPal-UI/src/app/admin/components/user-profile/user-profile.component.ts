import { Component, EventEmitter, Input, Output } from '@angular/core';
import { AdminUser } from '../../models/admin.model';
import { CommonModule } from '@angular/common';
import { User } from '../../../models/User';
import { Roles } from '../../../models/Roles';

@Component({
  selector: 'app-user-profile',
  imports: [CommonModule],
  templateUrl: './user-profile.component.html',
  styleUrl: './user-profile.component.css'
})
export class UserProfileComponent {
  @Input() user! : User | null
  @Output() logout = new EventEmitter<void>()
  @Output() profileClick = new EventEmitter<void>()
  @Output() settingsClick = new EventEmitter<void>()

  isDropdownOpen = false

  toggleDropdown(): void {
    this.isDropdownOpen = !this.isDropdownOpen
  }

  onProfileClick(): void {
    this.profileClick.emit()
    this.isDropdownOpen = false
  }

  onSettingsClick(): void {
    this.settingsClick.emit()
    this.isDropdownOpen = false
  }

  onHelpClick(): void {
    // Navigate to help page or open help modal
    console.log("Help clicked")
    this.isDropdownOpen = false
  }

  onLogout(): void {
    this.logout.emit()
    this.isDropdownOpen = false
  }

  onImageError(event: Event): void {
    // Handle avatar image load error
    const target = event.target as HTMLImageElement
    target.style.display = "none"
  }

  getInitials(name: string): string {
    return name
      .split(" ")
      .map((word) => word.charAt(0))
      .join("")
      .toUpperCase()
      .slice(0, 2)
  }

  getStatusClass(): string {

        return "bg-green-400"
      }

  getRoles(roles: Set<Roles>): string {
  if (!roles || roles.size === 0) {
    return 'No roles assigned';
  }

  return Array.from(roles).join(', ');
}

}
