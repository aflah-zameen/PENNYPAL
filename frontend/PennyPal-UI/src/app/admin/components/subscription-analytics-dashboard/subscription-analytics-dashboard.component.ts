import { Component } from '@angular/core';
import { AnalyticsFilters, PaymentStatusData, SalesData, SubscriptionAnalytics, SubscriptionBreakdown } from '../../models/admin-analytics-model';
import { SubscriptionAnalyticsService } from '../../services/admin-subscription-analytics-service';
import { CommonModule } from '@angular/common';
import { AnalyticsSummaryCardsComponent } from "../analytics-summary-cards/analytics-summary-cards.component";
import { AnalyticsFilterComponent } from "../analytics-filter/analytics-filter.component";
import { SalesChartComponent } from "../sales-chart/sales-chart.component";
import { SubscriptionBreakdownChartComponent } from "../subscription-breakdown-chart/subscription-breakdown-chart.component";

@Component({
  selector: 'app-subscription-analytics-dashboard',
  imports: [CommonModule, AnalyticsSummaryCardsComponent, AnalyticsFilterComponent, SalesChartComponent, SubscriptionBreakdownChartComponent],
  templateUrl: './subscription-analytics-dashboard.component.html',
  styleUrl: './subscription-analytics-dashboard.component.css'
})
export class SubscriptionAnalyticsDashboardComponent {
  analytics: SubscriptionAnalytics | null = null
  salesData: SalesData[] = []
  subscriptionBreakdown: SubscriptionBreakdown[] = []
  paymentStatusData: PaymentStatusData[] = []

  loading = false
  showExportToast = false
  showErrorToast = false
  errorMessage = ""

  constructor(private analyticsService: SubscriptionAnalyticsService) {}


  loadAllData() {
    this.loading = true

    Promise.all([
      this.analyticsService.getAnalytics().toPromise(),
      this.analyticsService.getSalesData().toPromise(),
      this.analyticsService.getSubscriptionBreakdown().toPromise(),
      this.analyticsService.getPaymentStatusData().toPromise(),
    ])
      .then(([analytics, salesData, breakdown, paymentStatus]) => {
        this.analytics = analytics!
        this.salesData = salesData!
        this.subscriptionBreakdown = breakdown!
        this.paymentStatusData = paymentStatus!
        this.loading = false
      })
      .catch((error) => {
        this.showError("Failed to load analytics data")
        this.loading = false
      })
  }

  refreshData() {
    this.loadAllData()
  }

  onFiltersChanged(filters: AnalyticsFilters) {
    this.loading = true

    Promise.all([
      this.analyticsService.getAnalytics(filters).toPromise(),
      this.analyticsService.getSalesData(filters).toPromise(),
      this.analyticsService.getSubscriptionBreakdown(filters).toPromise(),
      this.analyticsService.getPaymentStatusData(filters).toPromise(),
    ])
      .then(([analytics, salesData, breakdown, paymentStatus]) => {
        this.analytics = analytics!
        this.salesData = salesData!
        this.subscriptionBreakdown = breakdown!
        this.paymentStatusData = paymentStatus!
        this.loading = false
      })
      .catch((error) => {
        this.showError("Failed to apply filters")
        this.loading = false
      })
  }

  onExportRequested(event: { format: "pdf" | "excel" }) {
    this.loading = true

    this.analyticsService
      .exportData({
        format: event.format,
        includeCharts: true,
        dateRange: {
          start: new Date(new Date().getFullYear(), 0, 1),
          end: new Date(),
        },
      })
      .subscribe({
        next: (blob) => {
          // Create download link
          const url = window.URL.createObjectURL(blob)
          const link = document.createElement("a")
          link.href = url
          link.download = `subscription-analytics.${event.format === "pdf" ? "pdf" : "xlsx"}`
          link.click()
          window.URL.revokeObjectURL(url)

          this.loading = false
          this.showExportToast = true
          setTimeout(() => (this.showExportToast = false), 3000)
        },
        error: (error) => {
          this.showError("Export failed")
          this.loading = false
        },
      })
  }

  private showError(message: string) {
    this.errorMessage = message
    this.showErrorToast = true
    setTimeout(() => (this.showErrorToast = false), 3000)
  }
}
