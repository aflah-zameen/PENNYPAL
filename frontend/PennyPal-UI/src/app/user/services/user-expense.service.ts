import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { NewExpense } from "../models/expense-new-category";
import { environment } from "../../../environments/environment";
import { ApiResponse } from "../../models/ApiResponse";
import { BehaviorSubject, catchError, map, Observable, Subject, take, tap, throwError } from "rxjs";
import { AddExpenseForm, ExpenseResponseModel, ExpenseSummary, PendingExpense, RecurringExpensesResponse } from "../models/expense.model";
import { Transaction } from "../models/transaction.model";

@Injectable({
    providedIn : 'root'
})
export class UserExpenseService{
    private apiURL = environment.apiBaseUrl+"/api/user"
    private addExpenseSubject = new Subject<void>();
    
    addExpense$ = this.addExpenseSubject.asObservable();

    constructor(private https : HttpClient){}

     formatCurrency(amount: number): string {
        return new Intl.NumberFormat('en-US', {
          style: 'currency',
          currency: 'USD',
          minimumFractionDigits: 0,
          maximumFractionDigits: 0
        }).format(amount);
      }

    addExpense(expense : AddExpenseForm){
        return this.https.post<ApiResponse<ExpenseResponseModel>>(this.apiURL+"/add-expense", expense,{withCredentials: true})
        .pipe(
            tap(()=>{
              this.addExpenseSubject.next();
            }),
            map(response => response.data),
        );
    }

    getExpenseCategories():Observable<ExpenseResponseModel[]>{
        return this.https.get<ApiResponse<ExpenseResponseModel[]>>(this.apiURL+"/fetch-expenses",{withCredentials: true})
        .pipe(
            tap(res => console.log(res)),
            map(response => response.data),
        );
    }

    editExpense(expense : ExpenseResponseModel){
      return this.https.put<ApiResponse<string>>(`${this.apiURL}/expense/edit-expense`,expense,{withCredentials:true}).pipe(
        map(res => res.data),
      );
    }

    deleteExpense(expenseId : number){
      return this.https.delete<ApiResponse<string>>(`${this.apiURL}/expense/delete-expense`,{withCredentials:true,params:{id : expenseId}}).pipe(
        map(res => res.data),
      );
    }

    getExpenseSummary():Observable<ExpenseSummary>{
        return this.https.get<ApiResponse<ExpenseSummary>>(`${this.apiURL}/expense/summary`,{withCredentials : true}).pipe(
          map(res => res.data),
          )
    }

    getRecurringExpensesWithSummary():Observable<RecurringExpensesResponse>{
        return this.https.get<ApiResponse<RecurringExpensesResponse>>(`${this.apiURL}/expense/recurring-expense-summary`,{withCredentials:true}).pipe(
          map(res => res.data),
        );
    }

    getAllPendingExpenseSummary():Observable<PendingExpense[]>{
      return this.https.get<ApiResponse<PendingExpense[]>>(`${this.apiURL}/expense/pending-expenses-summary`,{withCredentials:true}).pipe(
        map(res => res.data),
      );
    }

    getRecentExpenseTransaction():Observable<Transaction[]>{
      return this.https.get<ApiResponse<Transaction[]>>(`${this.apiURL}/expense/recent-transactions`).pipe(
        map(res => res.data),
      );
    }

    toggleRecurringExpense(expenseId : number){
      return this.https.patch<ApiResponse<void>>(`${this.apiURL}/expense/toggle-recurring-expense`,{},{withCredentials:true,params:{id : expenseId}}).pipe(
        map(res => res.data),
      );
    }

    deleteRecurringExpense(expenseId : number){
      return this.https.delete<ApiResponse<void>>(`${this.apiURL}/expense/delete-recurring-expense`,{withCredentials:true,params:{id : expenseId}}).pipe(
        map(res => res.data),
      )
    }
    
    payPendingExpense(expenseId : number){
      return this.https.post<ApiResponse<void>>(`${this.apiURL}/expense/pay-pending`,{},{withCredentials:true,params:{id : expenseId}}).pipe(
        map(res => res.data),
      );
    }

}