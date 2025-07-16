import { IncomeResponseModel } from "./income.model";

export interface RecurringIncomesModel {

   recurringIncomeDTOS: IncomeResponseModel[];
    totalRecurring: number;
    monthlyTotal: number;
}
