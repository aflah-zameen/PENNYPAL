import { Component } from '@angular/core';
import { InputFieldComponent } from "../../../../shared/components/input-field/input-field.component";
import { ActionButtonComponent } from "../../../../shared/components/action-button/action-button.component";
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-forget-password',
  imports: [InputFieldComponent, ActionButtonComponent,ReactiveFormsModule],
  templateUrl: './forget-password.component.html',
  styleUrl: './forget-password.component.css'
})
export class ForgetPasswordComponent {
  email : string ="";
  forgetPasswordForm : FormGroup;
  constructor(
    private fb: FormBuilder, private authService : AuthService ,
  private toastr : ToastrService,
private router : Router ,
private spinner : NgxSpinnerService) {
    this.forgetPasswordForm = this.fb.group({
      email: ['', [Validators.email,Validators.required]],
    });
  }
  
  getFormControl(name  : string){
    return this.forgetPasswordForm.get(name) as FormControl
  }

  sendOtp(){
    if (this.forgetPasswordForm.invalid) {
      this.toastr.error('Please enter valid email', 'Form Error');
      return;
    }
    this.email = this.forgetPasswordForm.value.email;
    this.spinner.show();
    this.authService.sentOtp(this.email).subscribe({
      next: (expires) => {
        this.spinner.hide();
        this.authService.otpTimerSubject.next(expires);
        this.toastr.success('OTP sent successfully', 'Success');
        this.router.navigate(['/auth/otp-section'],{queryParams : {context : 'forget-password',email : this.email}});
      },
      error: (err) => {
        this.spinner.hide();
        this.toastr.error(err.message || 'OTP not sent', 'Error');
      },
    });
  }


}
