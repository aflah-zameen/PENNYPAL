import { BehaviorSubject, catchError, map, Observable, of, Subject, tap, throwError } from "rxjs";
import { IncomeModel } from "../models/income.model";
import { environment } from "../../../environments/environment";
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { ApiResponse } from "../../models/ApiResponse";
import { AddIncomeModel } from "../models/add-income.model";
import { Injectable } from "@angular/core";
import { RecurringIncomeComponent } from "../pages/income-management/recurring-income/recurring-income.component";
import { RecurringIncomesModel } from "../models/recurring-income.model";

@Injectable({
    providedIn : 'root'
})
export class UserIncomeService{

  getRecurringIncomes(): Observable<RecurringIncomesModel> {
    return this.https.get<ApiResponse<RecurringIncomesModel>>(`${this.apiURL}/get-recurring-incomes-data`, { withCredentials: true }).pipe(
      map((response) => response.data),
      catchError(this.handleError)
    );
  }
    private apiURL = `${environment.apiBaseUrl}/api/user`
    private incomeSubject = new BehaviorSubject<IncomeModel[]|null>(null) 
    private incomeAddedSubject = new Subject<void>();

    incomes$ =this.incomeSubject.asObservable();
    incomeAdded$ = this.incomeAddedSubject.asObservable();

    constructor(private https : HttpClient){}

    addIncome(income : IncomeModel):Observable<IncomeModel>{
        return this.https.post<ApiResponse<IncomeModel>>(`${this.apiURL}/add-income`,income,{withCredentials:true}).pipe(
            map(res => res.data),
            tap(()=>{
              this.incomeAddedSubject.next();
            }),
            catchError(this.handleError)
        )
    }

    getTotalIncome(){
      return this.https.get<ApiResponse<number>>(`${this.apiURL}/total-income`,{withCredentials:true}).pipe(
        map(res => res.data),
        catchError(this.handleError)
      )
    }

    getAllIncomes(){
      return this.https.get<ApiResponse<IncomeModel[]>>(`${this.apiURL}/fetch-incomes`,{withCredentials:true})
      .pipe(
        map(res => res.data),
        catchError(this.handleError)
      );
    }

    getRecentIncomes(size : number): Observable<IncomeModel[]> {
      return this.https.get<ApiResponse<IncomeModel[]>>(`${this.apiURL}/recent-incomes`, { withCredentials: true,params:{size : size} }).pipe(
        map((response) => response.data),      
      );
    }

    toggleRecurringIncome(incomeId: number): Observable<IncomeModel> {
      return this.https.patch<ApiResponse<IncomeModel>>(`${this.apiURL}/toggle-recurring-income/${incomeId}`, {}, { withCredentials: true }).pipe(
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