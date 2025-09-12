export interface Message {
  id: string
  message: string
  read: boolean
  timeStamp: string // ISO string format
  type: string
}