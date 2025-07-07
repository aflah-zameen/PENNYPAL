import { UserCategoryResponse } from "./user-category.model";

export interface RecurringIncomesModel {

   recurringIncomeDTOS: {
      id : number,
    amount : number,
    source : string,
    incomeDate : string,
    recurrence : boolean,
    frequency : string,
    active : boolean,
  }[];
    totalRecurring: number;
    monthlyTotal: number;
}
