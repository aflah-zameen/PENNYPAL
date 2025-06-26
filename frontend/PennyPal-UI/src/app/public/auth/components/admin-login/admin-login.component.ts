import { Component, OnInit } from '@angular/core';
import { LoginRequest } from '../../models/auth.model';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { InputFieldComponent } from "../../../../shared/components/input-field/input-field.component";
import { ActionButtonComponent } from "../../../../shared/components/action-button/action-button.component";

@Component({
  selector: 'app-admin-login',
  imports: [ReactiveFormsModule, InputFieldComponent, ActionButtonComponent],
  templateUrl: './admin-login.component.html',
  styleUrl: './admin-login.component.css'
})
export class AdminLoginComponent {

   loginForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private authService : AuthService,
    private router : Router,
    private toastr : ToastrService
  ) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required]],
      password: ['', [Validators.required]]
    });
  }

  // ngOnInit(): void {
  //     if(this.authService.)
  // }

  getFormControl(name: string) {
    return this.loginForm.get(name) as FormControl;
  }
   onSignIn() {
    if (this.loginForm.invalid) {
      // Mark all fields as touched to trigger validation messages
      this.loginForm.markAllAsTouched();
      // Show specific validation errors
      const errors: string[] = [];
      const emailControl = this.loginForm.get('email');
      const passwordControl = this.loginForm.get('password');

      if (emailControl?.errors?.['required']) {
        errors.push('Email is required');
      } else if (emailControl?.errors?.['email']) {
        errors.push('Invalid email format');
      }

      if (passwordControl?.errors?.['required']) {
        errors.push('Password is required');
      } else if (passwordControl?.errors?.['minlength']) {
        errors.push('Password must be at least 6 characters');
      }

      this.toastr.error(errors.join(' and '), 'Form Error');
      return;
    }

    const loginData : LoginRequest = this.loginForm.value;
    this.authService.login(loginData).subscribe({
      next: (response: any) => {
        this.toastr.success(response.message || 'Login successful', 'Success');
        this.router.navigate(['/admin/user-management']);
      },
      error: (err:any) => {
        this.toastr.error(err.message || 'Login failed. Please try again.', 'Error');
      },
    });
  }


}
