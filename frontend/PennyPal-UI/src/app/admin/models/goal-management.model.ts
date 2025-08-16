import { User } from "../../models/User"

export interface Goal {
  id: string
  user : User
  goalName: string
  targetAmount: number
  contributedAmount: number
  status: GoalStatus
  createdDate: string
  targetDate: string
  lastContribution: string
  category: string
  description?: string
  isActive: boolean
}

export type GoalStatus = "ACTIVE" | "COMPLETED" | "EXPIRED" | "WITHDRAW_PENDING" | "WITHDRAW_COLLECTED" | "CANCELLED"

export interface WithdrawalRequest {
  id: string
  goalId: string
  user : User
  amount: number
  requestDate: string
  status: WithdrawalStatus
}

export type WithdrawalStatus = "PENDING" | "APPROVED" | "REJECTED"

export interface GoalFilters {
  keyword: string
  status: GoalStatus[]
  dateRange: {
    from: string | null
    to: string | null
  }
  category: string
  minAmount: number | null
  maxAmount: number | null
}

export interface GoalAlert {
  id: string
  type: AlertType
  title: string
  message: string
  goalId?: string
  userId?: string
  timestamp: string
  isRead: boolean
  priority: "low" | "medium" | "high"
}

export type AlertType =
  | "withdrawal_request"
  | "goal_completed"
  | "insufficient_funds"
  | "suspicious_activity"
  | "system_error"

export interface PaginationInfo {
  page: number
  pageSize: number
  total: number
  totalPages: number
}

export interface GoalStats {
  totalGoals: number
  activeGoals: number
  completedGoals: number
  totalContributions: number
  pendingWithdrawals: number
}

export interface PaginatedGoalsResponse {
  data: Goal[];
  pagination: PaginationInfo;
}
