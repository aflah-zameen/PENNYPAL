import { UserResponse } from "../../models/UserResponse";
import { UserCategoryResponse } from "./user-category.model";

export interface ExpenseModel {
    id: number;
    name: string;
    category: UserCategoryResponse;
    amount: number;
    startDate: Date;
    endDate:Date;
    type :string;
}