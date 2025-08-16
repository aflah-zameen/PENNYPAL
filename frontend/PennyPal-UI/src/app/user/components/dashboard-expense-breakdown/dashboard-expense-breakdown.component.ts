import { CommonModule, DecimalPipe } from '@angular/common';
import { AfterViewInit, Component, ElementRef, Input, OnChanges, SimpleChanges, ViewChild } from '@angular/core';
import {
  Chart,
  ArcElement,
  Tooltip,
  Legend,
  ChartConfiguration,
  DoughnutController,
  ChartType
} from 'chart.js';
import { ExpenseData } from '../../models/dashboard.model';

Chart.register(ArcElement, Tooltip, Legend,DoughnutController);


@Component({
  selector: 'app-dashboard-expense-breakdown',
  imports: [DecimalPipe, CommonModule],
  templateUrl: './dashboard-expense-breakdown.component.html',
  styleUrl: './dashboard-expense-breakdown.component.css'
})
export class DashboardExpenseBreakdownComponent implements OnChanges, AfterViewInit {
  @Input() expenseData: ExpenseData[] = [];
  @ViewChild('chartCanvas', { static: false }) chartCanvas!: ElementRef<HTMLCanvasElement>;

  private chart: Chart | null = null;
  private readonly colors = [
    '#3B82F6', '#8B5CF6', '#10B981', '#F59E0B',
    '#EF4444', '#6B7280', '#EC4899', '#14B8A6'
  ];

  ngAfterViewInit() {
    if (this.expenseData.length > 0) {
      this.assignColors();
      this.createChart();
    }
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['expenseData'] && !changes['expenseData'].firstChange) {
      this.assignColors();
      this.updateChart();
    }
  }

  private assignColors(): void {
    this.expenseData.forEach((item, index) => {
      item.color = this.colors[index % this.colors.length];
    });
  }

  private createChart(): void {
    const ctx = this.chartCanvas.nativeElement.getContext('2d');
    if (!ctx) return;

    const config: ChartConfiguration<'doughnut'> = {
      type: 'doughnut',
      data: {
        labels: this.expenseData.map(item => item.category),
        datasets: [{
          data: this.expenseData.map(item => item.amount),
          backgroundColor: this.expenseData.map(item => item.color),
          borderWidth: 0,
          hoverBorderWidth: 3,
          hoverBorderColor: '#ffffff'
        }]
      },
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
            callbacks: {
              label: (context) => {
                const dataArray = Array.isArray(context.dataset.data)
                  ? context.dataset.data.filter((v): v is number => typeof v === 'number')
                  : [];
                const total = dataArray.reduce((a, b) => a + b, 0);
                const value = typeof context.parsed === 'number' ? context.parsed : 0;
                const percentage = total > 0 ? ((value / total) * 100).toFixed(1) : '0.0';
                const label = context.label ?? '';
                return `${label}: $${value.toFixed(2)} (${percentage}%)`;
              }
            }
          }
        },
        animation: { duration: 1000 },
        cutout: '60%'
      }
    };

    this.chart = new Chart(ctx, config);
  }

  private updateChart(): void {
    if (this.chart) {
      this.chart.data.labels = this.expenseData.map(item => item.category);
      this.chart.data.datasets[0].data = this.expenseData.map(item => item.amount);
      this.chart.data.datasets[0].backgroundColor = this.expenseData.map(item => item.color);
      this.chart.update();
    } else {
      this.createChart();
    }
  }

  getTotalExpenses(): number {
    return this.expenseData.reduce((sum, item) => sum + item.amount, 0);
  }

  getHighestCategory(): string {
    if (this.expenseData.length === 0) return '';
    return this.expenseData.reduce((prev, current) =>
      prev.amount > current.amount ? prev : current
    ).category;
  }
}
