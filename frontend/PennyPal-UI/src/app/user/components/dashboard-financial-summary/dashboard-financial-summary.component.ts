import { CommonModule, DecimalPipe } from '@angular/common';
import { Component, Input } from '@angular/core';
import { DashboardSumary } from '../../models/dashboard.model';
@Component({
  selector: 'app-dashboard-financial-summary',
  imports: [DecimalPipe,CommonModule],
  templateUrl: './dashboard-financial-summary.component.html',
  styleUrl: './dashboard-financial-summary.component.css'
})
export class DashboardFinancialSummaryComponent {
  @Input() summaryData!: DashboardSumary | null;
}
