export interface RedemptionRequest {
  id: string
  userId: string
  userName: string
  userEmail: string
  coinAmount: number
  realMoneyAmount: number
  status: RedemptionStatus
  requestedAt: Date
  processedAt?: Date
  processedBy?: string
}

export interface PaymentMethod {
  type: "BANK_TRANSFER" | "PAYPAL" | "MOBILE_MONEY"
  details: string
  accountInfo: string
}

export type RedemptionStatus = "PENDING" | "APPROVED" | "REJECTED"

export interface RedemptionStats {
  totalCoinsEarned: number
  totalMoneyRedeemed: number
  pendingRequests: number
}

export interface RedemptionFilter {
  status: "ALL" | RedemptionStatus
  search: string
  dateFrom?: Date
  dateTo?: Date
}

export interface PaginationInfo {
  currentPage: number
  totalPages: number
  totalItems: number
  itemsPerPage: number
}

export interface RedemptionRequestsResponse {
  requests: RedemptionRequest[]
  pagination: PaginationInfo
}
