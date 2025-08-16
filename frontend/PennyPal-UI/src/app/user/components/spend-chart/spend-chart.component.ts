import { ChangeDetectionStrategy, Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChartOptions,ChartType } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';
import { FormsModule } from '@angular/forms';
import { SpendData } from '../../models/CreditCard.model';


type RangeKey = 'monthly' | 'weekly' | 'yearly';
@Component({
  selector: 'app-spend-chart',
  imports: [CommonModule,BaseChartDirective,FormsModule],
  templateUrl: './spend-chart.component.html',
  styleUrl: './spend-chart.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SpendChartComponent {
  @Input() spendData: SpendData|null = null
  @Output() onChange: EventEmitter<string> = new EventEmitter<string>();
  selectedRange: RangeKey = 'monthly';

  barChartType: ChartType = 'bar';
  barChartOptions: ChartOptions = {
    responsive: true,
    scales: {
      y: {
        beginAtZero: true,
        ticks: {
          stepSize: 250,
          callback: (value) => value.toString(),
        },
      },
    },
    plugins: {
      legend: { display: false },
      tooltip: { enabled: true },
    },
  };

  // rangeData = {
  //   month: {
  //     labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep'],
  //     data: [900, 250, 450, 800, 200, 300, 180, 280, 220],
  //   },
  //   week: {
  //     labels: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
  //     data: [120, 150, 90, 200, 100, 80, 160],
  //   },
  //   year: {
  //     labels: ['2021', '2022', '2023', '2024', '2025'],
  //     data: [3000, 4200, 3900, 4800, 4100],
  //   },
  // };


  get barChartData() {
    const labels = this.spendData?.label || [];
    const data = this.spendData?.amount || [];
    return {
      labels: labels,
      datasets: [{
        data: data,
        backgroundColor: data.map((_, i) =>
          this.selectedRange === 'monthly' && i === 3
            ? 'rgba(59,130,246,0.8)' // April highlighted
            : 'rgba(147,197,253,0.8)' // Default bar
        ),
      }],
    };
}
  onRangeChange(event: Event) {
  const value = (event.target as HTMLSelectElement)?.value;
  if (value && (value === 'monthly' || value === 'weekly' || value === 'yearly')) {
      this.onChange.emit(value);
     }
}
}
