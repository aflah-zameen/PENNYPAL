export interface LendingRequest {
  id: string
  senderId: string
  senderName: string
  recipientId: string
  recipientName: string
  amount: number
  requestDate: Date
  repaymentDeadline: Date
  status: "PENDING" | "ACCEPTED" | "REJECTED" | "CANCELLED"
  message?: string
  createdAt: Date
  updatedAt: Date
}

export interface RequestApproveForm{
  recipientId :string;
  amount : number;
  note : string;
  pin : string;
  paymentMethodId : string
}

export interface SentLentRequest{
  amount : number,
  message : string,
  requestedTo : string,
  proposedDeadline : string
}

export interface Loan {
  id: string
  lenderId: string
  lenderName: string
  borrowerId: string
  borrowerName: string
  amount: number
  loanDate: Date
  repaymentDeadline: Date
  repaymentStatus: "active" | "paid" | "overdue" | "partial"
  amountPaid: number
  remainingAmount: number
  isOverdue: boolean
  daysPastDue?: number
  lastReminderSentAt : Date
  createdAt: Date
  updatedAt: Date
  type : string
}

export interface LendingSummary {
  totalAmountLent: number
  totalAmountBorrowed: number
  activeLoansSent: number
  activeLoansReceived: number
  overdueLoansCount: number
  totalPendingRequests: number
}

export interface LendingStats {
  totalAmountLent: number;
  activeLoansSent: number;
  totalPendingRequests: number;
}

export interface BorrowingStats {
  totalAmountBorrowed: number;
  activeLoansReceived: number;
  overdueLoansCount: number;
}


export interface LendingFilters {
  status?: string
  dateRange?: {
    start: Date
    end: Date
  }
  amountRange?: {
    min: number
    max: number
  }
  searchTerm?: string
}

export interface RegisterCase{
  loanId : string,
  reason : string
}

export interface LoanCase{
  
}

export interface RepayTransactionResponse{
  id: string
  senderId: string
  recipientId: string
  amount: number
  note?: string
  date: Date
  status: "pending" | "completed" | "failed"
  failureReason?: string
  type: "sent" | "received"
  coins : number
}
