import { Component } from '@angular/core';
import { SummaryCardComponent } from "../../components/summary-card/summary-card.component";
import { FeatureButtonComponent } from "../../components/feature-button/feature-button.component";
import { TodayExpenseCardComponent } from "../../components/today-expense-card/today-expense-card.component";
import { RecentTransactionCardComponent } from "../../components/recent-transaction-card/recent-transaction-card.component";
import { LucideAngularModule,User,BanknoteArrowUp,BanknoteArrowDownIcon,HandCoins, Coins } from 'lucide-angular';

@Component({
  selector: 'app-home',
  imports: [SummaryCardComponent, FeatureButtonComponent, TodayExpenseCardComponent, RecentTransactionCardComponent,LucideAngularModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  readonly SendMoneyIcon = User;
  readonly AddIncomeIcon = BanknoteArrowUp;
  readonly AddExpenseIcon = BanknoteArrowDownIcon;
  readonly RequestMoney = HandCoins;
  readonly ReferAndEarn = Coins;
}
