import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { NewExpense } from "../models/expense-new-category";
import { environment } from "../../../environments/environment";
import { ApiResponse } from "../../models/ApiResponse";
import { BehaviorSubject, catchError, map, Observable, Subject, take, tap, throwError } from "rxjs";
import { ExpenseModel } from "../models/expense.model";

@Injectable({
    providedIn : 'root'
})
export class UserExpenseService{
    private apiURL = environment.apiBaseUrl+"/api/user"
    private addExpenseSubject = new Subject<void>();
    
    addExpense$ = this.addExpenseSubject.asObservable();

    constructor(private https : HttpClient){}
    addExpense(expense : NewExpense){
        return this.https.post<ApiResponse<ExpenseModel>>(this.apiURL+"/add-expense", expense,{withCredentials: true})
        .pipe(
            tap(()=>{
              this.addExpenseSubject.next();
            }),
            map(response => response.data),
            catchError(this.handleError)
        );
    }

    getExpenseCategories():Observable<ExpenseModel[]>{
        return this.https.get<ApiResponse<ExpenseModel[]>>(this.apiURL+"/fetch-expenses",{withCredentials: true})
        .pipe(
            tap(res => console.log(res)),
            map(response => response.data),
            catchError(this.handleError)
        );
    }

    editExpense(expense : ExpenseModel){
      return this.https.put<ApiResponse<string>>(`${this.apiURL}/expense/edit-expense`,expense,{withCredentials:true}).pipe(
        map(res => res.data),
        catchError(this.handleError)
      );
    }

    deleteExpense(expenseId : number){
      return this.https.delete<ApiResponse<string>>(`${this.apiURL}/expense/delete-expense`,{withCredentials:true,params:{id : expenseId}}).pipe(
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