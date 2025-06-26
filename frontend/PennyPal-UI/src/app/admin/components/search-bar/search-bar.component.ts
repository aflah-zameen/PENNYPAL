import { Component,Input,Output,EventEmitter } from '@angular/core';

@Component({
  selector: 'app-search-bar',
  imports: [],
  templateUrl: './search-bar.component.html',
  styleUrl: './search-bar.component.css'
})
export class SearchBarComponent {
  @Input() placeholder: string = 'Search Users';
  @Input() searchValue: string = '';
  @Output() searchChange = new EventEmitter<string>();

  onSearchChange(event: Event): void {
    const target = event.target as HTMLInputElement;
    this.searchChange.emit(target.value);
  }
}
