import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-expense-item',
  imports: [],
  templateUrl: './expense-item.component.html',
  styleUrl: './expense-item.component.css'
})
export class ExpenseItemComponent {
  @Input() category!: string;
  @Input() spent!: number;
  @Input() budget!: number;
}
