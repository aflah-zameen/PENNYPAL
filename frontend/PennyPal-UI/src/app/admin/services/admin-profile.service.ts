import { Injectable } from "@angular/core"
import { BehaviorSubject, type Observable, of, delay } from "rxjs"
import type { AdminProfile, ProfileUpdateRequest } from "../models/admin.model"

@Injectable({
  providedIn: "root",
})
export class AdminProfileService {
  private profileSubject = new BehaviorSubject<AdminProfile | null>(null)

  profile$ = this.profileSubject.asObservable()

  constructor() {
    this.initializeMockProfile()
  }

  private initializeMockProfile() {
    const mockProfile: AdminProfile = {
      id: "admin-001",
      name: "Sarah Johnson",
      email: "sarah.johnson@company.com",
      avatar: "/placeholder.svg?height=120&width=120",
      role: {
        id: "role-001",
        name: "Senior Administrator",
        level: "Super Admin",
        description: "Full system access with all administrative privileges",
      },
      department: "Financial Operations",
      phoneNumber: "+1 (555) 123-4567",
      location: "New York, NY",
      timezone: "America/New_York",
      language: "English (US)",
      joinedDate: "2022-03-15T00:00:00Z",
      lastLogin: "2024-01-15T14:30:00Z",
      isActive: true,
      permissions: [
        {
          id: "perm-001",
          name: "User Management",
          category: "Users",
          description: "Create, edit, and delete user accounts",
          granted: true,
        },
        {
          id: "perm-002",
          name: "Transaction Oversight",
          category: "Transactions",
          description: "View and manage all transactions",
          granted: true,
        },
        {
          id: "perm-003",
          name: "System Configuration",
          category: "System",
          description: "Modify system settings and configurations",
          granted: true,
        },
        {
          id: "perm-004",
          name: "Reports & Analytics",
          category: "Reports",
          description: "Access to all reports and analytics",
          granted: true,
        },
        {
          id: "perm-005",
          name: "Security Management",
          category: "Security",
          description: "Manage security settings and policies",
          granted: false,
        },
      ],
      preferences: {
        theme: "light",
        notifications: {
          email: true,
          push: true,
          sms: false,
        },
        dashboard: {
          defaultView: "overview",
          refreshInterval: 30,
        },
        security: {
          twoFactorEnabled: true,
          sessionTimeout: 60,
        },
      },
      stats: {
        totalTransactionsHandled: 1247,
        totalUsersManaged: 89,
        averageResponseTime: 2.3,
        lastMonthActivity: 156,
        flaggedTransactionsResolved: 23,
      },
    }

    this.profileSubject.next(mockProfile)
  }

  updateProfile(updateData: ProfileUpdateRequest): Observable<AdminProfile> {
    const currentProfile = this.profileSubject.value
    if (!currentProfile) {
      throw new Error("No profile found")
    }

    const updatedProfile: AdminProfile = {
      ...currentProfile,
      name: updateData.name,
      phoneNumber: updateData.phoneNumber,
      location: updateData.location,
      timezone: updateData.timezone,
      language: updateData.language,
      // In real implementation, avatar would be uploaded to server
      avatar: updateData.avatar ? URL.createObjectURL(updateData.avatar) : currentProfile.avatar,
    }

    // Simulate API call
    return of(updatedProfile).pipe(delay(1000)).pipe(delay(500))
  }

  updateAvatar(file: File): Observable<string> {
    // Simulate avatar upload
    return of(URL.createObjectURL(file)).pipe(delay(1000))
  }

  updatePreferences(preferences: Partial<AdminProfile["preferences"]>): Observable<boolean> {
    const currentProfile = this.profileSubject.value
    if (!currentProfile) {
      return of(false)
    }

    const updatedProfile = {
      ...currentProfile,
      preferences: {
        ...currentProfile.preferences,
        ...preferences,
      },
    }

    this.profileSubject.next(updatedProfile)
    return of(true).pipe(delay(500))
  }

  changePassword(currentPassword: string, newPassword: string): Observable<boolean> {
    // Simulate password change
    return of(true).pipe(delay(1000))
  }

  enableTwoFactor(): Observable<{ qrCode: string; backupCodes: string[] }> {
    // Simulate 2FA setup
    return of({
      qrCode:
        "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNkYPhfDwAChwGA60e6kgAAAABJRU5ErkJggg==",
      backupCodes: ["123456", "789012", "345678", "901234", "567890"],
    }).pipe(delay(1000))
  }

  formatDateTime(dateString: string): string {
    return new Intl.DateTimeFormat("en-US", {
      year: "numeric",
      month: "long",
      day: "numeric",
      hour: "2-digit",
      minute: "2-digit",
    }).format(new Date(dateString))
  }

  getTimezones(): string[] {
    return [
      "America/New_York",
      "America/Chicago",
      "America/Denver",
      "America/Los_Angeles",
      "Europe/London",
      "Europe/Paris",
      "Europe/Berlin",
      "Asia/Tokyo",
      "Asia/Shanghai",
      "Australia/Sydney",
    ]
  }

  getLanguages(): { code: string; name: string }[] {
    return [
      { code: "en-US", name: "English (US)" },
      { code: "en-GB", name: "English (UK)" },
      { code: "es-ES", name: "Spanish" },
      { code: "fr-FR", name: "French" },
      { code: "de-DE", name: "German" },
      { code: "ja-JP", name: "Japanese" },
      { code: "zh-CN", name: "Chinese (Simplified)" },
    ]
  }
}
