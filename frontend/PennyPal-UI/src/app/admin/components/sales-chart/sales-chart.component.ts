import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { SalesData } from '../../models/admin-analytics-model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
interface ChartPoint {
  x: number;
  [planId: string]: number | string; // dynamic keys for each plan
}

@Component({
  selector: 'app-sales-chart',
  imports: [CommonModule,FormsModule],
  templateUrl: './sales-chart.component.html',
  styleUrl: './sales-chart.component.css'
})

export class SalesChartComponent implements OnInit {
  @Input() salesData: SalesData[] = [];
  planIds: { planId: string; planName: string }[] = [];
  planColors: { [planId: string]: string } = {};
  colorPalette: string[] = ['#4F46E5', '#10B981', '#F59E0B', '#EF4444', '#6366F1'];
  tooltip = {
    show: false,
    x: 0,
    y: 0,
    title: '',
    value: 0
  };

   ngOnChanges(changes: SimpleChanges): void {
    if (changes['salesData']) {
      this.preparePlans(); 
      this.updateChart();
    }
  }

  ngOnInit(): void {
    if (this.salesData.length > 0) {
      this.preparePlans();
      this.updateChart();
    }
  }

  chartPoints: ChartPoint[] = [];

  preparePlans(): void {
  const uniquePlans = new Map<string, string>();
  this.salesData.forEach(d => {
    if (!uniquePlans.has(d.planId)) {
      uniquePlans.set(d.planId, d.planName);
    }
  });

  this.planIds = Array.from(uniquePlans.entries()).map(([planId, planName]) => ({ planId, planName }));

  this.planIds.forEach((plan, i) => {
    this.planColors[plan.planId] = this.colorPalette[i % this.colorPalette.length];
  });
}


updateChart(): void {
  const months = [...new Set(this.salesData.map(d => d.month))];
  const monthSpacing = 800 / months.length;

  this.chartPoints = months.map((month, i) => {
    const point: ChartPoint = { x: i * monthSpacing };
    this.planIds.forEach(plan => {
      const data = this.salesData.find(d => d.month === month && d.planId === plan.planId);
      point[plan.planId] = data ? this.scaleRevenue(data.revenue) : 280; // fallback to bottom
    });
    return point;
  });
}

scaleRevenue(value: number): number {
  const maxRevenue = Math.max(...this.salesData.map(d => d.revenue));
  const chartHeight = 280;
  if (!maxRevenue || isNaN(value)) return chartHeight;
  return chartHeight - (value / maxRevenue) * chartHeight;
}


getLinePoints(planId: string): string {
  return this.chartPoints
    .map(p => {
      const y = typeof p[planId] === 'number' ? p[planId] : 280; // fallback to bottom
      return `${p.x},${y}`;
    })
    .join(' ');
}





  showTooltip(index: number, planId: string, event: MouseEvent): void {
    const data = this.salesData.find(d => d.planId === planId);
    if (!data) return;

    const rect = (event.target as Element).getBoundingClientRect();
    const container = (event.target as Element).closest('.relative')?.getBoundingClientRect();

    if (container) {
      this.tooltip = {
        show: true,
        x: rect.left - container.left + 10,
        y: rect.top - container.top - 40,
        title: `${data.planName} - ${this.getMonthLabel(index)}`,
        value: data.revenue
      };
    }
  }

  hideTooltip(): void {
    this.tooltip.show = false;
  }

  getMonthLabel(index: number): string {
  const rawMonth = this.chartPoints[index]?.x;
  const monthStr = this.salesData[index]?.month;
  if (!monthStr || monthStr.length !== 6) return 'Unknown';

  const year = monthStr.slice(0, 4);
  const month = parseInt(monthStr.slice(4, 6), 10);
  const monthNames = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
  return `${monthNames[month - 1]} ${year}`;
}

}