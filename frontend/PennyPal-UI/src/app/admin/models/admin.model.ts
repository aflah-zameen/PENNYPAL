
export interface AdminUser {
    id : string
  name: string
  email: string
  role: string
  avatar?: string
  lastLogin: string
  status: "online" | "offline" | "away",
  permissions: string[]
}