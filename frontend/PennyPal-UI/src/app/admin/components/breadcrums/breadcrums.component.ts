import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';

interface BreadcrumbItem {
  label: string
  active?: boolean
}

@Component({
  selector: 'app-breadcrums',
  imports: [CommonModule],
  templateUrl: './breadcrums.component.html',
  styleUrl: './breadcrums.component.css'
})
export class BreadcrumsComponent {
   @Input() items: BreadcrumbItem[] = []
}
