export interface NewExpense {
  name: string;
  categoryId: number | null;
  amount: number | null;
  type: string;
  startDate: string;
  endDate: string;
}