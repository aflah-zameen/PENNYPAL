
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

export interface AdminProfile {
  id: string
  name: string
  email: string
  avatar?: string
  role: AdminRole
  department: string
  phoneNumber?: string
  location?: string
  timezone: string
  language: string
  joinedDate: string
  lastLogin: string
  isActive: boolean
  permissions: AdminPermission[]
  preferences: AdminPreferences
  stats: AdminStats
}

export interface AdminRole {
  id: string
  name: string
  level: "Super Admin" | "Admin" | "Moderator" | "Support"
  description: string
}

export interface AdminPermission {
  id: string
  name: string
  category: string
  description: string
  granted: boolean
}

export interface AdminPreferences {
  theme: "light" | "dark" | "system"
  notifications: {
    email: boolean
    push: boolean
    sms: boolean
  }
  dashboard: {
    defaultView: string
    refreshInterval: number
  }
  security: {
    twoFactorEnabled: boolean
    sessionTimeout: number
  }
}

export interface AdminStats {
  totalTransactionsHandled: number
  totalUsersManaged: number
  averageResponseTime: number
  lastMonthActivity: number
  flaggedTransactionsResolved: number
}

export interface ProfileUpdateRequest {
  name: string
  phoneNumber?: string
  avatar?: File
}
