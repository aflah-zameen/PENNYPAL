import { CommonModule } from '@angular/common';
import { Component, HostListener, Input } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from '../../../../public/auth/services/auth.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmDialogComponent } from '../../../../shared/components/confirm-dialog/confirm-dialog.component';
import { Title } from '@angular/platform-browser';
import { User } from '../../../../models/User';
import { Observable } from 'rxjs';
import { ProfileDropdownComponent } from "../../../../shared/components/profile-dropdown/profile-dropdown.component";
import { ProfileDropdown } from '../../../models/profile-dropdown';

@Component({
  selector: 'app-user-profile-icon',
  imports: [CommonModule, ProfileDropdownComponent],
  templateUrl: './user-profile-icon.component.html',
  styleUrl: './user-profile-icon.component.css'
})
export class UserProfileIconComponent {
  @Input() profileURL : string|null = null;
  isDropdownOpen :boolean = false;
  dropdownItems = [
  { label: 'Profile', action: () => this.router.navigate(['/user/user-profile']) },
    { label: 'Settings', action: () => this.router.navigate(['/user/setting']) },
  { label: 'Logout', action: () => this.logoutUser() },
  ];

  constructor(private toastr : ToastrService,private router : Router,
    private authService : AuthService,
    private spinner : NgxSpinnerService,
    private dialog : MatDialog,
  ){
  }
  toggleDropdown() {
    this.isDropdownOpen = !this.isDropdownOpen;
  }

    @HostListener('document:click', ['$event'])
  onClickOutside(event: MouseEvent) {
    // If the click target is outside the dropdown or the button, close the dropdown
    const dropdown = document.querySelector('.dropDown');
    const button = document.querySelector('button');

    if (dropdown && !dropdown.contains(event.target as Node) && !button?.contains(event.target as Node)) {
      this.isDropdownOpen = false;
    }
  }

  logoutUser(){
    const refDialog = this.dialog.open(ConfirmDialogComponent,{
      width : '400px',
      data : {
        title: 'Confirm Logout',
        message: `Are you sure you want to Logout ?`,
        confirmText: `Logout`,
        cancelText: 'Cancel'
      }
  });
  refDialog.afterClosed().subscribe({
    next : (flag) => {
      if(flag){
          this.authService.logout().subscribe({
            next : () =>{
              this.toastr.success("Logout successfully","LOG_OUT",{timeOut:2000});
              this.authService.clearUser();
              this.router.navigate(['/login']);
            },
            error : (err)=>{
              this.toastr.error(err.message);
            }
          });
      }
    }
  });
  }


  closeDropdown(): void {
    this.isDropdownOpen = false
  }

  onDropdownItemClick(item: ProfileDropdown): void {
  if (item?.action && typeof item.action === 'function') {
    item.action(); // Safely call the action
  }
  this.closeDropdown();
}


}
