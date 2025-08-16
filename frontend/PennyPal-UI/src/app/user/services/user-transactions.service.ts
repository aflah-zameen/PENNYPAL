import { map, Observable, Subject, tap } from "rxjs";
import { environment } from "../../../environments/environment";
import { PendingTransactionTotalSummary, RecurringTransactionRequest, RecurringTransactionResponse, RecurringTransactionSummary, Transaction, TransactionRequest, TransactionSummaryResponseDTO, TransactionType } from "../models/transaction.model";
import { ApiResponse } from "../../models/ApiResponse";
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { DashboardSumary, ExpenseData } from "../models/dashboard.model";

@Injectable({
    providedIn: 'root'
})
export class UserTransactionService{
    
    private apiURL = `${environment.apiBaseUrl}/api/private/user`
    private reloadSubject = new Subject<void>();
    reload$ = this.reloadSubject.asObservable();

    constructor(private https: HttpClient) {}
        
    addTransaction(transactionRequest : TransactionRequest): Observable<Transaction> {
        return this.https.post<ApiResponse<Transaction>>(`${this.apiURL}/add-transaction`, transactionRequest, { withCredentials: true }).pipe(
            map((response) => response.data),
            tap(() => this.reloadSubject.next())
        );
    }

    getRecentTransactions(transactionType : TransactionType ,size : number): Observable<Transaction[]> {
        return this.https.get<ApiResponse<Transaction[]>>(`${this.apiURL}/recent-transactions`, { withCredentials: true,params:{transactionType : transactionType ,size : size} }).pipe(
            map((response) => response.data)
        );
    }

    getTransactionSummary(transactionType : TransactionType): Observable<TransactionSummaryResponseDTO> {
        return this.https.get<ApiResponse<TransactionSummaryResponseDTO>>(`${this.apiURL}/transaction/summary`, { withCredentials: true, params: { transactionType: transactionType } }).pipe(
            map((response) => response.data)
        );
    }


    getRecurringTransactions(transactionType : TransactionType): Observable<RecurringTransactionSummary> {
            return this.https.get<ApiResponse<RecurringTransactionSummary>>(`${this.apiURL}/recurring-transaction-summary`, { withCredentials: true ,params:{transactionType : transactionType}}).pipe(
              map((response) => response.data)
            );
          }

    addRecurringTransaction(recurringTransaction: RecurringTransactionRequest): Observable<RecurringTransactionResponse> {
        return this.https.post<ApiResponse<RecurringTransactionResponse>>(`${this.apiURL}/add-recurring-transaction`, recurringTransaction, { withCredentials: true }).pipe(
            map((response) => response.data),
            tap(() => this.reloadSubject.next())
        );
    }

    editRecurringTransaction(recurringTransaction: RecurringTransactionRequest, recurringId: string): Observable<RecurringTransactionResponse> {
        return this.https.put<ApiResponse<RecurringTransactionResponse>>(`${this.apiURL}/edit-recurring-transaction`, recurringTransaction, { withCredentials: true ,params:{recurringId : recurringId}}).pipe(
            map((response) => response.data),
            tap(() => this.reloadSubject.next())
        );
    }

    deleteRecurringTransaction(recurringId: string): Observable<void> {
        return this.https.delete<ApiResponse<void>>(`${this.apiURL}/delete-recurring-transaction`, { withCredentials: true, params: { recurringId: recurringId } }).pipe(
            map(() => {}),
            tap(() => this.reloadSubject.next())
        );
    }

    toggleRecurringTransactionStatus(recurringId: string,): Observable<RecurringTransactionResponse> {
        return this.https.patch<ApiResponse<RecurringTransactionResponse>>(`${this.apiURL}/toggle-recurring-transaction-status`, {}, { withCredentials: true, params: { recurringId: recurringId} }).pipe(
            map((response) => response.data),
            tap(() => this.reloadSubject.next())
        );
    }

    getPendingTransactionSummary(transactionType: TransactionType): Observable<PendingTransactionTotalSummary> {
        return this.https.get<ApiResponse<PendingTransactionTotalSummary>>(`${this.apiURL}/recurring-transactions-pending`, { withCredentials: true, params: { transactionType: transactionType } }).pipe(
            map((response) => response.data)
        );
    }

    collectPendingTransaction(logId: string): Observable<void> {
        return this.https.patch<ApiResponse<void>>(`${this.apiURL}/collect-pending-transaction`, {}, { withCredentials: true, params: { logId: logId } }).pipe(
            map(() => {}),
            tap(() => this.reloadSubject.next())
        );
    }

    getDashboardSummary(): Observable<DashboardSumary> {
        return this.https.get<ApiResponse<DashboardSumary>>(`${this.apiURL}/dashboard-summary`, { withCredentials: true }).pipe(
            map((response) => response.data)
        );
    }   

    getIncomeExpenseChartData(): Observable<{ date: string; income: number; expense: number; }[]> {
      return this.https.get<ApiResponse<{ date: string; income: number; expense: number; }[]>>(`${this.apiURL}/income-expense-chart`, { withCredentials: true }).pipe(
          map((response) => response.data)
      );
    }

    getExpenseBreakdownData(): Observable<ExpenseData[]> {
      return this.https.get<ApiResponse<ExpenseData[]>>(`${this.apiURL}/expense-breakdown-chart`, { withCredentials: true }).pipe(
        map((response) => response.data)
      );
    }

}