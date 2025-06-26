import { CommonModule } from '@angular/common';
import { Component,EventEmitter,Input, Output } from '@angular/core';
import { ActionButtonComponent } from "../action-button/action-button.component";
import { UserManagementService } from '../../services/UserManagamentService';
import { Outdent } from 'lucide-angular';
import { ToastrService } from 'ngx-toastr';
import { User } from '../../../models/User';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmDialogComponent } from '../../../shared/components/confirm-dialog/confirm-dialog.component';

@Component({
  selector: 'app-user-row',
  imports: [CommonModule, ActionButtonComponent],
  templateUrl: './user-row.component.html',
  styleUrl: './user-row.component.css'
})
export class UserRowComponent {
  @Input() userData!: any;
  @Output() updateUser = new EventEmitter<User>();

  constructor(private userManagementService : UserManagementService,
    private toastr : ToastrService,
    private dialog : MatDialog
  ){}

  handleBlockUser = (): void => {
      const dialogRef = this.dialog.open(ConfirmDialogComponent,{
         width: '400px',
        data: {
          title: 'Confirm Block',
          message: `Are you sure you want to ${this.userData.active ? 'BLOCK':'UNBLOCK'} ${this.userData.name}?`,
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
}
