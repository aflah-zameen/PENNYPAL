import { PaymentMethod } from "./money-flow.model"

export interface WalletBalance {
  id: string
  userId: string
  balance: number
  lastUpdated: string
}

export interface WalletTransaction {
  id: string
  type: TransactionType
  amount: number
  description: string
  timestamp: string
  category?: string
  icon : string
}

export type TransactionType = "credit" | "debit"

export type TransactionStatus = "completed" | "pending" | "failed" | "cancelled"

export interface AddMoneyRequest {
  amount: number,
  pin : string
  cardId : string
  notes?: string
}



export type PaymentMethodType = "credit_card" | "debit_card" | "bank_account" | "paypal" | "apple_pay" | "google_pay"

export interface WalletStats {
  totalTransactions: number
  averageTransaction: number
}

export interface TransactionFilters {
  type: TransactionType[]
  status: TransactionStatus[]
  dateRange: {
    from: string | null
    to: string | null
  }
  amountRange: {
    min: number | null
    max: number | null
  }
  category: string
}
