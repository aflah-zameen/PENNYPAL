import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { AdminProfile } from '../../models/admin.model';
import { AdminProfileService } from '../../services/admin-profile.service';
import { ProfileEditModalComponent } from "../../components/profile/profile-edit-modal/profile-edit-modal.component";
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../public/auth/services/auth.service';
import { User } from '../../../models/User';
import { Roles } from '../../../models/Roles';

@Component({
  selector: 'app-admin-profile',
  imports: [ ProfileEditModalComponent,CommonModule],
  templateUrl: './admin-profile.component.html',
  styleUrl: './admin-profile.component.css'
})
export class AdminProfileComponent {
  currentProfile$!: Observable<User| null>
  isEditModalOpen = false

  constructor(public adminProfileService: AdminProfileService,public authService :AuthService) {
    this.currentProfile$ = this.authService.user$
  }

  ngOnInit() {
    // this.profile$.subscribe((profile) => {
    //   this.currentProfile = profile
    // })
  }

  openEditModal() {
    this.isEditModalOpen = true
  }

  closeEditModal() {
    this.isEditModalOpen = false
  }

  onProfileUpdated(updatedProfile: User) {
    this.authService.updateUser(updatedProfile);
  }

  getRoles(roles: Set<Roles>): string {
    if (!roles || roles.size === 0) {
      return 'No roles assigned';
    }
  
    return Array.from(roles).join(', ');
  }

}
