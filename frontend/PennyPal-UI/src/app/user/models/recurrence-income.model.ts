export interface RecurrenceIncomeModel{
  title: string;              
  amount: number|null;               
  startDate: string;            
  endDate: string;            
  frequency: 'DAILY' | 'WEEKLY' | 'MONTHLY' | 'YEARLY';         
  category: number|null;            
  description?: string;
  isRecurring : boolean;
}