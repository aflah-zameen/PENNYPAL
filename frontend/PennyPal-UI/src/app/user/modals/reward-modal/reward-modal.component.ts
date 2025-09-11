import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ModalOverlayComponent } from "../modal-overlay/modal-overlay.component";

@Component({
  selector: 'app-reward-modal',
  imports: [ModalOverlayComponent],
  templateUrl: './reward-modal.component.html',
  styleUrl: './reward-modal.component.css'
})
export class RewardModalComponent {
  @Input() coins!: number;
  @Input() isModalOpen : boolean = false;
  @Output() close = new EventEmitter<void>();

  onOkay(): void {
    this.isModalOpen = false;
    this.close.emit();
  }
}
