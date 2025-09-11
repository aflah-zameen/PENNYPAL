export interface AdminSubscriptionPlan {
  id: string
  name: string
  amount: number
  durationDays: number
  features: string[]
  createdDate: Date
  updatedDate: Date
  isActive: boolean
  subscriberCount: number
  description?: string
}

export interface CreatePlanRequest {
  name: string
  amount: number
  durationDays: number
  features: string[]
  description?: string
}

export interface UpdatePlanRequest extends CreatePlanRequest {
  id: string
  // isActive: boolean
}

export interface PlanSubscriber {
  id: string
  userId: string
  userName: string
  userEmail: string
  subscriptionDate: Date
  expiryDate: Date
  status: "active" | "expired" | "cancelled"
  paymentMethod: string
}

export interface TableColumn {
  key: string
  label: string
  sortable: boolean
  type: "text" | "currency" | "date" | "number" | "badge" | "actions"
}

export interface SortConfig {
  key: string
  direction: "asc" | "desc"
}
