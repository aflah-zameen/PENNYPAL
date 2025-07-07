export interface Goal {
  id: number;
  title: string;
  currentAmount: number;
  targetAmount: number;
  startDate: string;
  endDate: string;
  category: 'apartment' | 'car' | 'vacation' | 'education' | 'other';
  color: string;
  contributions?: Contribution[]
}

export interface GoalStats {
  totalActiveGoals: number;
  totalSaved: number;
  completedGoals: number;
}

export interface GoalFormData {
  name: string;
  amount: number;
  startDate: string;
  endDate: string;
  description?: string;
  category: 'apartment' | 'car' | 'vacation' | 'education' | 'other';
}

export interface GoalFormErrors {
  name?: string;
  amount?: string;
  startDate?: string;
  endDate?: string;
  general?: string;
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
