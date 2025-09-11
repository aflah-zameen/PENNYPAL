export type ActionType =
  | "GOAL_COMPLETION"
  | "LOAN_REPAYMENT"

export interface RewardPolicy {
  id: string
  actionType: ActionType
  coinAmount: number
  description?: string
  isActive: boolean
  isDeleted: boolean
  createdAt: Date
  updatedAt: Date
}

export interface CreateRewardPolicyRequest {
  actionType: ActionType
  coinAmount: number
  description?: string
  isActive: boolean
}

export interface UpdateRewardPolicyRequest extends CreateRewardPolicyRequest {
  id: string
}

export interface RewardPolicyFilter {
  search: string
  status: "all" | "active" | "inactive" | "deleted"
}

export interface PaginationConfig {
  page: number
  pageSize: number
  total: number
}

export interface ActionTypeOption {
  value: ActionType
  label: string
  description: string
}
