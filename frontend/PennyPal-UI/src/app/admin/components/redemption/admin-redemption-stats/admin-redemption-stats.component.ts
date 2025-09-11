import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { RedemptionStats } from '../../../models/admin-redemption-model';

@Component({
  selector: 'app-admin-redemption-stats',
  imports: [CommonModule],
  templateUrl: './admin-redemption-stats.component.html',
  styleUrl: './admin-redemption-stats.component.css'
})
export class AdminRedemptionStatsComponent {
    @Input() stats: RedemptionStats | null = null
}
