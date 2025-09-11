import { Component, EventEmitter, Input, Output, SimpleChanges } from '@angular/core';
import { AdminProfile, ProfileUpdateRequest } from '../../../models/admin.model';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AdminProfileService } from '../../../services/admin-profile.service';
import { CommonModule } from '@angular/common';
import { User } from '../../../../models/User';
import { ModalOverlayComponent } from "../../../../user/modals/modal-overlay/modal-overlay.component";

@Component({
  selector: 'app-profile-edit-modal',
  imports: [CommonModule, ReactiveFormsModule, ModalOverlayComponent],
  templateUrl: './profile-edit-modal.component.html',
  styleUrl: './profile-edit-modal.component.css'
})
export class ProfileEditModalComponent {
  @Input() isOpen = false
  @Input() profile: User | null = null
  @Output() close = new EventEmitter<void>()
  @Output() profileUpdated = new EventEmitter<User>()

  profileForm: FormGroup
  isLoading = false
  previewAvatar: string | null = null
  selectedAvatarFile: File | null = null
  timezones: string[] = []
  languages: { code: string; name: string }[] = []

  constructor(
    private fb: FormBuilder,
    private adminProfileService: AdminProfileService,
  ) {
    this.profileForm = this.fb.group({
      name: ["", Validators.required],
      phoneNumber: [""]
    })
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['profile'] && this.profile) {
      this.profileForm.patchValue({
        name: this.profile.userName,
        phoneNumber: this.profile.phone
      });      
      this.previewAvatar = this.profile.profileURL || null;
    }
  }


  onAvatarChange(event: Event) {
    const input = event.target as HTMLInputElement
    if (input.files && input.files[0]) {
      const file = input.files[0]

      // Validate file size (5MB max)
      if (file.size > 5 * 1024 * 1024) {
        alert("File size must be less than 5MB")
        return
      }

      // Validate file type
      if (!file.type.startsWith("image/")) {
        alert("Please select an image file")
        return
      }

      this.selectedAvatarFile = file

      // Create preview
      const reader = new FileReader()
      reader.onload = (e) => {
        this.previewAvatar = e.target?.result as string
      }
      reader.readAsDataURL(file)
    }
  }

  saveProfile(): void {
  if (this.profileForm.valid) {

    const updateData: ProfileUpdateRequest = {
      name: this.profileForm.value.name,
      phoneNumber: this.profileForm.value.phoneNumber,
      avatar: this.selectedAvatarFile || undefined,
    };
    this.adminProfileService.updateProfile(updateData).subscribe({
      next: (updatedProfile) => {
        this.isLoading = false;
        this.profileUpdated.emit(updatedProfile);
        this.closeModal();
      },
      error: (error) => {
        this.isLoading = false;
        console.error("Error updating profile:", error);
        alert("Failed to update profile. Please try again.");
      },
    });
    
  }
}


  closeModal() {
    this.close.emit()
    this.previewAvatar = null
    this.selectedAvatarFile = null
    this.profileForm.reset()
  }
}
