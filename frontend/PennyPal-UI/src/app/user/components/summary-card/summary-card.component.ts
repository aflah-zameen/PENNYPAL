import { CommonModule, CurrencyPipe } from '@angular/common';
import { Component,Input,Output } from '@angular/core';

@Component({
  selector: 'app-summary-card',
  imports: [CommonModule],
  templateUrl: './summary-card.component.html',
  styleUrl: './summary-card.component.css',
  providers :[CurrencyPipe]
})
export class SummaryCardComponent {
  @Input() performance : number | null =null;
  @Input() growth : boolean =false;
  @Input() header : string = '';
  @Input() content : string|number =0;
  @Input() growthText : string ='';
  @Input() size : 'small' | 'large' ='large';

  constructor(private currencyPipe : CurrencyPipe){}

  getFormattedContent(): string {
    if (typeof this.content === 'number') {
      return this.currencyPipe.transform(this.content, 'USD', 'symbol', '1.2-2') || '$0.00';
    }
    return this.content;
  }
}
