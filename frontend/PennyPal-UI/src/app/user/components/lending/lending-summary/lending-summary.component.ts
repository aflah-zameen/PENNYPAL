import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { LendingSummary } from '../../../models/lend.model';
import { LendingService } from '../../../services/lending-management.serive';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-lending-summary',
  imports: [CommonModule],
  templateUrl: './lending-summary.component.html',
  styleUrl: './lending-summary.component.css'
})
export class LendingSummaryComponent {
  summary$!: Observable<LendingSummary|null>

  constructor(public lendingService: LendingService) {}

  ngOnInit() {
    this.summary$ = this.lendingService.lendingSummary$
  }
}
