export interface ExpenseModel {
    id: number;
    name: string;
    userId: number;
    category: string;
    amount: number;
    startDate: Date;
    endDate:Date;
    type :string;
}