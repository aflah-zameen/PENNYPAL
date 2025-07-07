import { UserCategoryResponse } from "./user-category.model"

export interface IncomeModel{
    id ?: number
    amount : number,
    source : UserCategoryResponse | null,
    income_date : string,
    recurrence : boolean,
    frequency : string,
    status : string
    description ?: string
    notes ?: string
    created_at ?: string
    updated_at ?: string
    user_id ?: number
}