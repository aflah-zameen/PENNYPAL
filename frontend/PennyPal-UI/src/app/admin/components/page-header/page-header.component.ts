import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
interface BreadcrumbItem {
  label: string
  route?: string
  active?: boolean
}
@Component({
  selector: 'app-page-header',
  imports: [CommonModule],
  templateUrl: './page-header.component.html',
  styleUrl: './page-header.component.css'
})
export class PageHeaderComponent {
  @Input() title = ""
  @Input() subtitle = ""
  @Input() breadcrumbs: BreadcrumbItem[] = []
  @Input() showActions = false

  constructor(private router: Router) {}

  navigateTo(route: string): void {
    this.router.navigate([route])
  }
}
