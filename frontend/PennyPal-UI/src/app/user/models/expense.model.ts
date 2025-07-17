import { UserResponse } from "../../models/UserResponse";
import { RecurringFrequency } from "../enums/income-frequency.enum";
import { UserCategoryResponse } from "./user-category.model";

export interface ExpenseResponseModel{
    id: number;
    title: string;
    amount: number;
    category: UserCategoryResponse;
    status : string;
    description : string;
    deleted: boolean

    //one-time expense
    paymentDate ?: string;

    // recurring income attributes
    isRecurring: boolean;
    frequency?: RecurringFrequency
    startDate?: Date;
    endDate?:Date;
    recurrenceActive?: boolean

    //Auditing field
    createdAt : string
}

export interface EditExpenseModel{
    id : number;
    title : string;
    amount : number;
    categoryId : number;
    description : string;
    frequency : RecurringFrequency;
    startDate : string;
    endDate : string;
}

export interface AddExpenseForm{
    title : string;
    amount : number|null;
    categoryId : number | null;
    description : string;
    isRecurring : boolean;

    //one-time
    paymentDate? : string

    //recurring attributes
    frequency? : RecurringFrequency;
    startDate? : string;
    endDate? : string; 
}

export interface PendingExpense{
    id : number;
    title : string;
    amount : number;
    paymentDate : string;
    category : UserCategoryResponse;
    description : string;
    status : string;

}




// Expense summary stats
export interface ExpenseSummary {
  totalExpenseSummary: {
    totalAmount: number
    progressValue: number
  }
  pendingExpenseSummary: {
    totalAmount: number
    pendingExpenses: number
  }
  activeRecurringExpense: {
    count: number
  }
}

// For recurring income summary and listing
export interface RecurringExpensesResponse {
  recurringExpenseList: ExpenseResponseModel[]
  totalRecurring: number
  monthlyTotal: number
}