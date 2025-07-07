import { CommonModule } from '@angular/common';
import { Component,EventEmitter,Input, Output } from '@angular/core';
import { MenuItem } from '../../../models/side-navbar.model';

@Component({
  selector: 'app-side-navbar-item',
  imports: [CommonModule],
  templateUrl: './side-navbar-item.component.html',
  styleUrl: './side-navbar-item.component.css'
})
export class SideNavbarItemComponent {
   @Input() item!: MenuItem
  @Output() itemClick = new EventEmitter<MenuItem>()

  isExpanded = false

  onItemClick(): void {
    if (this.item.subItems && this.item.subItems.length > 0) {
      this.isExpanded = !this.isExpanded
    } else {
      this.itemClick.emit(this.item)
    }
  }

  onSubItemClick(subItem: MenuItem): void {
    this.itemClick.emit(subItem)
  }

  getItemClasses(): string {
    const baseClasses = 'hover:bg-gray-50'
    
    if (this.item.isActive) {
      return `${baseClasses} bg-blue-50 text-blue-700 border-r-2 border-blue-600`
    }
    
    return `${baseClasses} text-gray-700 hover:text-gray-900`
  }

  getIconClasses(): string {
    if (this.item.isActive) {
      return 'text-blue-600'
    }
    return 'text-gray-400 group-hover:text-gray-600'
  }

  getBadgeClasses(): string {
    if (this.item.badge === 'New') {
      return 'bg-green-100 text-green-800'
    }
    return 'bg-gray-100 text-gray-800'
  }

  getSubItemClasses(subItem: MenuItem): string {
    const baseClasses = 'hover:bg-gray-50'
    
    if (subItem.isActive) {
      return `${baseClasses} bg-blue-50 text-blue-700`
    }
    
    return `${baseClasses} text-gray-600 hover:text-gray-900`
  }

  getSubIconClasses(subItem: MenuItem): string {
    if (subItem.isActive) {
      return 'text-blue-600'
    }
    return 'text-gray-400'
  }
}
