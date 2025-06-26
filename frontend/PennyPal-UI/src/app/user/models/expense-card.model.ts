export interface ExpenseItem {
  category: string;
  spent: number;
  budget: number;
}

export interface ExpensesCardProps {
  expenses: ExpenseItem[];
}