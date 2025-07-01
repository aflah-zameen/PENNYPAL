import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { User } from '../../../models/User';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../public/auth/services/auth.service';
import { Observable, of } from 'rxjs';
import { UserService } from '../../services/user.service';
import { ProfileUploadComponent } from "../../../shared/components/profile-upload/profile-upload.component";
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-profile',
  imports: [CommonModule, FormsModule, ProfileUploadComponent],
  templateUrl: './user-profile.component.html',
  styleUrl: './user-profile.component.css'
})
export class UserProfileComponent implements OnInit {
  user$ :Observable<User | null>;
  user : User | null = null;
  tempUser: Partial<User> = {};
  isEditing: boolean = false;
  changeProfileFlag : boolean = false;
  newProfile : File | null = null;
  editEmailFlag : boolean =false;
  newEmail : string | null =null;
  checkEmail : boolean = false;
  EmailCheckStatus : string ="";
  otpSent: boolean = false;
  otp: string = '';
  emailVerified: boolean = false;


  constructor(private authService : AuthService,
    private userService : UserService,
    private spinner : NgxSpinnerService,
    private toastr : ToastrService,
    private router : Router
  ){
    this.user$ = this.authService.user$;
  }

  ngOnInit(): void {
    this.user$.subscribe({
      next : (userData)=>{
        if(userData){
          this.user  = userData;
          this.tempUser = {...userData};
        }
      }
    });
  }

  startEditing(): void {
    this.isEditing = true;
    this.tempUser = { ...this.user };
  }

  saveChanges(): void {
  if ((this.editEmailFlag && !this.emailVerified) || !this.tempUser) {
    this.toastr.warning("Please verify your new email before saving.");
    return;
  }
  this.spinner.show();
  this.userService.updateUser(this.tempUser, this.newProfile).subscribe({
    next: (user) => {
      this.authService.updateUser(user);
      this.user = user;
      this.isEditing = false;
      this.changeProfileFlag = false;
      this.editEmailFlag = false;
      this.spinner.hide();
    },
    error: () => {
      this.spinner.hide();
      this.toastr.error("Failed to update profile.");
    }
  });
}


  cancelEditing(): void {
    this.tempUser = { ...this.user };
    this.isEditing = false;
  }

  onProfilePictureChange(file : File): void {
    this.newProfile = file;
  }

  changeProfile(){
    this.changeProfileFlag = true;    
  }

  checkEmailAvailability(){
    if(this.newEmail != null && this.newEmail.trim() != "" && this.newEmail != this.user?.email){
    this.spinner.show();
    this.authService.checkEmailAvailability(this.newEmail).subscribe({
      next:(flag)=>{
          if(flag){
            this.EmailCheckStatus = "Email is available.";
            this.spinner.hide();
            this.toastr.success("Email is available.");
            this.checkEmail = true;
            this.tempUser = {...this.user,email : this.newEmail!};
          }
      },
      error : (err)=>{
        this.EmailCheckStatus = err.error.message;
        this.spinner.hide();
      }
    })
    }
    
  }

  resetNewEmail(){
    this.checkEmail = false;
    this.EmailCheckStatus = '';
  }

  sendOtpToEmail() {
  if (!this.newEmail) return;
  this.spinner.show();
  this.authService.sentOtp(this.newEmail).subscribe({
    next: () => {
      this.otpSent = true;
      this.spinner.hide();
      this.toastr.info("OTP sent to your new email.");
    },
    error: (err) => {
      this.spinner.hide();
      this.toastr.error("Failed to send OTP.");
    }
  });
}

verifyOtp() {
  if (!this.newEmail || !this.otp) return;
  this.spinner.show();
  this.authService.verifyOtp(this.newEmail, this.otp,"EmailProfileUpdate").subscribe({
    next: (verified) => {
      if (verified) {
        this.emailVerified = true;
        this.toastr.success("Email verified!");
      } else {
        this.toastr.error("Incorrect OTP.");
      }
      this.spinner.hide();
    },
    error: () => {
      this.spinner.hide();
      this.toastr.error("Verification failed.");
    }
  });
}

}