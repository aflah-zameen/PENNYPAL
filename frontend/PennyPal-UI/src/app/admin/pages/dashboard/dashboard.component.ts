import { Component } from '@angular/core';
import { AnalyticsFilters, PaymentStatusData, SalesData, SubscriptionAnalytics, SubscriptionBreakdown } from '../../models/admin-analytics-model';
import { SubscriptionAnalyticsService } from '../../services/admin-subscription-analytics-service';
import { AnalyticsSummaryCardsComponent } from "../../components/analytics-summary-cards/analytics-summary-cards.component";
import { AnalyticsFilterComponent } from "../../components/analytics-filter/analytics-filter.component";
import { SalesChartComponent } from "../../components/sales-chart/sales-chart.component";
import { SubscriptionBreakdownChartComponent } from "../../components/subscription-breakdown-chart/subscription-breakdown-chart.component";
import { CommonModule } from '@angular/common';
import { forkJoin } from 'rxjs';
import { AdminSubscriptionService } from '../../services/admin-subscription-service';
import { SubscriptionPlan } from '../../../user/models/subscription-plan-model';

@Component({
  selector: 'app-dashboard',
  imports: [CommonModule,AnalyticsSummaryCardsComponent, AnalyticsFilterComponent, SalesChartComponent, SubscriptionBreakdownChartComponent],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {
  analytics: SubscriptionAnalytics | null = null
  salesData: SalesData[] = []
  subscriptionBreakdown: SubscriptionBreakdown[] = []
  paymentStatusData: PaymentStatusData[] = []

  showExportToast = false
  showErrorToast = false
  errorMessage = ""

  plans : SubscriptionPlan[]=[]
  status : string[] = ["COMPLETED","PENDING","FAILED"]

  constructor(private analyticsService: SubscriptionAnalyticsService,private planService : AdminSubscriptionService) {}

  ngOnInit() {
   this.planService.getPlans().subscribe(data =>{
        this.plans = data;
      this.loadAllData();  
    })
  }

  loadAllData() {

  forkJoin({
    analytics: this.analyticsService.getAnalytics({
     dateRange: {
      start: new Date(new Date().getFullYear(), 0, 1),
      end: new Date() 
    },
    subscriptionType: this.plans.map(data => data.id),
    paymentStatus: this.status
    }),
    salesData: this.analyticsService.getSalesData({
     dateRange: {
      start: new Date(new Date().getFullYear(), 0, 1),
      end: new Date() 
    },
    subscriptionType: this.plans.map(data => data.id),
    paymentStatus: this.status
    }),
    breakdown: this.analyticsService.getSubscriptionBreakdown({
     dateRange: {
      start: new Date(new Date().getFullYear(), 0, 1),
      end: new Date() 
    },
    subscriptionType: this.plans.map(data => data.id),
    paymentStatus: this.status
    }),
    paymentStatus: this.analyticsService.getPaymentStatusData({
     dateRange: {
      start: new Date(new Date().getFullYear(), 0, 1),
      end: new Date() 
    },
    subscriptionType: this.plans.map(data => data.id),
    paymentStatus: this.status
    })
  }).subscribe({
    next: ({ analytics, salesData, breakdown, paymentStatus }) => {
      this.analytics = analytics;
      this.salesData = salesData;
      this.subscriptionBreakdown = breakdown;
      this.paymentStatusData = paymentStatus;
      
    },
    error: () => {
      this.showError("Failed to load analytics data");
    }
  });
}


  refreshData() {
    this.loadAllData()
  }

  onFiltersChanged(filters: AnalyticsFilters) {

    forkJoin({
      analytics: this.analyticsService.getAnalytics(filters),
      salesData: this.analyticsService.getSalesData(filters),
      breakdown: this.analyticsService.getSubscriptionBreakdown(filters),
      paymentStatus: this.analyticsService.getPaymentStatusData(filters)
    }).subscribe({
      next: ({ analytics, salesData, breakdown, paymentStatus }) => {
        this.analytics = analytics;
        this.salesData = salesData;
        this.subscriptionBreakdown = breakdown;
        this.paymentStatusData = paymentStatus;
      },
      error: () => {
        this.showError('Failed to apply filters');
      }
    });
  }

  onExportRequested(event: { format: "pdf" | "excel" }) {

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

          this.showExportToast = true
          setTimeout(() => (this.showExportToast = false), 3000)
        },
        error: (error) => {
          this.showError("Export failed")
        },
      })
  }

  private showError(message: string) {
    this.errorMessage = message
    this.showErrorToast = true
    setTimeout(() => (this.showErrorToast = false), 3000)
  }
}
