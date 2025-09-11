import { Component, Input } from '@angular/core';
import { LendingSummary } from '../../models/lend.model';
import { LendingService } from '../../services/lending-management.serive';
import { SummaryCardComponent } from "../summary-card/summary-card.component";
import { CommonModule } from '@angular/common';
import { LendingSummaryCardComponent } from "../lending-summary-card/lending-summary-card.component";

@Component({
  selector: 'app-lending-summary-card-template',
  imports: [CommonModule, LendingSummaryCardComponent],
  templateUrl: './lending-summary-card-template.component.html',
  styleUrl: './lending-summary-card-template.component.css'
})
export class LendingSummaryCardTemplateComponent {
  @Input() summary!: LendingSummary;
  @Input() mode: 'lending' | 'borrowing' = 'lending';

  constructor(public lendingService: LendingService) {}
}
