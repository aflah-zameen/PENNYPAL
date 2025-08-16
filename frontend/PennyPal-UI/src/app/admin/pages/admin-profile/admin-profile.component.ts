import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { AdminProfile } from '../../models/admin.model';
import { AdminProfileService } from '../../services/admin-profile.service';
import { SecuritySettingComponent } from "../../components/profile/security-setting/security-setting.component";
import { ProfileEditModalComponent } from "../../components/profile/profile-edit-modal/profile-edit-modal.component";
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-admin-profile',
  imports: [SecuritySettingComponent, ProfileEditModalComponent,CommonModule],
  templateUrl: './admin-profile.component.html',
  styleUrl: './admin-profile.component.css'
})
export class AdminProfileComponent {
   profile$!: Observable<AdminProfile | null>
  currentProfile: AdminProfile | null = null
  isEditModalOpen = false

  constructor(public adminProfileService: AdminProfileService) {}

  ngOnInit() {
    this.profile$ = this.adminProfileService.profile$
    this.profile$.subscribe((profile) => {
      this.currentProfile = profile
    })
  }

  openEditModal() {
    this.isEditModalOpen = true
  }

  closeEditModal() {
    this.isEditModalOpen = false
  }

  onProfileUpdated(updatedProfile: AdminProfile) {
    this.currentProfile = updatedProfile
    // In a real app, you would update the service state here
  }

  updateTheme(event: Event) {
    const target = event.target as HTMLSelectElement
    const theme = target.value as "light" | "dark" | "system"

    this.adminProfileService
      .updatePreferences({
        theme,
      })
      .subscribe()
  }

  updateNotificationPreference(type: "email" | "push" | "sms", event: Event) {
    const target = event.target as HTMLInputElement
    const currentNotifications = this.currentProfile?.preferences.notifications || {
      email: false,
      push: false,
      sms: false,
    }

    this.adminProfileService
      .updatePreferences({
        notifications: {
          ...currentNotifications,
          [type]: target.checked,
        },
      })
      .subscribe()
  }
}
