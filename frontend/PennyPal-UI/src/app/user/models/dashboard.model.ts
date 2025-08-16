export interface DashboardSumary{
    expenseSummary: {
        totalExpenses: number;
        progressValue: number;
    };
    incomeSummary :{
        totalIncomes: number;
        progressValue: number;
    };
}

export interface ExpenseData {
  category: string;
  amount: number;
  percentage?: number;
  color?: string;
}
