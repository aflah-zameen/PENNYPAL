import { CommonModule, DatePipe } from '@angular/common';
import { Component,EventEmitter,Input, Output } from '@angular/core';
import { ActionButtonComponent } from "../action-button/action-button.component";
import { UserManagementService } from '../../services/UserManagamentService';
import { Outdent } from 'lucide-angular';
import { ToastrService } from 'ngx-toastr';
import { User } from '../../../models/User';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmDialogComponent } from '../../../shared/components/confirm-dialog/confirm-dialog.component';
import { Roles } from '../../../models/Roles';

@Component({
  selector: 'app-user-row',
  imports: [CommonModule,DatePipe],
  templateUrl: './user-row.component.html',
  styleUrl: './user-row.component.css'
})
export class UserRowComponent {
  @Input() userData!: User;
  @Output() updateUser = new EventEmitter<User>();

  showDropdown = false;
  constructor(private userManagementService : UserManagementService,
    private toastr : ToastrService,
    private dialog : MatDialog
  ){}

  handleBlockUser = (): void => {
      const dialogRef = this.dialog.open(ConfirmDialogComponent,{
         width: '400px',
        data: {
          title: 'Confirm Block',
          message: `Are you sure you want to ${this.userData.active ? 'BLOCK':'UNBLOCK'} ${this.userData.userName}?`,
          confirmText: `${this.userData.active ? 'BLOCK':'UNBLOCK'}`,
          cancelText: 'Cancel'
        }
      });

      dialogRef.afterClosed().subscribe({
        next : (flag)=>{
          if(flag){
            this.userManagementService.toggleUserActive(this.userData.email , !this.userData.active).subscribe({
            next : (updatedUser)=>{
              this.updateUser.emit(updatedUser);
              this.toastr.success("User status updated successfully.");
            },
            error : (err)=>{
              this.toastr.error(err.message || "An unknow error occured. Try again later");
            }
          })
          }
        }
      })

  };

  toggleDropdown() {
    this.showDropdown = !this.showDropdown;
  }

  getRolesLabel(): string {
  const rolesArray = [...this.userData.roles];
  return rolesArray.join(', ');
}


  getStatusBadgeClass(active: boolean): string {
    return active ? 
      'bg-green-100 text-green-800 border-green-200' : 
      'bg-red-100 text-red-800 border-red-200';
  }
  onImgError(event: Event): void {
    const target = event.target as HTMLImageElement;
    target.src = 'images/default/user-placeholder.jpg';
  }
}
