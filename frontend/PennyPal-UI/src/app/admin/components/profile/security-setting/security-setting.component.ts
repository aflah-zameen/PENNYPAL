import { Component, Input } from '@angular/core';
import { AdminProfile } from '../../../models/admin.model';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AdminProfileService } from '../../../services/admin-profile.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-security-setting',
  imports: [CommonModule,ReactiveFormsModule],
  templateUrl: './security-setting.component.html',
  styleUrl: './security-setting.component.css'
})
export class SecuritySettingComponent {
  @Input() profile: AdminProfile | null = null

  passwordForm: FormGroup
  isChangingPassword = false
  show2FAModal = false
  qrCode = ""
  backupCodes: string[] = []

  constructor(
    private fb: FormBuilder,
    private adminProfileService: AdminProfileService,
  ) {
    this.passwordForm = this.fb.group(
      {
        currentPassword: ["", Validators.required],
        newPassword: ["", [Validators.required, Validators.minLength(8)]],
        confirmPassword: ["", Validators.required],
      },
      { validators: this.passwordMatchValidator },
    )
  }

  ngOnInit() {}

  passwordMatchValidator(form: FormGroup) {
    const newPassword = form.get("newPassword")
    const confirmPassword = form.get("confirmPassword")

    if (newPassword && confirmPassword && newPassword.value !== confirmPassword.value) {
      return { passwordMismatch: true }
    }
    return null
  }

  changePassword() {
    if (this.passwordForm.valid) {
      this.isChangingPassword = true
      const { currentPassword, newPassword } = this.passwordForm.value

      this.adminProfileService.changePassword(currentPassword, newPassword).subscribe({
        next: (success) => {
          this.isChangingPassword = false
          if (success) {
            alert("Password changed successfully")
            this.passwordForm.reset()
          }
        },
        error: () => {
          this.isChangingPassword = false
          alert("Failed to change password")
        },
      })
    }
  }

  toggle2FA() {
    if (this.profile?.preferences.security.twoFactorEnabled) {
      // Disable 2FA
      this.adminProfileService
        .updatePreferences({
          security: {
            ...this.profile.preferences.security,
            twoFactorEnabled: false,
          },
        })
        .subscribe()
    } else {
      // Enable 2FA
      this.adminProfileService.enableTwoFactor().subscribe({
        next: (data) => {
          this.qrCode = data.qrCode
          this.backupCodes = data.backupCodes
          this.show2FAModal = true
        },
      })
    }
  }

  confirm2FASetup() {
    this.adminProfileService
      .updatePreferences({
        security: {
          ...this.profile!.preferences.security,
          twoFactorEnabled: true,
        },
      })
      .subscribe(() => {
        this.show2FAModal = false
      })
  }

  cancel2FASetup() {
    this.show2FAModal = false
  }

  updateSessionTimeout(event: Event) {
    const target = event.target as HTMLSelectElement
    const timeout = Number.parseInt(target.value)

    this.adminProfileService
      .updatePreferences({
        security: {
          ...this.profile!.preferences.security,
          sessionTimeout: timeout,
        },
      })
      .subscribe()
  }
}
