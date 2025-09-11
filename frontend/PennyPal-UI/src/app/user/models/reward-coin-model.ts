export interface CoinTransaction {
  id: string
  userId: string
  type: CoinTransactionType
  amount: number
  description: string
  relatedEntityId?: string
  relatedEntityType?: string
  createdAt: Date
  status: TransactionStatus
}

export type CoinTransactionType =
  | "GOAL_COMPLETION"
  | "LOAN_REPAYMENT"
  | "REDEMPTION"
  | "EARNED"
  | "REDEEMED"
  | "BONUS"
  | "PENALTY"
  | "REFUND"
  | "EXPIRED"

export type TransactionStatus = "PENDING" | "COMPLETED" | "FAILED" | "CANCELLED"

export interface CoinBalance {
  availableCoins: number
  totalCoins: number
}

export interface PaymentMethod {
  id: string
  name: string
  type: "BANK_TRANSFER" | "PAYPAL" | "MOBILE_MONEY"
  details: string
  isActive: boolean
}

export interface RedemptionRequest {
  coinAmount: number
  realMoneyAmount: number
}

export interface RedemptionHistory {
  id: string
  coinAmount: number
  realMoneyAmount: number
  status: "PENDING" | "APPROVED" | "REJECTED"
  requestedAt: Date
  completedAt?: Date
}
