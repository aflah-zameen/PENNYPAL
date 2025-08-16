export interface Contact {
  id: string
  name: string
  email: string
  profileURL: string
  isOnline?: boolean
  transactions: Transaction[];
}

export interface PaymentMethod {
  id: string
  type: "card" | "wallet" | "bank"
  holder: string
  balance: number
  cardNumber: string
  name: string
  // isDefault: boolean
  isActive: boolean
  icon ?: string
}

export interface TransferRequest {
  recipientId: string
  amount: number
  note?: string
  pin: string
  paymentMethodId: string // Add this field
}

export interface Transaction {
  id: string
  senderId: string
  recipientId: string
  amount: number
  note?: string
  date: Date
  status: "pending" | "completed" | "failed"
  failureReason?: string
  type: "sent" | "received"
}


export interface UserBalance {
  available: number
  currency: string
}

export interface TransferStep {
  step: "amount" | "pin" | "processing" | "success" | "failed"
  data?: any
}
