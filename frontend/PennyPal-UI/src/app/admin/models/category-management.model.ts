export interface AdminCategory {
  id?: number
  name: string
  description?: string
  icon: string
  color: string
  usageTypes: CategoryUsageType[]
  active: boolean
  default: boolean
  sortOrder: number
  createdAt?: string
  updatedAt?: string
  createdBy?: string
  usageCount: number
}

export type CategoryUsageType = "GOAL" | "INCOME" | "EXPENSE" | "SHARED"

export interface CategoryFormData {
  name: string
  description?: string
  icon: string
  color: string
  usageTypes: CategoryUsageType[]
  active: boolean
  sortOrder: number
}

export interface Catgeory{
  id : number;
  name : string;
  usageTypes : CategoryUsageType[];
  description : string;
  color : string;
  icon : string;
  sortOrder : number;
  active : boolean;
  isDefault : boolean;
}

export interface CategoryFilter {
  usageTypes: CategoryUsageType | "all"
  status: "all" | "active" | "inactive"
  search: string
}

export interface CategoryStats {
  total: number
  active: number
  inactive: number
  byUsageType: Record<CategoryUsageType, number>
}
