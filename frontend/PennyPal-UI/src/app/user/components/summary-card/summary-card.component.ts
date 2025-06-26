import { CommonModule } from '@angular/common';
import { Component,Input,Output } from '@angular/core';

@Component({
  selector: 'app-summary-card',
  imports: [CommonModule],
  templateUrl: './summary-card.component.html',
  styleUrl: './summary-card.component.css'
})
export class SummaryCardComponent {
  @Input() performance : number =0;
  @Input() growth : boolean =false;
  @Input() header : string = '';
  @Input() content : string ='';
  @Input() growthText : string ='';
  @Input() size : 'small' | 'large' ='large';
}
