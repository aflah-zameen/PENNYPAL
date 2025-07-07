import { Component, Input } from '@angular/core';
import { CategoryStats } from '../../../models/category-management.model';

@Component({
  selector: 'app-category-stats',
  imports: [],
  templateUrl: './category-stats.component.html',
  styleUrl: './category-stats.component.css'
})
export class CategoryStatsComponent {
  @Input() stats!: CategoryStats
}
