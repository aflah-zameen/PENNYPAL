import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { LoginSectionComponent } from "../components/login-section/login-section.component";

@Component({
  selector: 'app-auth-layout',
  imports: [RouterOutlet, LoginSectionComponent],
  templateUrl: './auth-layout.component.html',
  styleUrl: './auth-layout.component.css'
})
export class AuthLayoutComponent {

}
