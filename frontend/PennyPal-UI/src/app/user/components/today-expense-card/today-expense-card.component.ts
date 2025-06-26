import { Component } from '@angular/core';
import { ExpenseItemComponent } from "./expense-item/expense-item.component";
import { ExpenseHeaderComponent } from "./expense-header/expense-header.component";

@Component({
  selector: 'app-today-expense-card',
  imports: [ExpenseItemComponent, ExpenseHeaderComponent],
  templateUrl: './today-expense-card.component.html',
  styleUrl: './today-expense-card.component.css'
})
export class TodayExpenseCardComponent {

}
