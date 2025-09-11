import { Injectable } from "@angular/core"
import { type Observable, of, delay, map } from "rxjs"
import type {
  RedemptionRequest,
  RedemptionStats,
  RedemptionFilter,
  RedemptionRequestsResponse,
} from "../models/admin-redemption-model"
import { environment } from "../../../environments/environment"
import { HttpClient, HttpParams } from "@angular/common/http"
import { ApiResponse } from "../../models/ApiResponse"

@Injectable({
  providedIn: "root",
})
export class AdminRedemptionService {
  private readonly baseUrl = `${environment.apiBaseUrl}/api/private/admin/redemptions`

  constructor(private http: HttpClient) {}

  getRedemptionStats(): Observable<RedemptionStats> {
    return this.http.get<ApiResponse<RedemptionStats>>(`${this.baseUrl}/stats`, { withCredentials: true }).pipe(
      map(res => res.data)
    )
  }

  getRedemptionRequests(
    filter: RedemptionFilter,
    page = 1,
    itemsPerPage = 10
  ): Observable<RedemptionRequestsResponse> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('itemsPerPage', itemsPerPage.toString())

    if (filter.status && filter.status !== 'ALL') {
      params = params.set('status', filter.status)
    }
    if (filter.search) {
      params = params.set('search', filter.search)
    }
    if (filter.dateFrom) {
      params = params.set('dateFrom', filter.dateFrom.toISOString())
    }
    if (filter.dateTo) {
      params = params.set('dateTo', filter.dateTo.toISOString())
    }

    return this.http.get<ApiResponse<RedemptionRequestsResponse>>(`${this.baseUrl}`, {
      params,
      withCredentials: true,
    }).pipe(
      map(res => res.data)
    )
  }

  approveRequest(requestId: string): Observable<void> {
    return this.http.post<void>(
      `${this.baseUrl}/${requestId}/approve`,
      {},
      { withCredentials: true }
    )
  }

  rejectRequest(requestId: string, reason: string): Observable<void> {
    return this.http.post<void>(
      `${this.baseUrl}/${requestId}/reject`,
      { reason },
      { withCredentials: true }
    )
  }
}