import { Component, OnInit } from '@angular/core';
import { ExpenseCategory } from '../../models/expense-category.model';
import { NewExpense } from '../../models/expense-new-category';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NgxSpinnerService } from 'ngx-spinner';
import { UserExpenseService } from '../../services/user-expense.service';
import { ExpenseModel } from '../../models/expense.model';

@Component({
  selector: 'app-expense-management',
  imports: [CommonModule,FormsModule],
  templateUrl: './expense-management.component.html',
  styleUrl: './expense-management.component.css'
})
export class ExpenseManagementComponent implements OnInit {
  searchTerm: string = '';
  
  categories: ExpenseModel[] = []

  newExpense: NewExpense = {
    name: '',
    category: 'Food',
    amount: 0,
    type: 'monthly',
    startDate: '',
    endDate: ''
  };

  constructor(private spinner:NgxSpinnerService,
    private userExpenseService: UserExpenseService
  ){}

  ngOnInit() {
    this.loadCategories();
    this.userExpenseService.addExpense$.subscribe(() => {
      this.loadCategories();
    });
  }

  get filteredCategories(): ExpenseModel[] {
    if (!this.searchTerm) {
      return this.categories;
    }
    return this.categories.filter(category =>
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
          category: 'Food',
          amount: 0,
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

  editCategory(category: ExpenseModel) {
    // Implement edit functionality
    console.log('Edit category:', category);
  }

  deleteCategory(id: number) {
    this.categories = this.categories.filter(cat => cat.id !== id);
  }

  loadCategories() {
    this.spinner.show();
    this.userExpenseService.getExpenseCategories().subscribe({
      next: (response) => {
        this.categories = response;
        this.spinner.hide();
      },
      error: (error) => {
        console.error('Error fetching categories:', error);
        this.spinner.hide();
      }
    });
  }
}
