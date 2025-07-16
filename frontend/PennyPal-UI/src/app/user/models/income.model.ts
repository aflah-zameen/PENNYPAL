import { CategoryFilter, CategoryFormData, CategoryUsageType, Catgeory } from "../../admin/models/category-management.model"
import { IncomeFrequency } from "../enums/income-frequency.enum"

export interface IncomeResponseModel{
    id : number
    amount : number,
    title : string
    category : Catgeory,
    incomeDate ?: string,
    status : string
    description : string
    isRecurring : boolean,
    frequency ?: IncomeFrequency
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
    frequency ?: IncomeFrequency
    startDate ?: string;
    endDate ?: string;
}

export interface RecentIncomeTransaction{
    id : number;
    title :string;
    amount : number;
    transactionDate : Date;
    category : {
        name : string,
        icon : string,
        color : string
    }

}
