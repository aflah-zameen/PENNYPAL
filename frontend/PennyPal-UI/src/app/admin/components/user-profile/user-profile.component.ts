import { Component, EventEmitter, Input, Output } from '@angular/core';
import { AdminUser } from '../../models/admin.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-user-profile',
  imports: [CommonModule],
  templateUrl: './user-profile.component.html',
  styleUrl: './user-profile.component.css'
})
export class UserProfileComponent {
  @Input() user!: AdminUser
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
    switch (this.user.status) {
      case "online":
        return "bg-green-400"
      case "away":
        return "bg-yellow-400"
      case "offline":
        return "bg-gray-400"
      default:
        return "bg-gray-400"
    }
  }

  formatLastLogin(): string {
    const date = new Date(this.user.lastLogin)
    const now = new Date()
    const diffInHours = Math.floor((now.getTime() - date.getTime()) / (1000 * 60 * 60))

    if (diffInHours < 1) {
      return "Just now"
    } else if (diffInHours < 24) {
      return `${diffInHours}h ago`
    } else {
      const diffInDays = Math.floor(diffInHours / 24)
      return `${diffInDays}d ago`
    }
  }
}
