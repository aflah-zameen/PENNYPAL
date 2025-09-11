export interface User {
  id: string
  name: string
  email: string
  avatar?: string
}

export interface Transaction {
  id: string
  type: TransactionType
  fromUser: User
  toUser?: User
  amount: number
  fees?: number
  status: TransactionStatus
  createdAt: string
  updatedAt: string
  notes?: string
  flagReason?: string
  retryAttempts?: number
  systemLogs?: string[]
  adminNotes?: string
}

export type TransactionType = "Income" | "Expense" | "Transfer" | "Wallet Recharge" | "Lending" | "Saving"

export type TransactionStatus = "Success" | "Pending" | "Failed" | "Reversed"

export interface TransactionFilters {
  types: TransactionType[]
  statuses: TransactionStatus[]
  dateRange: {
    from: string | null
    to: string | null
  }
  amountRange: {
    min: number | null
    max: number | null
  }
  userEmail: string
}

export interface PaginationInfo {
  currentPage: number
  totalPages: number
  totalItems: number
  itemsPerPage: number
}
