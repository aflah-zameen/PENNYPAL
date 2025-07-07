import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-transaction-header',
  imports: [],
  templateUrl: './transaction-header.component.html',
  styleUrl: './transaction-header.component.css'
})
export class TransactionHeaderComponent {
  @Output() seeAll = new EventEmitter<void>()

  onSeeAll(): void {
    this.seeAll.emit()
  }
}
