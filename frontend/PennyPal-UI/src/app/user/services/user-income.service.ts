import { BehaviorSubject, catchError, map, Observable, of, Subject, tap, throwError } from "rxjs";
import { environment } from "../../../environments/environment";
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { ApiResponse } from "../../models/ApiResponse";
import { Injectable } from "@angular/core";
import { RecurringIncomesModel } from "../models/recurring-income.model";
import { IncomeRequestModel, IncomeResponseModel } from "../models/income.model";
import { AllPendingIncomesSummary, IncomeSummary, PendingIncomesModel, PendingIncomeSummary } from "../models/income-summary-model";
import { Transaction } from "../models/transaction.model";

@Injectable({
    providedIn : 'root'
})
export class UserIncomeService{
    private apiURL = `${environment.apiBaseUrl}/api/user`
    private incomeSubject = new BehaviorSubject<IncomeResponseModel[]|null>(null)
    private recurringIncomeSubject = new BehaviorSubject<RecurringIncomesModel | null>(null);
    private recentIncomeTransactionSubject = new BehaviorSubject<Transaction[]|null>(null); 
    private allPendingIncomeSummary = new BehaviorSubject<AllPendingIncomesSummary|null>(null);
    private incomeAddedSubject = new Subject<void>();

    incomes$ =this.incomeSubject.asObservable();
    incomeAdded$ = this.incomeAddedSubject.asObservable();
    recentIncomeTransaction$ = this.recentIncomeTransactionSubject.asObservable();
    recurringIncome$ = this.recurringIncomeSubject.asObservable();
    allPendingIncomeSummary$ = this.allPendingIncomeSummary.asObservable();

    constructor(private https : HttpClient){}


    getRecurringIncomes(): Observable<RecurringIncomesModel> {
    return this.https.get<ApiResponse<RecurringIncomesModel>>(`${this.apiURL}/income/recurring-incomes`, { withCredentials: true }).pipe(
      map((response) => response.data),
      tap((data) => this.recurringIncomeSubject.next(data)),
    );
  }

    addIncome(income : IncomeRequestModel):Observable<IncomeResponseModel>{
        return this.https.post<ApiResponse<IncomeResponseModel>>(`${this.apiURL}/add-income`,income,{withCredentials:true}).pipe(
            map(res => res.data),
            tap(()=>{
              this.incomeAddedSubject.next();
            }),
        )
    }

    getIncomeSummary():Observable<IncomeSummary>{
      return this.https.get<ApiResponse<IncomeSummary>>(`${this.apiURL}/income/summary`,{withCredentials:true}).pipe(
        map(res => res.data),
        tap(data => console.log(data)),
      )
    }

    getAllIncomes(){
      return this.https.get<ApiResponse<IncomeResponseModel[]>>(`${this.apiURL}/fetch-incomes`,{withCredentials:true})
      .pipe(
        map(res => res.data),
      );
    }

    getRecentIncomeTransactions(size : number): Observable<Transaction[]> {
      return this.https.get<ApiResponse<Transaction[]>>(`${this.apiURL}/income/recent-transactions`, { withCredentials: true,params:{size : size} }).pipe(
        map((response) => response.data),
        tap((data)=> this.recentIncomeTransactionSubject.next(data)),
      );
    }

    toggleRecurringIncome(incomeId: number): Observable<IncomeResponseModel> {
      return this.https.patch<ApiResponse<IncomeResponseModel>>(`${this.apiURL}/toggle-recurring-income/${incomeId}`, {}, { withCredentials: true }).pipe(
        map((response) => response.data),
      );
    }

    deleteRecurringIncome(incomeId: number): Observable<void> {
      return this.https.delete<ApiResponse<void>>(`${this.apiURL}/delete-recurring-income/${incomeId}`, { withCredentials: true }).pipe(
        map((response) => response.data),
      );
    }

    getAllPendingIncomesSummary():Observable<AllPendingIncomesSummary>{
      return this.https.get<ApiResponse<AllPendingIncomesSummary>>(`${this.apiURL}/income/all-pending-incomes-summary`,{withCredentials:true}).pipe(
        map(res=>res.data),
        tap((data) => {
          console.log(data);
          this.allPendingIncomeSummary.next(data)
        }),
      )
    }

    collectPendingIcomes(income : PendingIncomesModel):Observable<{message:string}>{
      return this.https.patch<{message : string}>(`${this.apiURL}/income/collect-pending`,{incomeId : income.incomeId,incomeDate : income.incomeDate},{withCredentials:true}).pipe(
      );
    }

  
}