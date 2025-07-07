export interface UserCategoryResponse {
    id: number;
    name: string;
    usageTypes : string[]; 
    active : true;
    isDefault : boolean;
    sortOrder: number;
}