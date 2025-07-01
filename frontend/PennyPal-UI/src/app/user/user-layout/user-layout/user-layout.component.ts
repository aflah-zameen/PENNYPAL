import { Component, OnInit } from '@angular/core';
import { SideNavbarComponent } from "../../components/side-navbar/side-navbar.component";
import { TopHeaderComponent } from "../../components/top-header/top-header.component";
import { RouterOutlet } from '@angular/router';
import { SummaryCardComponent } from "../../components/summary-card/summary-card.component";
import { HomeComponent } from "../../pages/home/home.component";

@Component({
  selector: 'app-user-layout',
  imports: [SideNavbarComponent, TopHeaderComponent, RouterOutlet],
  templateUrl: './user-layout.component.html',
  styleUrl: './user-layout.component.css'
})
export class UserLayoutComponent{
}
