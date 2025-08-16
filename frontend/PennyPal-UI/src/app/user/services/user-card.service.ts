import { Injectable } from "@angular/core";
import { environment } from "../../../environments/environment";
import { map, Observable, Subject, tap } from "rxjs";
import { HttpClient } from "@angular/common/http";
import { CreditCard, CreditCardForm, SpendData, Transaction } from "../models/CreditCard.model";
import { ApiResponse } from "../../models/ApiResponse";
import { CardExpenseOverview, filterValue } from "../components/expense-overview/expense-overview.component";

@Injectable({
  providedIn: 'root'
})
export class UserCardService {

    private apiURL = `${environment.apiBaseUrl}/api/private/user`
    private reloadSubject = new Subject<void>();
    reload$ = this.reloadSubject.asObservable();
  constructor(private http : HttpClient) {}

  addCard(card: CreditCardForm): Observable<CreditCard> {
    return this.http.post<CreditCard>(`${this.apiURL}/add-card`, card,{withCredentials:true}).pipe(
      tap(() => {
        this.reloadSubject.next();
      })
    );
  }

  getCardSpendingOverview(cardId: string, range: string): Observable<SpendData> {
    return this.http.get<ApiResponse<SpendData>>(`${this.apiURL}/card-spending-chart`, { withCredentials: true,params:{cardId : cardId, range : range} }).pipe(
      map(res => res.data)
    );
  }

  getCardsSummary(): Observable<CreditCard[]> {
    return this.http.get<ApiResponse<CreditCard[]>>(`${this.apiURL}/fetch-cards-summary`, { withCredentials: true }).pipe(
      map((res) => res.data),
    );
  }

  removeCard(cardId: string): void {
    this.http.delete(`${this.apiURL}/remove-card`, { withCredentials: true, params: { cardId : cardId } }).subscribe(() => {
      this.reloadSubject.next();
    });
  }

  getCardExpenseOverview(cardId: string, filterValue: filterValue): Observable<CardExpenseOverview[]> {
    return this.http.get<ApiResponse<CardExpenseOverview[]>>(`${this.apiURL}/card-expense-overview`,{ withCredentials: true, params:{cardId : cardId, range : filterValue.range, fromDate : filterValue?.fromDate || '', toDate : filterValue?.toDate || ''} }).pipe(
      map(res => res.data)
    );
  }

  getCardRecentTransactions(cardId: string,size : number): Observable<Transaction[]> {
    return this.http.get<ApiResponse<Transaction[]>>(`${this.apiURL}/card-recent-transactions`, { withCredentials: true ,params:{cardId : cardId, size:size}}).pipe(
      map(res => res.data)
    );
  }
}