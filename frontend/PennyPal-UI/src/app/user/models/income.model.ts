import { CategoryFilter, CategoryFormData, CategoryUsageType, Catgeory } from "../../admin/models/category-management.model"
import { RecurringFrequency } from "../enums/income-frequency.enum"

export interface IncomeResponseModel{
    id : number
    amount : number,
    title : string
    category : Catgeory,
    incomeDate ?: string,
    status : string
    description : string
    isRecurring : boolean,
    frequency ?: RecurringFrequency
    startDate ?: string;
    endDate ?: string;
    createdAt : string
    updatedAt : string,
    active : boolean,
    deleted : boolean}

export interface IncomeRequestModel{
    amount : number|null,
    title : string
    categoryId : number | null,
    incomeDate ?: string,
    description : string
    isRecurring : boolean,
    frequency ?: RecurringFrequency
    startDate ?: string;
    endDate ?: string;
}


