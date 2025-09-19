import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { LendingRequest } from '../../../models/lend.model';
import { LendingService } from '../../../services/lending-management.serive';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-lending-requests-sent',
  imports: [CommonModule],
  templateUrl: './lending-requests-sent.component.html',
  styleUrl: './lending-requests-sent.component.css'
})
export class LendingRequestsSentComponent {
  requestsSent$!: Observable<LendingRequest[]>
  loading$!: Observable<boolean>

  constructor(public lendingService: LendingService) {}

  ngOnInit() {
    this.requestsSent$ = this.lendingService.lendingRequestSent$
    this.loading$ = this.lendingService.loading$
  }

  cancelRequest(requestId: string) {
    this.lendingService.cancelRequest(requestId).subscribe({
      next: (success) => {
        if (success) {
        }
      },
      error: (error) => {
        console.error("Error cancelling request:", error)
      },
    })
  }
}
