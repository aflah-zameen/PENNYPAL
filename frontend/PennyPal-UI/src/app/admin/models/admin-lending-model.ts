export interface LoanCase {
  id: string
  loanId: string
  borrowerId: string
  borrowerName: string
  lenderName: string
  amount: number
  filedDate: Date
  status: 'OPEN'|'UNDER_REVIEW'|'RESOLVED'| 'ESCALATED'
  reason: string
  adminNotes?: string
  resolvedDate?: Date
  createdAt: Date
  updatedAt: Date
}

export interface AdminAction {
  id: string
  userId: string
  userName: string
  action: "suspend" | "block" | "ban" | "warning" | "note"
  reason: string
  duration?: number // in days
  adminId: string
  adminName: string
  createdAt: Date
  expiresAt?: Date
}

export interface UserViolation {
  id: string
  userId: string
  userName: string
  violationType: "late_payment" | "non_payment" | "fraud" | "harassment" | "other"
  description: string
  severity: "low" | "medium" | "high" | "critical"
  loanId?: string
  reportedBy: string
  createdAt: Date
}
