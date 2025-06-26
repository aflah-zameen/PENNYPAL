import { Component, OnInit } from '@angular/core';
import { InputFieldComponent } from "../../../../shared/components/input-field/input-field.component";
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActionButtonComponent } from "../../../../shared/components/action-button/action-button.component";
import { AuthService } from '../../services/auth.service';
import { ToastrService } from 'ngx-toastr';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-set-new-password',
  imports: [InputFieldComponent, ReactiveFormsModule, ActionButtonComponent],
  templateUrl: './set-new-password.component.html',
  styleUrl: './set-new-password.component.css'
})
export class SetNewPasswordComponent implements OnInit {
    setPasswordForm : FormGroup
    email : string ="";
    constructor(builder : FormBuilder,private authService : AuthService,private toastr : ToastrService,
      private router : Router,private route :ActivatedRoute
    ){
        this.setPasswordForm = builder.group( {
      password: ['', [
        Validators.required,
        Validators.minLength(8),
        Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W_]).{8,}$/)
      ]],
      confirmPassword: ['', Validators.required]
    },{
      validators: this.passwordMatchValidator
    }
  )
    }

  ngOnInit() {
    this.route.queryParams.subscribe((params) => {
      this.email = params['email'] || null;
    });
  }

  getFormControl(name: string): FormControl {
  return this.setPasswordForm.get(name) as FormControl;
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

savePassword(){
  if(this.setPasswordForm.valid){
    this.authService.updatePassword(this.setPasswordForm.value.password,this.email).subscribe({
      next : (message)=>{
          this.toastr.success("Password updated successfully. Login with new password"); 
          this.router.navigate(["/login"]);
      },
      error : (err) => {
        this.toastr.error(err);
      }
    }
    )
  }else{
    this.toastr.error("Enter a valid password");
  }
}


}
