import { Component, OnInit, ViewChild } from '@angular/core';
import { OtpInputFieldComponent } from "./otp-input-field/otp-input-field.component";
import { TimerComponent } from "../../../../shared/components/timer/timer.component";
import { ActionButtonComponent } from "../../../../shared/components/action-button/action-button.component";
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from '../../services/auth.service';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { CommonModule } from '@angular/common';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-otp-section',
  imports: [OtpInputFieldComponent, TimerComponent, ActionButtonComponent,CommonModule],
  templateUrl: './otp-section.component.html',
  styleUrl: './otp-section.component.css'
})
export class OtpSectionComponent implements OnInit{
  email: string | null = null;
  context : string| null =null;
  otpValue: string = '';
  expiresAt$ : Observable<Date | null> ;
  isOtpComplete: BehaviorSubject<boolean>;
  resendDisableFlag : BehaviorSubject<boolean>;

  constructor(private router : Router,private toastr : ToastrService,
    private authService : AuthService,
    private route : ActivatedRoute,
    private spinner : NgxSpinnerService
  ){
    this.isOtpComplete = new BehaviorSubject<boolean>(false);
    this.resendDisableFlag = new BehaviorSubject<boolean>(true);
    this.expiresAt$ = this.authService.otpTimerSubject.asObservable();
  }

   ngOnInit() {
    this.route.queryParams.subscribe((params) => {
      this.email = params['email'] || null;
      this.context = params['context'] || 'register';
      if (!this.email) {
        this.toastr.error('Email not provided', 'Error');
        this.router.navigate(['/signup']);
      }
    });
  }

  @ViewChild(OtpInputFieldComponent)
  otpInputComponent! : OtpInputFieldComponent

  onOtpChange(value: string) {
    this.otpValue = value;
    this.isOtpComplete.next(value.length === 6);
  }


  onTimerExpire() {
    this.resendDisableFlag.next(false);
  }
  resendCode(){
    if(this.email != null){
      this.spinner.show();
      this.authService.resendOtp(this.email).subscribe({
        next : (expires)=>{
            this.spinner.hide()
            this.toastr.success("OTP successfully resent.","CHECK")
            this.authService.otpTimerSubject.next(expires);
            this.otpValue='';
            this.isOtpComplete.next(false);
            this.resendDisableFlag.next(true);
            this.otpInputComponent.clearOtp();

        },
        error : (err)=>{
          this.toastr.error(err.message);
        }
      })
    }else{
      this.spinner.hide();
      this.toastr.info("Email is not given","EMAIL NOT FOUND",{timeOut:2000});
    } 
  }

  goBack() {
    console.log('Going back');
  }

  submitOtp() {
    
    if (!this.isOtpComplete || !this.email  ) {
      this.toastr.error('Please enter a valid OTP', 'Form Error');
      return;
    }


    this.authService.verifyOtp(this.email,this.otpValue,this.context!
    ).subscribe({
      next: (response: any) => {
        this.toastr.success(response.message || 'OTP verified successfully', 'Success');
        if(this.context === 'register'){
          this.router.navigate(['/login']);
        }
        else if(this.context === 'forget-password'){
          this.router.navigate(['/set-new-password'],{queryParams :{email : this.email}})
        }
      },
      error: (err) => {
        this.toastr.error(err.message || 'OTP verification failed', 'Error');
      },
    });
  }

}
