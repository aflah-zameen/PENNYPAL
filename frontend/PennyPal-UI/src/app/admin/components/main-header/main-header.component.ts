import { Component } from '@angular/core';
import { LogoComponent } from "./logo/logo.component";
import { UserProfileComponent } from "./user-profile/user-profile.component";
import { LogoutButtonComponent } from "./logout-button/logout-button.component";
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { AuthService } from '../../../public/auth/services/auth.service';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmDialogComponent } from '../../../shared/components/confirm-dialog/confirm-dialog.component';

@Component({
  selector: 'app-main-header',
  imports: [LogoComponent, UserProfileComponent, LogoutButtonComponent],
  templateUrl: './main-header.component.html',
  styleUrl: './main-header.component.css'
})
export class MainHeaderComponent {

  constructor(private toastr : ToastrService, private router : Router,
    private spinner : NgxSpinnerService,
    private authService : AuthService,
    private dialog  : MatDialog
  ){}

  handleLogout(): void {
    const ref = this.dialog.open(ConfirmDialogComponent,{
      width: '400px',
        data: {
          title: 'Confirm Logout',
          message: `Are you sure you want to Logout ?`,
          confirmText: `Logout`,
          cancelText: 'Cancel'
        }
    });

    ref.afterClosed().subscribe({
      next:(flag)=>{
        if(flag){
          this.spinner.show();
          this.authService.logout().subscribe({
          next : ()=>{
                this.spinner.hide();
                this.toastr.success("Logout successfully","SUCCESS",{timeOut:2000});
                this.router.navigate(['/admin-login'])
              }
            });
        }
      }
    });
  }
}
