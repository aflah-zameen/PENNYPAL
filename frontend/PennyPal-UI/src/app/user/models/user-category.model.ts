import { CategoryUsageType } from "../../admin/models/category-management.model";

export interface UserCategoryResponse {
    id: number;
    name: string;
    usageTypes : CategoryUsageType[]; 
    active : true;
    isDefault : boolean;
    sortOrder: number;
    color : string;
    icon : string;
}