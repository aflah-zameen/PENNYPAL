import { Component,Input, Pipe } from '@angular/core';
import { CoinIconComponent } from "../coin-icon/coin-icon.component";
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-balance-display',
  imports: [CoinIconComponent,CommonModule],
  templateUrl: './balance-display.component.html',
  styleUrl: './balance-display.component.css'
})
export class BalanceDisplayComponent {
  @Input() balance : number =0;
}
