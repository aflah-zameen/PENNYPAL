import { Component, EventEmitter, Output } from '@angular/core';
import { FiltersDTO } from '../../../models/FiltersDTO';
import { CommonModule } from '@angular/common';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-date-filter',
  imports: [CommonModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatDatepickerModule,
    MatNativeDateModule],
  templateUrl: './date-filter.component.html',
  styleUrl: './date-filter.component.css'
})
export class DateFilterComponent {
   @Output() filterChange = new EventEmitter<Partial<FiltersDTO>>();

  onChange(field: 'joinedAfter' | 'joinedBefore', value: Date | null) {
    this.filterChange.emit({ [field]: value ?? undefined });
  }
}
