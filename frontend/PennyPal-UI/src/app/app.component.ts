import { Component, inject } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NgxSpinnerModule } from 'ngx-spinner';
import { MatDialogModule} from '@angular/material/dialog'; 
import {MatButtonModule} from '@angular/material/button'

@Component({
  selector: 'app-root',
  imports: [RouterOutlet,NgxSpinnerModule,MatDialogModule,MatButtonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
}
