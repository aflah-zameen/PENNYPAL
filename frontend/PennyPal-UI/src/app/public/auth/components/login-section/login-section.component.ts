import { Component, OnInit } from '@angular/core';
import { InputFieldComponent } from '../../../../shared/components/input-field/input-field.component';
import { ActionButtonComponent } from '../../../../shared/components/action-button/action-button.component';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router, RouterModule } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { LoginRequest } from '../../models/auth.model';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-login-section',
  imports: [InputFieldComponent,ActionButtonComponent,ReactiveFormsModule,RouterModule],
  templateUrl: './login-section.component.html',
  styleUrl: './login-section.component.css'
})
export class LoginSectionComponent {
   loginForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private authService : AuthService,
    private router : Router,
    private toastr : ToastrService,
    private spinner : NgxSpinnerService
  ) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required]],
      password: ['', [Validators.required]]
    });
  }

  // ngOnInit(): void {
  //     this.authService.isAuthenticated().subscribe({
  //       next : (isAuthenticated) =>{
  //         if(isAuthenticated){
  //           this.router.navigate(['/home']);
  //         }
  //       }
  //     }
  //     )
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
    this.spinner.show()
    this.authService.login(loginData).subscribe({
      next : (res) =>{
        this.spinner.hide();
        this.toastr.success("Logged in successfully.","SUCCESS",{timeOut:1000});
        this.router.navigate(['/user']);
      },
      error : (err)=>{
        this.spinner.hide();
        this.toastr.error(err.message);
      }
    })
  }




}
