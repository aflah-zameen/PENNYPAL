import { Injectable, OnInit } from "@angular/core"
import { BehaviorSubject, type Observable, map, of, delay, tap } from "rxjs"
import type {
  WalletBalance,
  WalletTransaction,
  AddMoneyRequest,
  WalletStats,
  TransactionFilters,
  TransactionType,
} from "../models/wallet.model"
import { PaymentMethod } from "../models/money-flow.model"
import { HttpClient } from "@angular/common/http"
import { environment } from "../../../environments/environment"
import { ApiResponse } from "../../models/ApiResponse"

@Injectable({
  providedIn: "root",
})
export class WalletService {

  private readonly apiURL = environment.apiBaseUrl+"/api/private/user"

  private walletBalanceSubject = new BehaviorSubject<WalletBalance | null>(null)
  private transactionsSubject = new BehaviorSubject<WalletTransaction[]>([])
  private paymentMethodsSubject = new BehaviorSubject<PaymentMethod[]>([])

  walletBalance$ = this.walletBalanceSubject.asObservable()
  transactions$ = this.transactionsSubject.asObservable()
  paymentMethods$ = this.paymentMethodsSubject.asObservable()

  constructor(private http : HttpClient) {
  }
  addMoney(request: AddMoneyRequest): Observable<boolean> {
    return this.http.post<unknown>(`${this.apiURL}/wallet/add`,request,{withCredentials:true}).pipe(
      map(res => true),
      tap(()=>{
        this.getWallet().subscribe();
      })
    )
  }

  getWallet():Observable<WalletBalance>{
    return this.http.get<ApiResponse<WalletBalance>>(`${this.apiURL}/wallet`,{withCredentials : true}).pipe(
        map(res => res.data),
        tap(data => this.walletBalanceSubject.next(data))
    );
  }



  getWalletStats(): Observable<WalletStats> {
    return this.http.get<ApiResponse<WalletStats>>(`${this.apiURL}/wallet/stats`,{withCredentials:true}).pipe(
      map(res => res.data)
    )
  }

  getWalletTransactions() : Observable<WalletTransaction[]>{
    return this.http.get<ApiResponse<WalletTransaction[]>>(`${this.apiURL}/wallet/transactions`,{withCredentials : true}).pipe(
      map(res => res.data),
      tap(data => this.transactionsSubject.next(data))
    )
  }


  // refreshWallet(): Observable<boolean> {
  //   // Simulate refresh
  //   return of(true).pipe(delay(1000))
  // }

  formatCurrency(amount: number): string {
    return new Intl.NumberFormat("en-US", {
      style: "currency",
      currency: "USD",
    }).format(amount)
  }

  formatDateTime(dateString: string): string {
    return new Intl.DateTimeFormat("en-US", {
      year: "numeric",
      month: "short",
      day: "numeric",
      hour: "2-digit",
      minute: "2-digit",
    }).format(new Date(dateString))
  }

}
