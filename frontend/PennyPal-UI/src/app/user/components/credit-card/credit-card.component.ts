import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CreditCard } from '../../models/CreditCard.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-credit-card',
  imports: [CommonModule],
  templateUrl: './credit-card.component.html',
  styleUrl: './credit-card.component.css'
})
export class CreditCardComponent {
  @Input() card!: CreditCard
  @Input() isSelected = false
  @Output() cardSelected = new EventEmitter<CreditCard>()

  onCardSelect() {
    this.cardSelected.emit(this.card)
  }
}
