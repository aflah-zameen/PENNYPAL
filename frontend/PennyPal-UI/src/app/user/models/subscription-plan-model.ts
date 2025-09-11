export interface SubscriptionPlan {
  id: string
  name: string
  amount: number
  durationDays: number
  description?: string
  features?: string[]
  isPopular?: boolean
}

export interface PurchaseEvent {
  planId: string
  plan: SubscriptionPlan
}

export enum PlanDuration {
  MONTHLY = 30,
  QUARTERLY = 90,
  SEMI_ANNUAL = 180,
  ANNUAL = 365,
}
