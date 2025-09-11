export interface SubscriptionAnalytics {
  totalRevenue: number;
  activeSubscriptions: number;
  monthlyRecurringRevenue: number;
  planSummaries: SubscriptionBreakdownOutput[];
}

export interface SubscriptionBreakdownOutput{
  planId : string,
  subscriberCount : number,
  totalRevenue : number
}

export interface SalesData{
  planId :string,
  planName : string,
  subscriberCount : number,
  revenue : number 
  month : string
}


export interface SubscriptionBreakdown {
  type: string 
  count: number
  revenue: number
  percentage: number
  color: string 
}


export interface PaymentStatusData {
  status: "successful" | "pending" | "failed"
  count: number
  amount: number
  percentage: number
}

export interface PaymentStatusDTO {
  status: string; // e.g. "COMPLETED", "PENDING", "FAILED", "CANCELLED"
  count: number;
  totalAmount: number;
}


export interface AnalyticsFilters {
  subscriptionType: string[];
  dateRange: {
    start: Date;
    end: Date;
  };
  paymentStatus: string[];
}


export interface ExportOptions {
  format: "pdf" | "excel"
  includeCharts: boolean
  dateRange: {
    start: Date
    end: Date
  }
}
