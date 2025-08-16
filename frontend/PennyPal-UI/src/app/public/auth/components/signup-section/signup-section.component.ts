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
import { ProfileUploadComponent } from "../../../../shared/components/profile-upload/profile-upload.component";

@Component({
  selector: 'app-signup-section',
  imports: [ReactiveFormsModule, FormsModule, ActionButtonComponent,
     InputFieldComponent, PhoneFieldComponent, RouterModule,ProfileUploadComponent],
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
      confirmPassword: ['', Validators.required],
      profilePicture: [null, Validators.required] 
    }, {
      validators: this.passwordMatchValidator
    });
  }

  getFormControl(name: string): FormControl {
  return this.accountForm.get(name) as FormControl;
}   

onProfileSelected(file: File) {
  this.accountForm.patchValue({ profilePicture: file });
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
    this.toastr.error('Please fill out the form.', 'Form Error');
    return;
  }

  const signupRequest = this.accountForm.value;

  this.spinner.show();
  this.authService.signup(signupRequest).subscribe({
    next: (response:{
        id: string;
        email: string;
        expiry: string;
    }) => {
      this.spinner.hide();
      this.toastr.success('Registration completed', 'Success');
      if(response?.expiry != null)
        this.authService.otpTimerSubject.next(new Date(response.expiry));
      this.router.navigate(['/otp-section'], {
            queryParams: { email: signupRequest.email, context: 'register' }
          });
    },
    error: (err) => {
      this.spinner.hide();
      this.toastr.error(err.message || 'Signup failed. Please try again.');
    }
  });
}


}
