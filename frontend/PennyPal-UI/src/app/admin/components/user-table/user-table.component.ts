import { Component,Input } from '@angular/core';
import { UserRowComponent } from "../user-row/user-row.component";
import { CommonModule } from '@angular/common';
import { BehaviorSubject } from 'rxjs';
import { ActionButtonComponent} from "../action-button/action-button.component";
import { User } from '../../../models/User';
import { UserManagementComponent } from '../../pages/user-management/user-management.component';
import { UserManagementService } from '../../services/UserManagamentService';


@Component({
  selector: 'app-user-table',
  imports: [UserRowComponent, CommonModule],
  templateUrl: './user-table.component.html',
  styleUrl: './user-table.component.css'
})
export class UserTableComponent {
   @Input() users : User[] | null =null;
   constructor(private userManagementService : UserManagementService){}
   updateUser(updatedUser : User){
    this.users = this.users?.map((user) => user.email === updatedUser?.email ? updatedUser : user)|| null;
   }
}
