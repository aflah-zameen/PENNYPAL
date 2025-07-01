import { BehaviorSubject, catchError, map, Observable, of, Subject, tap, throwError } from "rxjs";
import { IncomeModel } from "../models/income.model";
import { environment } from "../../../environments/environment";
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { ApiResponse } from "../../models/ApiResponse";
import { AddIncomeModel } from "../models/add-income.model";
import { Injectable } from "@angular/core";

@Injectable({
    providedIn : 'root'
})
export class UserIncomeService{
    private apiURL = `${environment.apiBaseUrl}/api/user`
    private incomeSubject = new BehaviorSubject<IncomeModel[]|null>(null) 
    private incomeAddedSubject = new Subject<void>();

    incomes$ =this.incomeSubject.asObservable();
    incomeAdded$ = this.incomeAddedSubject.asObservable();

    constructor(private https : HttpClient){}

    addIncome(income : AddIncomeModel):Observable<IncomeModel>{
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