import { Injectable } from "@angular/core"
import { type Observable, of } from "rxjs"
import { delay, map } from "rxjs/operators"
import type {
  CoinBalance,
  CoinTransaction,
  PaymentMethod,
  RedemptionRequest,
  RedemptionHistory,
} from "../models/reward-coin-model"
import { environment } from "../../../environments/environment";
import { HttpClient } from "@angular/common/http";
import { ApiResponse } from "../../models/ApiResponse";

@Injectable({
  providedIn: "root",
})
export class CoinService {
 private readonly apiURL = `${environment.apiBaseUrl}/api/private/user/coins`;

  constructor(private http: HttpClient) {}

  getCoinBalance(): Observable<CoinBalance> {
    return this.http
      .get<ApiResponse<CoinBalance>>(`${this.apiURL}/balance`, {
        withCredentials: true,
      })
      .pipe(map((res) => res.data));
  }

//   getCoinTransactions(): Observable<CoinTransaction[]> {
//     return this.http
//       .get<ApiResponse<CoinTransaction[]>>(`${this.apiURL}/transactions`, {
//         withCredentials: true,
//       })
//       .pipe(map((res) => res.data));
//   }

//   getPaymentMethods(): Observable<PaymentMethod[]> {
//     return this.http
//       .get<ApiResponse<PaymentMethod[]>>(`${this.apiURL}/payment-methods`, {
//         withCredentials: true,
//       })
//       .pipe(map((res) => res.data));
//   }

  getRedemptionHistory(): Observable<RedemptionHistory[]> {
    return this.http
      .get<ApiResponse<RedemptionHistory[]>>(`${this.apiURL}/redemptions`, {
        withCredentials: true,
      })
      .pipe(map((res) => res.data));
  }

  redeemCoins(request: RedemptionRequest): Observable<RedemptionHistory> {
    return this.http.post<ApiResponse<RedemptionHistory>>(
      `${this.apiURL}/redeem`,
      {
        coinAmount: request.coinAmount,
        realMoneyAmount: request.realMoneyAmount,
      },
      { withCredentials: true }
    ).pipe(
        map(res=>res.data)
    );
  }
}
