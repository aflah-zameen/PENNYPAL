import { Component, Input, SimpleChanges } from '@angular/core';
import { SubscriptionBreakdown } from '../../models/admin-analytics-model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-subscription-breakdown-chart',
  imports: [CommonModule],
  templateUrl: './subscription-breakdown-chart.component.html',
  styleUrl: './subscription-breakdown-chart.component.css'
})
export class SubscriptionBreakdownChartComponent {
  @Input() breakdownData: SubscriptionBreakdown[] = []

  chartSegments: Array<{
    color: string
    dashArray: string
    dashOffset: number
    data: SubscriptionBreakdown
  }> = []

  tooltip = {
    show: false,
    x: 0,
    y: 0,
    title: "",
    count: 0,
    revenue: 0,
  }

  get totalSubscriptions(): number {
    return this.breakdownData.reduce((sum, item) => sum + item.count, 0)
  }

  ngOnInit() {
    this.updateChart()
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes["breakdownData"]) {
      this.updateChart()
    }
  }

  updateChart() {
    if (!this.breakdownData || this.breakdownData.length === 0) return

    const circumference = 2 * Math.PI * 80 // radius = 80
    let currentOffset = 0

    this.chartSegments = this.breakdownData.map((item) => {
      const segmentLength = (item.percentage / 100) * circumference
      const dashArray = `${segmentLength} ${circumference - segmentLength}`
      const dashOffset = -currentOffset

      currentOffset += segmentLength

      return {
        color: item.color,
        dashArray,
        dashOffset,
        data: item,
      }
    })
  }

  showTooltip(segment: {
    color: string
    dashArray: string
    dashOffset: number
    data: SubscriptionBreakdown
  }, event: MouseEvent) {
    const rect = (event.target as Element).getBoundingClientRect()
    const container = (event.target as Element).closest(".relative")?.getBoundingClientRect()

    if (container) {
      this.tooltip = {
        show: true,
        x: rect.left - container.left + 10,
        y: rect.top - container.top - 60,
        title: segment.data.type,
        count: segment.data.count,
        revenue: segment.data.revenue,
      }
    }
  }

  hideTooltip() {
    this.tooltip.show = false
  }
}
