import { UserCategoryResponse } from "./user-category.model";

export interface Goal {
  id: string;
  title: string;
  currentAmount: number;
  targetAmount: number;
  startDate: string;
  endDate: string;
  status : string
  category: UserCategoryResponse;
  contributions?: Contribution[];
  createdAt : string
}

export interface GoalStats {
  totalActiveGoals: number;
  totalSaved: number;
  completedGoals: number;
}

export interface GoalFormData {
  title: string;
  targetAmount: number|null;
  startDate: string;
  endDate: string;
  description?: string;
  categoryId: string | null;
}

export interface GoalFormErrors {
  name?: string;
  amount?: string;
  startDate?: string;
  endDate?: string;
  general?: string;
  category?:string; 
}

export interface Contribution {
  id: number
  amount: number
  date: string
  notes?: string
}

export interface GoalFilterOptions {
  category: string
  progress: string
  sort: string
}
