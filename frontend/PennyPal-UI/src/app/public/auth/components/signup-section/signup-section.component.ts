import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { InputFieldComponent } from '../../../../shared/components/input-field/input-field.component';
import { ActionButtonComponent } from '../../../../shared/components/action-button/action-button.component';
import { PhoneFieldComponent } from "../../../../shared/components/phone-field/phone-field.component";
import { SignupRequest, SignupResponse } from '../../models/auth.model';
import { AuthService } from '../../services/auth.service';
import { Router, RouterModule } from '@angular/router';
import {ToastrService} from 'ngx-toastr'
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-signup-section',
  imports: [ReactiveFormsModule, FormsModule, ActionButtonComponent,
     InputFieldComponent, PhoneFieldComponent, RouterModule],
  templateUrl: './signup-section.component.html',
  styleUrl: './signup-section.component.css'
})
export class SignupSectionComponent {
  accountForm: FormGroup;
  errorMessage : string | null =null;
  successMessage : string | null =null;
  constructor(private fb: FormBuilder,private authService : AuthService,
    private toastr : ToastrService,
    private router :Router,
    private spinner  : NgxSpinnerService) {

    this.accountForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(2)]],
      phone: ['', [Validators.required, Validators.pattern(/^[0-9]{10}$/)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [
        Validators.required,
        Validators.minLength(8),
        Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W_]).{8,}$/)
      ]],
      confirmPassword: ['', Validators.required]
    }, {
      validators: this.passwordMatchValidator
    });
  }

  getFormControl(name: string): FormControl {
  return this.accountForm.get(name) as FormControl;
  }


  passwordMatchValidator(form: FormGroup) {
  const passwordControl = form.get('password');
  const confirmPasswordControl = form.get('confirmPassword');

  if (!passwordControl || !confirmPasswordControl) return null;

  const password = passwordControl.value;
  const confirmPassword = confirmPasswordControl.value;

  if (password !== confirmPassword) {
    confirmPasswordControl.setErrors({
      ...confirmPasswordControl.errors,
      mismatch: true
    });
  } else {
    if (confirmPasswordControl.hasError('mismatch')) {
      const errors = { ...confirmPasswordControl.errors };
      delete errors['mismatch'];
      confirmPasswordControl.setErrors(Object.keys(errors).length ? errors : null);
    }
  }

  return null;
}

  onSubmit() {
    if (this.accountForm.invalid) {
      this.toastr.error('Please fill out the form correctly', 'Form Error')
      return;
    }
    const signupData : SignupRequest = this.accountForm.value;
    this.authService.signup(signupData).subscribe({
      next : (response : any) => {
        this.toastr.success(response.message || 'Registration completed', 'Success',{timeOut:1000});
        this.spinner.show();
        this.authService.sentOtp(signupData.email).subscribe({
          next : (expiresAt) =>{
            this.spinner.hide();
            this.authService.otpTimerSubject.next(expiresAt);
            this.toastr.success(response.message || "OTP send successfully. Please verify the OTP.");
            this.router.navigate(['/otp-section'],{ queryParams: { email: signupData.email,
              context : 'register'} })
          },
          error : (err)=>{
            this.toastr.error(err.message || "Something causing error for sending OTP");
          }
        })
      },
      error: (err) =>{
        this.toastr.error(err.message || 'Signup failed. Please try again.', 'Error');
      }
    });
  }
}
