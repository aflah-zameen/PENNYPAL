import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-add-card-section',
  imports: [CommonModule],
  templateUrl: './add-card.component.html',
  styleUrl: './add-card.component.css'
})
export class AddCardComponentSection {
  @Output() addCard = new EventEmitter<void>()
  onAddCard() {
    this.addCard.emit()
  }
}
