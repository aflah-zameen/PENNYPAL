import { Injectable } from "@angular/core"
import { type Observable, of, delay, map } from "rxjs"
import type {
  SubscriptionAnalytics,
  SalesData,
  SubscriptionBreakdown,
  PaymentStatusData,
  AnalyticsFilters,
  ExportOptions,
  SubscriptionBreakdownOutput,
  PaymentStatusDTO,
} from "../models/admin-analytics-model"
import { HttpClient, HttpParams } from "@angular/common/http"
import { environment } from "../../../environments/environment"

@Injectable({
  providedIn: "root",
})
export class SubscriptionAnalyticsService {

  private readonly apiURL = `${environment.apiBaseUrl}/api/private/admin/analytics`  

  colorPalette = [
  '#4CAF50', '#2196F3', '#FF9800', '#9C27B0', '#F44336',
  '#00BCD4', '#8BC34A', '#FFC107', '#3F51B5', '#E91E63'
];
  constructor(private http: HttpClient) {}

  getAnalytics(filters?: AnalyticsFilters): Observable<SubscriptionAnalytics> {

    const params = this.buildQueryParams(filters)
    return this.http.get<SubscriptionAnalytics>(this.apiURL+'/summary', { withCredentials:true,params })
  }

  getSalesData(filters?: AnalyticsFilters): Observable<SalesData[]> {
      const effectiveFilters = filters ?? {
    dateRange: {
      start: new Date(new Date().getFullYear(), 0, 1), // Jan 1st of current year
      end: new Date() // today
    },
    subscriptionType: [],
    paymentStatus: []
  };
    const params = this.buildQueryParams(effectiveFilters)
    return this.http.get<SalesData[]>(this.apiURL+'/sales', {withCredentials:true ,params })
  }

  getSubscriptionBreakdown(filters?: AnalyticsFilters): Observable<SubscriptionBreakdown[]> {
      const effectiveFilters = filters ?? {
    dateRange: {
      start: new Date(new Date().getFullYear(), 0, 1), // Jan 1st of current year
      end: new Date() // today
    },
    subscriptionType: [],
    paymentStatus: []
  };
  const params = this.buildQueryParams(effectiveFilters);
  return this.http.get<SubscriptionBreakdownOutput[]>(this.apiURL + '/breakdown', { withCredentials:true,params }).pipe(
    map((response): SubscriptionBreakdown[] => {
      const totalRevenue = response.reduce((sum, item) => sum + item.totalRevenue, 0);

      return response.map((item, index) => ({
        type: item.planId,
        count: item.subscriberCount,
        revenue: item.totalRevenue,
        percentage: totalRevenue > 0 ? (item.totalRevenue / totalRevenue) * 100 : 0,
        color: this.colorPalette[index % this.colorPalette.length]
      }));
    })
  );
}


  getPaymentStatusData(filters?: AnalyticsFilters): Observable<PaymentStatusData[]> {
      const effectiveFilters = filters ?? {
    dateRange: {
      start: new Date(new Date().getFullYear(), 0, 1), // Jan 1st of current year
      end: new Date() // today
    },
    subscriptionType: [],
    paymentStatus: []
  };
  const params = this.buildQueryParams(effectiveFilters);
  return this.http.get<PaymentStatusDTO[]>(this.apiURL + '/payment-status-summary', {withCredentials : true ,params }).pipe(
    map((response): PaymentStatusData[] => {
      const totalAmount = response.reduce((sum, item) => sum + item.totalAmount, 0);

      return response
        .filter(item => item.status !== 'CANCELLED') // optional: exclude if not needed
        .map(item => ({
          status: this.mapStatus(item.status),
          count: item.count,
          amount: item.totalAmount,
          percentage: totalAmount > 0 ? (item.totalAmount / totalAmount) * 100 : 0
        }));
    })
  );
}


  exportData(options: ExportOptions): Observable<Blob> {
    const body = {
      format: options.format,
      includeCharts: options.includeCharts,
      dateRange: options.dateRange,
    }
    return this.http.post(this.apiURL+'/export', body, {
      responseType: 'blob',
    })
  }

  private mapStatus(status: string): "successful" | "pending" | "failed" {
  switch (status) {
    case "COMPLETED":
      return "successful";
    case "PENDING":
      return "pending";
    case "FAILED":
    case "CANCELLED": // if you decide to include it
      return "failed";
    default:
      return "failed"; // fallback
  }
 }


  private buildQueryParams(filters?: AnalyticsFilters): HttpParams {
    let params = new HttpParams()

    if (!filters) return params

    if (filters.dateRange) {
      params = params
        .set('start', filters.dateRange.start.toISOString().slice(0,10))
        .set('end', filters.dateRange.end.toISOString().slice(0,10))
    }

    if (filters.subscriptionType?.length) {
      filters.subscriptionType.forEach((type) => {
        params = params.append('subscriptionType', type)
      })
    }

    if (filters.paymentStatus?.length) {
      filters.paymentStatus.forEach((status) => {
        params = params.append('paymentStatus', status)
      })
    }

    return params


  }

}
