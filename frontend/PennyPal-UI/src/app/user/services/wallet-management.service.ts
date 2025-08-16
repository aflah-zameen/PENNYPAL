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

  

//   private initializeMockData() {
//     const mockWalletBalance: WalletBalance = {
//       id: "wallet-001",
//       userId: "user-001",
//       currentBalance: 2847.5,
//       availableBalance: 2647.5,
//       pendingAmount: 200.0,
//       currency: "USD",
//       lastUpdated: new Date().toISOString(),
//     }

//     const mockTransactions: WalletTransaction[] = [
//       {
//         id: "txn-001",
//         walletId: "wallet-001",
//         type: "deposit",
//         amount: 500.0,
//         description: "Bank transfer deposit",
//         status: "completed",
//         timestamp: "2024-01-15T10:30:00Z",
//         reference: "DEP-001",
//         balanceAfter: 2847.5,
//         category: "Income",
//       },
//       {
//         id: "txn-002",
//         walletId: "wallet-001",
//         type: "payment",
//         amount: -85.5,
//         description: "Grocery shopping at Whole Foods",
//         status: "completed",
//         timestamp: "2024-01-14T15:45:00Z",
//         reference: "PAY-002",
//         balanceAfter: 2347.5,
//         category: "Food & Dining",
//       },
//       {
//         id: "txn-003",
//         walletId: "wallet-001",
//         type: "transfer_in",
//         amount: 250.0,
//         description: "Transfer from John Doe",
//         status: "completed",
//         timestamp: "2024-01-13T09:20:00Z",
//         reference: "TRF-003",
//         balanceAfter: 2433.0,
//         category: "Transfer",
//       },
//       {
//         id: "txn-004",
//         walletId: "wallet-001",
//         type: "withdrawal",
//         amount: -100.0,
//         description: "ATM withdrawal",
//         status: "pending",
//         timestamp: "2024-01-12T14:10:00Z",
//         reference: "WTH-004",
//         fee: 2.5,
//         balanceAfter: 2183.0,
//         category: "Cash",
//       },
//       {
//         id: "txn-005",
//         walletId: "wallet-001",
//         type: "refund",
//         amount: 45.99,
//         description: "Refund from Amazon order",
//         status: "completed",
//         timestamp: "2024-01-11T11:30:00Z",
//         reference: "REF-005",
//         balanceAfter: 2285.5,
//         category: "Refund",
//       },
//       {
//         id: "txn-006",
//         walletId: "wallet-001",
//         type: "payment",
//         amount: -1200.0,
//         description: "Monthly rent payment",
//         status: "completed",
//         timestamp: "2024-01-10T08:00:00Z",
//         reference: "PAY-006",
//         balanceAfter: 2239.51,
//         category: "Housing",
//       },
//       {
//         id: "txn-007",
//         walletId: "wallet-001",
//         type: "bonus",
//         amount: 150.0,
//         description: "Cashback bonus",
//         status: "completed",
//         timestamp: "2024-01-09T16:20:00Z",
//         reference: "BON-007",
//         balanceAfter: 3439.51,
//         category: "Bonus",
//       },
//       {
//         id: "txn-008",
//         walletId: "wallet-001",
//         type: "fee",
//         amount: -5.0,
//         description: "Monthly maintenance fee",
//         status: "completed",
//         timestamp: "2024-01-08T00:00:00Z",
//         reference: "FEE-008",
//         balanceAfter: 3289.51,
//         category: "Fee",
//       },
//     ]

//     const mockPaymentMethods: PaymentMethod[] = [
//   {
//     id: "pm-001",
//     type: "card",
//     holder: "John Doe",
//     balance: 2500,
//     cardNumber: "**** **** **** 4242",
//     name: "Visa ending in 4242",
//     isActive: true
//   },
//   {
//     id: "pm-002",
//     type: "bank",
//     holder: "John Doe",
//     balance: 5600,
//     cardNumber: "****1234",
//     name: "Chase Bank",
//     isActive: false,
//     icon: "🏦"
//   },
//   {
//     id: "pm-003",
//     type: "wallet",
//     holder: "John Doe",
//     balance: 150,
//     cardNumber: "",
//     name: "PayPal Wallet",
//     isActive: false,
//     icon: "💰"
//   },
//   {
//     id: "pm-004",
//     type: "wallet",
//     holder: "John Doe",
//     balance: 300,
//     cardNumber: "",
//     name: "Apple Pay Wallet",
//     isActive: false,
//     icon: "📱"
//   }
// ];


//     this.walletBalanceSubject.next(mockWalletBalance)
//     this.transactionsSubject.next(mockTransactions)
//     this.paymentMethodsSubject.next(mockPaymentMethods)
//   }

  addMoney(request: AddMoneyRequest): Observable<boolean> {
    return this.http.post<any>(`${this.apiURL}/wallet/add`,request,{withCredentials:true}).pipe(
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
