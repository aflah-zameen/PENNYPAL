import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { GoalStats } from '../../../models/goal-management.model';
import { GoalDashboardService } from '../../../services/goal-management.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-goal-stats',
  imports: [CommonModule],
  templateUrl: './goal-stats.component.html',
  styleUrl: './goal-stats.component.css'
})
export class GoalStatsComponent {
  stats$!: Observable<GoalStats>

  constructor(public goalDashboardService: GoalDashboardService) {}

  ngOnInit() {
    this.stats$ = this.goalDashboardService.getGoalStats()
  }
}
