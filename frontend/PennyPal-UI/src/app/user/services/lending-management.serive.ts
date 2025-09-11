import { Injectable } from "@angular/core"
import { BehaviorSubject, forkJoin, type Observable, of } from "rxjs"
import { delay, finalize, map, tap } from "rxjs/operators"
import type { LendingRequest, Loan, LendingSummary, SentLentRequest, RequestApproveForm, RegisterCase, RepayTransactionResponse } from "../models/lend.model"
import { HttpClient } from "@angular/common/http";
import { environment } from "../../../environments/environment";
import { ApiResponse } from "../../models/ApiResponse";
import { Transaction, TransferRequest } from "../models/money-flow.model";
import { SummaryStats } from "../../admin/components/loan-summary/loan-summary.component";
import { HistoryFilters } from "../../admin/components/loan-listing/loan-listing.component";
import { AuthService } from "../../public/auth/services/auth.service";

@Injectable({
  providedIn: "root",
})
export class LendingService {


  private readonly apiURL = `${environment.apiBaseUrl}/api/private/user/lending`;
  private readonly adminApiURL =  `${environment.apiBaseUrl}/api/private/admin/lending`

  // --- Private BehaviorSubjects to hold the state ---
  private lendingSummarySubject = new BehaviorSubject<LendingSummary | null>(null);
  private lendingRequestSentSubject = new BehaviorSubject<LendingRequest[]>([]);
  private lendingRequestReceivedSubject = new BehaviorSubject<LendingRequest[]>([]);
  private loansToPaySubject = new BehaviorSubject<Loan[]>([]);
  private loansToCollectSubject = new BehaviorSubject<Loan[]>([]);
  private loadingSubject = new BehaviorSubject<boolean>(false);

  // --- Public Observables for components to subscribe to ---
  public lendingSummary$ = this.lendingSummarySubject.asObservable();
  public lendingRequestSent$ = this.lendingRequestSentSubject.asObservable();
  public lendingRequestReceived$ = this.lendingRequestReceivedSubject.asObservable();
  public loansToPay$ = this.loansToPaySubject.asObservable();
  public loansToCollect$ = this.loansToCollectSubject.asObservable();
  public loading$ = this.loadingSubject.asObservable();

  constructor(private http: HttpClient,private authService : AuthService) {
  }

  // --- DATA FETCHING METHODS ---

  public fetchAllData(){
    this.fetchLendingSummary().subscribe();
    this.fetchLendingRequestsSent().subscribe();
    this.fetchLendingRequestsReceived().subscribe();
    this.fetchLoansToCollect().subscribe();
    this.fetchLoansToPay().subscribe();
  }

  fetchLendingSummary(): Observable<LendingSummary> {
    return this.http.get<ApiResponse<LendingSummary>>(`${this.apiURL}/summary`, { withCredentials: true }).pipe(
      map(res => res.data),
      tap(data => this.lendingSummarySubject.next(data))
    );
  }

  fetchLendingRequestsSent(): Observable<LendingRequest[]> {
    return this.http.get<ApiResponse<LendingRequest[]>>(`${this.apiURL}/requests/sent`, { withCredentials: true }).pipe(
      map(res => res.data),
      tap(data => this.lendingRequestSentSubject.next(data))
    );
  }

  fetchLendingRequestsReceived(): Observable<LendingRequest[]> {
    return this.http.get<ApiResponse<LendingRequest[]>>(`${this.apiURL}/requests/received`, { withCredentials: true }).pipe(
      map(res => res.data),
      tap(data => this.lendingRequestReceivedSubject.next(data))
    );
  }

  fetchLoansToPay(): Observable<Loan[]> {
    return this.http.get<ApiResponse<Loan[]>>(`${this.apiURL}/loans/to-pay`, { withCredentials: true }).pipe(
      map(res => res.data),
      tap(data => this.loansToPaySubject.next(data))
    );
  }

  fetchLoansToCollect(): Observable<Loan[]> {
    return this.http.get<ApiResponse<Loan[]>>(`${this.apiURL}/loans/to-collect`, { withCredentials: true }).pipe(
      map(res => res.data),
      tap(data => this.loansToCollectSubject.next(data))
    );
  }

  sentMoneyRequest(form : SentLentRequest):Observable<LendingRequest>{
    return this.http.post<ApiResponse<LendingRequest>>(`${this.apiURL}/request`,form,{withCredentials:true}).pipe(
      map(res => res.data),
      tap(data => {
        const current = this.lendingRequestSentSubject.getValue();
        this.lendingRequestSentSubject.next([data, ...current]); 
      })
    );
  }
  
  refreshAllLendingData(): Observable<any> {
    this.loadingSubject.next(true);
    return forkJoin([
      this.fetchLendingSummary(),
      this.fetchLendingRequestsSent(),
      this.fetchLendingRequestsReceived(),
      this.fetchLoansToPay(),
      this.fetchLoansToCollect()
    ]).pipe(
      finalize(() => this.loadingSubject.next(false))
    );
  }

  // --- ACTION/MUTATION METHODS ---

  approveRequest(form: RequestApproveForm,requestId  : string): Observable<any> {
    this.loadingSubject.next(true);
    return this.http.post<any>(`${this.apiURL}/requests/${requestId}/approve`, form, { withCredentials: true }).pipe(
      tap(() => {
        this.fetchLendingRequestsReceived().subscribe();
        this.fetchLoansToPay().subscribe();
        this.fetchLendingSummary().subscribe();
      }),
      finalize(() => this.loadingSubject.next(false))
    );
  }

  rejectRequest(requestId: string): Observable<any> {
    this.loadingSubject.next(true);
    return this.http.post<any>(`${this.apiURL}/requests/${requestId}/reject`, {}, { withCredentials: true }).pipe(
      tap(() => {
        this.fetchLendingRequestsReceived().subscribe();
        this.fetchLendingSummary().subscribe();
      }),
      finalize(() => this.loadingSubject.next(false))
    );
  }

  cancelRequest(requestId: string): Observable<any> {
    this.loadingSubject.next(true);
    return this.http.post<any>(`${this.apiURL}/requests/${requestId}/cancel`, {}, { withCredentials: true }).pipe(
      tap(() => {
        this.fetchLendingRequestsSent().subscribe();
        this.fetchLendingSummary().subscribe();
      }),
      finalize(() => this.loadingSubject.next(false))
    );
  }

  makePayment(form : TransferRequest ,loanId : string):Observable<any>{
      return this.http.post<ApiResponse<RepayTransactionResponse>>(`${this.apiURL}/loan/${loanId}/repayment`,form,{withCredentials:true}).pipe(
        map(res => res.data),
        map(data => data.coins),
        tap(coins => {
          if(coins)
            this.authService.updateUserCoins(coins);
         this.fetchLoansToPay().subscribe(); 
        })
      );
  }

  // markAsPaid(loanId: string, amount: number): Observable<any> {
  //   this.loadingSubject.next(true);
  //   return this.http.post<any>(`${this.apiURL}/loans/${loanId}/pay`, { amount }, { withCredentials: true }).pipe(
  //     tap(() => {
  //       this.fetchLoansToCollect().subscribe();
  //       this.fetchLendingSummary().subscribe();
  //     }),
  //     finalize(() => this.loadingSubject.next(false))
  //   );
  // }

  sendReminder(loanId: string): Observable<any> {
    this.loadingSubject.next(true);
    return this.http.post<ApiResponse<Loan>>(`${this.apiURL}/loans/${loanId}/remind`, {}, { withCredentials: true }).pipe(
      map(res => res.data),
      tap(updatedLoan => {
        const currentLoans = this.loansToCollectSubject.value;
        const updatedList = currentLoans.map(loan =>
          loan.id === updatedLoan.id ? { ...loan, ...updatedLoan } : loan
        );
      this.loansToCollectSubject.next(updatedList);
      }),
      finalize(() => this.loadingSubject.next(false))
    );
  }

  fileCase(register: RegisterCase): Observable<any> {
    this.loadingSubject.next(true);
    return this.http.post<ApiResponse<Loan>>(`${this.apiURL}/loans/file-case`, register, { withCredentials: true }).pipe(
      map(res => res.data),
      tap(updatedLoan => {
        const currentLoans = this.loansToCollectSubject.value;
        const updatedList = currentLoans.map(loan =>
          loan.id === updatedLoan.id ? { ...loan, ...updatedLoan } : loan
        );
        this.loansToCollectSubject.next(updatedList);
      }),
      finalize(() => this.loadingSubject.next(false))
    );
  }

  // admin methods

  getSummaryStats(): Observable<SummaryStats> {
    return this.http.get<ApiResponse<SummaryStats>>(`${this.adminApiURL}/summary-stats`, {
      withCredentials: true
    }).pipe(
      map(res => res.data)
    );
  }

  getFilteredHistory(historyFilters: HistoryFilters): Observable<Loan[]> {
    return this.http.post<ApiResponse<Loan[]>>(`${this.adminApiURL}/loan-history/filter`, historyFilters, {
      withCredentials: true
    }).pipe(
      map(res => res.data)
    );
  }

  getLoanHistory(): Observable<Loan[]> {
    return this.http.get<ApiResponse<Loan[]>>(`${this.adminApiURL}/loan-history`, {
      withCredentials: true
    }).pipe(
      map(res => res.data)
    );
  }

  // --- HELPER METHODS ---

  formatCurrency(amount: number): string {
    return new Intl.NumberFormat("en-US", {
      style: "currency",
      currency: "USD",
    }).format(amount);
  }

  formatDate(date: Date | string): string {
    return new Intl.DateTimeFormat("en-US", {
      year: "numeric",
      month: "short",
      day: "numeric",
    }).format(new Date(date));
  }
}
