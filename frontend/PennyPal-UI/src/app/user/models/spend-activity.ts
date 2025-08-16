import { UserCategoryResponse } from "./user-category.model"

export interface Transaction {
  id: number
  description: string
  amount: number
  date: string
  category: SpendingCategory
  type: "debit" | "credit"
  bankCard: BankCard
  merchant?: string
}

export interface SpendingCategory {
  id: string
  name: string
  emoji: string
  color: string
}

export interface BankCard {
  id: string
  name: string
  type: "credit" | "debit"
  lastFour: string
  color: string
}

export interface CategorySpending {
  category: UserCategoryResponse
  amount: number
  // percentage: number
  transactionCount: number
}

export interface SpendingSummary {
  totalSpend: number
  topCategory: CategorySpending | null
  mostUsedCard: BankCard & { amount: number } | null
  monthlyComparison: {
    currentMonth: number
    previousMonth: number
    percentage: number
    trend: "increase" | "decrease"
  }
}

export interface ChartData {
  categories: CategorySpending[]
  period: "day" | "week" | "month" | "year"
  viewMode: "bar" | "pie"
}
