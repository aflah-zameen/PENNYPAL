import { BehaviorSubject, catchError, map, Observable, of, Subject, tap, throwError } from "rxjs";
import { environment } from "../../../environments/environment";
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { ApiResponse } from "../../models/ApiResponse";
import { Injectable } from "@angular/core";
import { RecurringIncomesModel } from "../models/recurring-income.model";
import { IncomeRequestModel, IncomeResponseModel, RecentIncomeTransaction } from "../models/income.model";
import { AllPendingIncomesSummary, IncomeSummary, PendingIncomesModel, PendingIncomeSummary } from "../models/income-summary-model";

@Injectable({
    providedIn : 'root'
})
export class UserIncomeService{
    private apiURL = `${environment.apiBaseUrl}/api/user`
    private incomeSubject = new BehaviorSubject<IncomeResponseModel[]|null>(null)
    private recurringIncomeSubject = new BehaviorSubject<RecurringIncomesModel | null>(null);
    private recentIncomeTransactionSubject = new BehaviorSubject<RecentIncomeTransaction[]|null>(null); 
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
      catchError(this.handleError)
    );
  }

    addIncome(income : IncomeRequestModel):Observable<IncomeResponseModel>{
        return this.https.post<ApiResponse<IncomeResponseModel>>(`${this.apiURL}/add-income`,income,{withCredentials:true}).pipe(
            map(res => res.data),
            tap(()=>{
              this.incomeAddedSubject.next();
            }),
            catchError(this.handleError)
        )
    }

    getIncomeSummary():Observable<IncomeSummary>{
      return this.https.get<ApiResponse<IncomeSummary>>(`${this.apiURL}/income/summary`,{withCredentials:true}).pipe(
        map(res => res.data),
        tap(data => console.log(data)),
        catchError(this.handleError)
      )
    }

    getAllIncomes(){
      return this.https.get<ApiResponse<IncomeResponseModel[]>>(`${this.apiURL}/fetch-incomes`,{withCredentials:true})
      .pipe(
        map(res => res.data),
        catchError(this.handleError)
      );
    }

    getRecentIncomeTransactions(size : number): Observable<RecentIncomeTransaction[]> {
      return this.https.get<ApiResponse<RecentIncomeTransaction[]>>(`${this.apiURL}/income/recent-transactions`, { withCredentials: true,params:{size : size} }).pipe(
        map((response) => response.data),
        tap((data)=> this.recentIncomeTransactionSubject.next(data)),
        catchError(this.handleError)      
      );
    }

    toggleRecurringIncome(incomeId: number): Observable<IncomeResponseModel> {
      return this.https.patch<ApiResponse<IncomeResponseModel>>(`${this.apiURL}/toggle-recurring-income/${incomeId}`, {}, { withCredentials: true }).pipe(
        map((response) => response.data),
        catchError(this.handleError)
      );
    }

    deleteRecurringIncome(incomeId: number): Observable<void> {
      return this.https.delete<ApiResponse<void>>(`${this.apiURL}/delete-recurring-income/${incomeId}`, { withCredentials: true }).pipe(
        map((response) => response.data),
        catchError(this.handleError)
      );
    }

    getAllPendingIncomesSummary():Observable<AllPendingIncomesSummary>{
      return this.https.get<ApiResponse<AllPendingIncomesSummary>>(`${this.apiURL}/income/all-pending-incomes-summary`,{withCredentials:true}).pipe(
        map(res=>res.data),
        tap((data) => {
          console.log(data);
          this.allPendingIncomeSummary.next(data)
        }),
        catchError(this.handleError)
      )
    }

    collectPendingIcomes(income : PendingIncomesModel):Observable<{message:string}>{
      return this.https.patch<{message : string}>(`${this.apiURL}/income/collect-pending`,{incomeId : income.incomeId,incomeDate : income.incomeDate},{withCredentials:true}).pipe(
        catchError(this.handleError)
      );
    }

    //handle all erros from the client or server
  private handleError(error : HttpErrorResponse):Observable<never>{
      let errorMessage : string = "An unknown error occured!";
      let validationErrors : string[] =[];
              
      if(error?.status ===0){
        errorMessage = 'Cannot connect to the server. Please try again later.';
      }
      else if(error.error instanceof ErrorEvent){
        //client-side or network error
        errorMessage = `Client Error : ${error.error}`;
      }
      else{
        if(error.status === 500){
          errorMessage = 'Server issues. Try again later';
        }else{
        const errorBody = error.error;
        
        if(errorBody){
          if(typeof errorBody === 'string'){
            errorMessage = errorBody;
          }else if(errorBody.message){
            errorMessage = errorBody.message;
          }
          if(errorBody.errors && Array.isArray(errorBody.errors)){
            validationErrors = errorBody.error;
          }
        }
      }
    }
    return throwError(()=>({
      message : errorMessage,
      status : error.status,
      errors : validationErrors
    }))
  }

}