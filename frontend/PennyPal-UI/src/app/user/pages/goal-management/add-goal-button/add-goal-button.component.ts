import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-add-goal-button',
  imports: [],
  templateUrl: './add-goal-button.component.html',
  styleUrl: './add-goal-button.component.css'
})
export class AddGoalButtonComponent {
  @Output() addGoal = new EventEmitter<void>();
  onAddGoal(): void {
    this.addGoal.emit();
  }
}
