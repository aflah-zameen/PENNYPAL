import { Injectable } from "@angular/core"
import { BehaviorSubject, type Observable, map, combineLatest, of, delay, switchMap, tap, catchError } from "rxjs"
import type {
  Goal,
  GoalFilters,
  PaginationInfo,
  GoalStats,
  PaginatedGoalsResponse,
  WithdrawalRequest,
} from "../models/goal-management.model"
import { environment } from "../../../environments/environment";
import { HttpClient, HttpParams } from "@angular/common/http";
import { ApiResponse } from "../../models/ApiResponse";

@Injectable({
  providedIn: "root",
})
export class GoalDashboardService {
  private apiUrl = environment.apiBaseUrl + "/api/private/admin/goals"

    // --- State Management using BehaviorSubjects ---
  private goalsSubject = new BehaviorSubject<Goal[]>([]);
  private filtersSubject = new BehaviorSubject<GoalFilters>({
    keyword: '',
    status: [],
    dateRange: { from: null, to: null },
    category: '',
    minAmount: null,
    maxAmount: null,
  });
  private withdrawalRequestsSubject = new BehaviorSubject<WithdrawalRequest[]>([]);
  private paginationSubject = new BehaviorSubject<PaginationInfo>({
    page: 0,
    pageSize: 10,
    total: 0,
    totalPages: 0,
  });
  private isLoadingSubject = new BehaviorSubject<boolean>(false);

  // --- Public Observables for components to subscribe to ---
  public readonly goals$ = this.goalsSubject.asObservable();
  public readonly filters$ = this.filtersSubject.asObservable();
  public readonly pagination$ = this.paginationSubject.asObservable();
  public readonly withdrawalRequests$ = this.withdrawalRequestsSubject.asObservable();
  public readonly isLoading$ = this.isLoadingSubject.asObservable();

  constructor(private http: HttpClient) {
    // Trigger initial data fetch when filters or pagination change
    this.listenForDataChanges().subscribe();
    this.fetchWithdrawalRequests().subscribe(); // Initial fetch for withdrawal requests
  }

  /**
   * Listens to changes in filters and pagination to refetch data automatically.
   */
  private listenForDataChanges(): Observable<Goal[]> {
    return combineLatest([this.filters$, this.pagination$]).pipe(
      tap(([filters, pagination]) => {
      console.log('listenForDataChanges triggered:', { filters, pagination });
    }),
      // Use switchMap to cancel previous requests and switch to a new one
      switchMap(([filters, pagination]) => {
        this.isLoadingSubject.next(true);
        const params = this.buildHttpParams(filters, pagination);

        return this.http.get<ApiResponse<PaginatedGoalsResponse>>(`${this.apiUrl}`, { withCredentials : true, params }).pipe(
          map((response) => response.data),
          tap((response) => {
            // Update state with the new data from the API
            this.goalsSubject.next(response.data);
             const current = this.paginationSubject.value;
              const next = response.pagination;
              // Compare relevant fields
              if (
                current.page !== next.page ||
                current.pageSize !== next.pageSize ||
                current.total !== next.total ||
                current.totalPages !== next.totalPages
              ) {
                this.paginationSubject.next(next);
              }
            this.isLoadingSubject.next(false);
          }),
          map((response) => response.data),
          catchError((error) => {
            console.error('Failed to fetch goals:', error);
            this.isLoadingSubject.next(false);
            // Return empty state on error
            this.goalsSubject.next([]);
            return of([]);
          })
        );
      })
    );
  }

  // --- 

  // --- Data Computation and Transformation Observables ---

  /**
   * Calculates high-level statistics based on the current set of goals.
   */
  getGoalStats(): Observable<GoalStats> {
    // This can be a separate API call or computed client-side
    // For this example, we assume a dedicated API endpoint for efficiency
    return this.http.get<ApiResponse<GoalStats>>(`${this.apiUrl}/stats`,{withCredentials:true}).pipe(
      map((response) => response.data),
      catchError((error) => {
        console.error('Failed to fetch goal stats:', error);
        // Return a zeroed-out stats object on error
        return of({
          totalGoals: 0,
          activeGoals: 0,
          completedGoals: 0,
          totalContributions: 0,
          pendingWithdrawals: 0,
          averageProgress: 0,
        });
      })
    );
  }

  /**
 * Fetches withdrawal requests from the backend and updates the observable.
 */
fetchWithdrawalRequests(): Observable<WithdrawalRequest[]> {
  return this.http.get<ApiResponse<WithdrawalRequest[]>>(`${this.apiUrl}/withdrawals`, { withCredentials: true }).pipe(
    map((response) => response.data),
    tap((requests) => {
      this.withdrawalRequestsSubject.next(requests);
    }),
    catchError((error) => {
      console.error('Failed to fetch withdrawal requests:', error);
      this.withdrawalRequestsSubject.next([]);
      return of([]);
    })
  );
}

  // --- Action Methods (Interacting with the Backend) ---

  /**
   * Fetches alerts from the backend.
   */


  /**
   * Approves a withdrawal request.
   * @param withdrawalId The ID of the withdrawal request.
   * @param adminNotes Optional notes from the administrator.
   */
  approveWithdrawal(withdrawalId: string, adminNotes?: string): Observable<boolean> {
    return this.http.post<void>(`${this.apiUrl}/withdrawals/${withdrawalId}/approve`, {},{withCredentials:true}).pipe(
      tap(() => this.refreshData()), // Refresh data on success
      map(() => true),
      catchError((error) => {
        console.error('Failed to approve withdrawal:', error);
        return of(false);
      })
    );
  }

  /**
   * Rejects a withdrawal request.
   * @param withdrawalId The ID of the withdrawal request.
   * @param adminNotes Optional notes explaining the rejection.
   */
  rejectWithdrawal(withdrawalId: string, adminNotes?: string): Observable<boolean> {
    return this.http.post<void>(`${this.apiUrl}/withdrawals/${withdrawalId}/reject`, {},{withCredentials:true}).pipe(
      tap(() => {
        this.refreshData();
        let requests : WithdrawalRequest[] = this.withdrawalRequestsSubject.value;
        requests = requests.filter(request => request.id !== withdrawalId);
        this.withdrawalRequestsSubject.next(requests);
      }), // Refresh data on success
      map(() => true),
      catchError((error) => {
        console.error('Failed to reject withdrawal:', error);
        return of(false);
      })
    );
  }



  /**
   * Manually triggers a refresh of the goal data by re-applying current filters.
   */
  public refreshData(): void {
    this.filtersSubject.next(this.filtersSubject.value);
    
  }

  // --- Filter and Pagination Management ---

  /**
   * Updates the filter state, which automatically triggers a data refetch.
   * @param filters A partial object of the filters to update.
   */
  updateFilters(filters: Partial<GoalFilters>): void {
    const currentFilters = this.filtersSubject.value;
    this.filtersSubject.next({ ...currentFilters, ...filters });
  }

  /**
   * Updates the pagination state (e.g., when the user changes page).
   * @param page The new page number.
   */
  goToPage(page: number): void {
    const currentPagination = this.paginationSubject.value;
    this.paginationSubject.next({ ...currentPagination, page });
  }

  // --- Helper Methods ---

  /**
   * Constructs HttpParams from filter and pagination states for the API request.
   */
  private buildHttpParams(filters: GoalFilters, pagination: PaginationInfo): HttpParams {
    let params = new HttpParams()
      .set('page', pagination.page.toString())
      .set('pageSize', pagination.pageSize.toString());

    if (filters.keyword) {
      params = params.set('keyword', filters.keyword);
    }
    if (filters.status && filters.status.length > 0) {
      params = params.set('status', filters.status.join(','));
    }
    if (filters.category) {
      params = params.set('category', filters.category);
    }
    if (filters.dateRange.from) {
      params = params.set('dateFrom', filters.dateRange.from);
    }
    if (filters.dateRange.to) {
      params = params.set('dateTo', filters.dateRange.to);
    }
    if (filters.minAmount !== null) {
      params = params.set('minAmount', filters.minAmount.toString());
    }
    if (filters.maxAmount !== null) {
      params = params.set('maxAmount', filters.maxAmount.toString());
    }

    return params;
  }

  // --- Client-side Utility Functions ---

  /**
   * Formats a number into a currency string (e.g., USD).
   */
  formatCurrency(amount: number): string {
    return new Intl.NumberFormat('en-US', {
      style: 'currency',
      currency: 'USD',
    }).format(amount);
  }

  /**
   * Formats a date string into a readable format.
   */
  formatDateTime(dateString: string): string {
    return new Intl.DateTimeFormat('en-US', {
      year: 'numeric',
      month: 'short',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit',
    }).format(new Date(dateString));
  }

  /**
   * Calculates the progress percentage of a goal.
   */
  calculateProgress(contributed: number, target: number): number {
    if (target === 0) return 0;
    return Math.round((contributed / target) * 100);
  }
}
