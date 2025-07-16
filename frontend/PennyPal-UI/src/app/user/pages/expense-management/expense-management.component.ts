import { Component, OnInit } from '@angular/core';
import { ExpenseCategory } from '../../models/expense-category.model';
import { NewExpense } from '../../models/expense-new-category';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NgxSpinnerService } from 'ngx-spinner';
import { UserExpenseService } from '../../services/user-expense.service';
import { ExpenseModel } from '../../models/expense.model';
import { UserCategoryResponse } from '../../models/user-category.model';
import { UserService } from '../../services/user.service';
import { ToastrService } from 'ngx-toastr';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmDialogComponent } from '../../../shared/components/confirm-dialog/confirm-dialog.component';

@Component({
  selector: 'app-expense-management',
  imports: [CommonModule,FormsModule],
  templateUrl: './expense-management.component.html',
  styleUrl: './expense-management.component.css'
})
export class ExpenseManagementComponent implements OnInit {
  searchTerm: string = '';
  
  categories: UserCategoryResponse[] = []
  expenses : ExpenseModel[] = []

  newExpense: NewExpense = {
    name: '',
    categoryId: null  ,
    amount: null,
    type: 'monthly',
    startDate: '',
    endDate: ''
  };

  constructor(private spinner:NgxSpinnerService,
    private userExpenseService: UserExpenseService,
    private userService : UserService,
    private toastr : ToastrService,
    private dialog : MatDialog
  ){}

  ngOnInit() {
    this.userExpenseService.addExpense$.subscribe(() => {
      this.loadCategories();
      this.loadExpenses();
    });
      this.loadCategories();
      this.loadExpenses();
  }

  get filteredCategories(): ExpenseModel[] {
    if (!this.searchTerm) {
      return this.expenses;
    }
    return this.expenses.filter(category =>
      category.name.toLowerCase().includes(this.searchTerm.toLowerCase())
    );
  }

  addExpense() {
    this.spinner.show();
    this.userExpenseService.addExpense(this.newExpense).subscribe({
      next: (response) => {
        console.log('Expense added successfully:', response);
        this.spinner.hide();
        // Reset the form
        this.newExpense = {
          name: '',
          categoryId: null,
          amount: null,
          type: 'monthly',
          startDate: '',
          endDate: ''
        };
      },
      error: (error) => {
        console.error('Error adding expense:', error);
        this.spinner.hide();
      }
    });
  }

  editCategory(expense: ExpenseModel) {
    this.spinner.show();
    this.userExpenseService.editExpense(expense).subscribe({
      next : ()=>{
        this.spinner.hide();
        this.toastr.success("Successfully edited");
      },
      error:(err)=>{
        this.spinner.hide();
        this.toastr.error("Errro during editing catgeory");
      }
    })
  }

  deleteCategory(expense: ExpenseModel) {
    const message = `
    <b>Are you sure you want to delete this expense?</b><br><br>
    <strong>• Title:</strong> ${expense.name}<br>
    <strong>• Target Amount:</strong> ${this.formatCurrency(expense.amount)}<br>
    <strong>• Category:</strong> ${expense.category.name}<br>
    <strong>• Type:</strong> ${expense.type}<br><br>
    This action cannot be undone.
    `;

  
    this.dialog.open(ConfirmDialogComponent, {
            width: '400px',
            data: {
              title: 'Delete Expense?',
              message: message,
              confirmText: 'Delete',
              cancelText: 'Cancel'
            }
          }).afterClosed().subscribe({
            next: (confirmed) => {
              if (confirmed) {
                this.spinner.show();
                this.userExpenseService.deleteExpense(expense.id).subscribe({
                  next: () => {
                    this.toastr.success('Expense deleted successfully');
                    this.spinner.hide();
                  },
                  error: (err) => {
                    this.toastr.error(`Failed to delete expense: ${err.errorCode}`);
                    this.spinner.hide();
                  }
                });
              }
            }
          });
    
  }

  loadCategories() {
    this.spinner.show();
    this.userService.getCategories().subscribe({
      next: (response) => {
        this.categories = response.filter(category => category.usageTypes.includes("EXPENSE") || category.usageTypes.includes("SHARED"));
        this.spinner.hide();
      },
      error: (error) => {
        console.error('Error fetching categories:', error);
        this.spinner.hide();
      }
    });
  }
  loadExpenses(){
    this.spinner.show();
    this.userExpenseService.getExpenseCategories().subscribe({
      next : (response)=>{
        this.expenses = response;
        this.spinner.hide();
      },
      error:(error)=>{
        this.spinner.hide();
        console.error('Error fetching expenses:', error?.errorCode);
      }
    })
  }
  formatCurrency(amount: number): string {
    return new Intl.NumberFormat('en-US', {
      style: 'currency',
      currency: 'USD',
      minimumFractionDigits: 0,
      maximumFractionDigits: 0
    }).format(amount);
  }
}
