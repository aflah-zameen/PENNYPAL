import { DecimalPipe } from '@angular/common';
import { AfterViewInit, Component, ElementRef, Input, OnChanges, SimpleChanges, ViewChild } from '@angular/core';
import {
  Chart,
  ChartConfiguration,
  LineController,
  LineElement,
  PointElement,
  LinearScale,
  CategoryScale,
  Title,
  Tooltip,
  Legend,
  Filler
} from 'chart.js';

Chart.register(
  LineController,
  LineElement,
  PointElement,
  LinearScale,
  CategoryScale,
  Title,
  Tooltip,
  Legend,
  Filler
);

interface ChartData {
  date: string;
  income: number;
  expense: number;
} 

@Component({
  selector: 'app-dashboard-income-expense-chart',
  imports: [DecimalPipe],
  templateUrl: './dashboard-income-expense-chart.component.html',
  styleUrl: './dashboard-income-expense-chart.component.css'
})
export class DashboardIncomeExpenseChartComponent implements OnChanges, AfterViewInit {
  @Input() chartData: ChartData[] = [];
  @ViewChild('chartCanvas', { static: false }) chartCanvas!: ElementRef<HTMLCanvasElement>;

  private chart: Chart | null = null;

  ngAfterViewInit() {
    if (this.chartData.length > 0) {
      this.createChart();
    }
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['chartData'] && !changes['chartData'].firstChange) {
      this.updateChart();
    }
  }

  private createChart() {
    const ctx = this.chartCanvas.nativeElement.getContext('2d');
    if (!ctx) return;

    const config: ChartConfiguration<'line'> = {
      type: 'line',
      data: this.prepareChartData(),
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          legend: { display: false },
          tooltip: {
            backgroundColor: 'rgba(255, 255, 255, 0.95)',
            titleColor: '#374151',
            bodyColor: '#374151',
            borderColor: '#E5E7EB',
            borderWidth: 1,
            cornerRadius: 12,
            displayColors: true,
            callbacks: {
              label: (context) => `${context.dataset.label}: $${context.parsed.y.toFixed(2)}`
            }
          }
        },
        scales: {
          x: {
            type: 'category',
            grid: { display: false },
            ticks: {
              color: '#6B7280',
              font: { size: 12 }
            }
          },
          y: {
            grid: { color: '#F3F4F6' },
            ticks: {
              color: '#6B7280',
              font: { size: 12 },
              callback: value => '$' + value
            }
          }
        },
        interaction: {
          intersect: false,
          mode: 'index'
        }
      }
    };

    this.chart = new Chart(ctx, config);
  }

  private updateChart() {
    if (this.chart) {
      const newData = this.prepareChartData();
      this.chart.data.labels = newData.labels;
      this.chart.data.datasets = newData.datasets;
      this.chart.update();
    } else {
      this.createChart();
    }
  }

  private prepareChartData() {
    return {
      labels: this.chartData.map(item => {
        const date = new Date(item.date);
        return `${date.getDate()}`;
      }),
      datasets: [
        {
          label: 'Income',
          data: this.chartData.map(item => item.income),
          borderColor: '#10B981',
          backgroundColor: 'rgba(16, 185, 129, 0.1)',
          borderWidth: 3,
          fill: true,
          tension: 0.4,
          pointBackgroundColor: '#10B981',
          pointBorderColor: '#ffffff',
          pointBorderWidth: 2,
          pointRadius: 5
        },
        {
          label: 'Expenses',
          data: this.chartData.map(item => item.expense),
          borderColor: '#EF4444',
          backgroundColor: 'rgba(239, 68, 68, 0.1)',
          borderWidth: 3,
          fill: true,
          tension: 0.4,
          pointBackgroundColor: '#EF4444',
          pointBorderColor: '#ffffff',
          pointBorderWidth: 2,
          pointRadius: 5
        }
      ]
    };
  }

  getTotalIncome(): number {
    return this.chartData.reduce((sum, item) => sum + item.income, 0);
  }

  getTotalExpenses(): number {
    return this.chartData.reduce((sum, item) => sum + item.expense, 0);
  }
}