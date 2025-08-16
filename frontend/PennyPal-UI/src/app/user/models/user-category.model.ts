import { CategoryUsageType } from "../../admin/models/category-management.model";

export interface UserCategoryResponse {
    categoryId: string;
    name: string;
    usageTypes : CategoryUsageType[]; 
    active : true;
    isDefault : boolean;
    sortOrder: number;
    color : string;
    icon : string;
}