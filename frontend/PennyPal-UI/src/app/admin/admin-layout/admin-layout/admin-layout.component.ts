import { Component } from '@angular/core';
import { SideNavbarComponent } from "../../../admin/components/side-navbar/side-navbar.component";
import { MainHeaderComponent } from "../../components/main-header/main-header.component";
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-admin-layout',
  imports: [SideNavbarComponent, MainHeaderComponent,RouterOutlet],
  templateUrl: './admin-layout.component.html',
  styleUrl: './admin-layout.component.css'
})
export class AdminLayoutComponent {

}
