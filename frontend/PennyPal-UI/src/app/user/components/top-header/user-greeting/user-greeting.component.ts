import { Component,Input } from '@angular/core';

@Component({
  selector: 'app-user-greeting',
  imports: [],
  templateUrl: './user-greeting.component.html',
  styleUrl: './user-greeting.component.css'
})
export class UserGreetingComponent {
  @Input() name: string = '';
}
