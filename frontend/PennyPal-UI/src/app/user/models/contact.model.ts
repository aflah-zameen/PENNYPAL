export interface Contact {
  id: string
  name: string
  email: string
  avatar: string
  isOnline?: boolean
}

export interface Transaction {
  id: string
  contactId: string
  amount: number
  type: "sent" | "received"
  date: Date
  status: "completed" | "pending" | "failed"
}

export interface ChatMessage {
  id: string
  contactId: string
  message: string
  timestamp: Date
  isFromUser: boolean
}
