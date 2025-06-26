import { CommonModule } from '@angular/common';
import { Component,Input,EventEmitter,Output } from '@angular/core';
import { FiltersDTO } from '../../../models/FiltersDTO';

@Component({
  selector: 'app-filter-dropdown',
  imports: [CommonModule],
  templateUrl: './filter-dropdown.component.html',
  styleUrl: './filter-dropdown.component.css'
})
export class FilterDropdownComponent {
  @Input() label: string = '';
  @Input() filterKey! : keyof FiltersDTO;
  @Input() options: string[] = [];
  @Input() selected: string | null = null;

  @Output() filterChange = new EventEmitter<Partial<FiltersDTO>>();

  onSelectChange(event : Event): void {
    const value = (event.target as HTMLSelectElement).value;
    this.filterChange.emit({[this.filterKey]:value});
  }
}
