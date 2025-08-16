import { Component } from '@angular/core';
import { CategorySpending, SpendingCategory, SpendingSummary} from '../../models/spend-activity';
import { SpendingDataService } from '../../services/spending-data.service';
import { CommonModule } from '@angular/common';
import { SpendSummaryCardComponent } from "./spend-summary-card/spend-summary-card.component";
import { SpendCategoryChartComponent } from "./spend-category-chart/spend-category-chart.component";
import { RecentSpendTransactionComponent } from "./recent-spend-transaction/recent-spend-transaction.component";
import { Transaction } from '../../models/transaction.model';
import { Observable } from 'rxjs';
import { UserService } from '../../services/user.service';
import { UserCategoryResponse } from '../../models/user-category.model';

@Component({
  selector: 'app-spend-activity',
  imports: [CommonModule, SpendSummaryCardComponent, SpendCategoryChartComponent, RecentSpendTransactionComponent],
  templateUrl: './spend-activity.component.html',
  styleUrl: './spend-activity.component.css'
})
export class SpendActivityComponent {
  transactions$!: Observable<Transaction[]> ;
  categories$!: Observable<UserCategoryResponse[]>;
  spendingSummary$!: Observable<SpendingSummary>;
  categorySpending$!: Observable<CategorySpending[]>;
  chartViewMode: "bar" | "pie" = "bar";

  constructor(private spendingDataService: SpendingDataService, private userService : UserService) {}

  ngOnInit() {
    this.loadData()
  }

  private loadData() {
      this.transactions$ = this.spendingDataService.getTransactions();      
      this.categories$ = this.userService.getCategories()
      this.spendingSummary$ = this.spendingDataService.getSpendingSummary()
      // this.categorySpending$ = this.spendingDataService.getCategorySpending()
  }

  onChartViewModeChange(mode: "bar" | "pie"): void {
    this.chartViewMode = mode
  }
}
