import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output, output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AddIncomeModel } from '../../models/add-income.model';
import { IncomeModel } from '../../models/income.model';

@Component({
  selector: 'app-add-income',
  imports: [CommonModule,FormsModule],
  templateUrl: './add-income.component.html',
  styleUrl: './add-income.component.css'
})
export class AddIncomeComponent {
  @Input() isModalOpen = false;
  @Output() close = new EventEmitter<void>()
  @Output() submitForm : EventEmitter<AddIncomeModel> = new EventEmitter<AddIncomeModel>();
  
   getTodayDateString(){
    return new Date().toISOString().split('T')[0];
  }
  
  formData : AddIncomeModel = {
    amount : 0,
    income_date : this.getTodayDateString(),
    source : '',
    notes : '',
    recurring : ''
  };

  resetForm() {
    this.formData = {
        amount : 0,
        income_date : this.getTodayDateString(),
        notes : '',
        source : '',
        recurring : ''
      };

  }

  onSubmit(){
    this.submitForm.emit(this.formData);
    this.resetForm();
    this.close.emit();
  }
 
}
