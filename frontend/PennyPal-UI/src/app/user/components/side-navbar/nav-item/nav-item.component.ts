import { CommonModule } from '@angular/common';
import { Component,Input } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-nav-item',
  imports: [CommonModule],
  templateUrl: './nav-item.component.html',
  styleUrl: './nav-item.component.css'
})
export class NavItemComponent {
  @Input() label = ""
  @Input() icon = ""
  @Input() route = ""
  @Input() isActive = false
  @Input() paddingLeft = false

  constructor(private router: Router) {}

  get iconClasses(): string {
    const baseClasses = "transition-all duration-300"
    const sizeClasses = "w-5 h-5"
    const colorClasses = this.isActive ? "text-white" : "text-gray-500 group-hover:text-blue-600"

    return `${baseClasses} ${sizeClasses} ${colorClasses}`
  }

  navigate(): void {
    if (this.route) {
      this.router.navigate([this.route])
    }
  }
}
