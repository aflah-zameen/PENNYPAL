import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject, catchError, map, Observable, Subject, tap, throwError } from "rxjs";
import { Goal, GoalFormData, GoalStats } from "../models/goal.model";
import { environment } from "../../../environments/environment";
import { ApiResponse } from "../../models/ApiResponse";
import { ContributionFormData } from "../models/contribution-form-date.model";

@Injectable({
    providedIn : 'root'
})
export class UserGoalService{

    private apiURL = environment.apiBaseUrl+"/api/user"
    private addGoalSubject = new Subject<void>();
    allGoals = new BehaviorSubject<Goal[]>([]);
    goalStats = new BehaviorSubject<GoalStats|null>(null)
    changeCycle = new Subject<void>();


    addGoal$ = this.addGoalSubject.asObservable();
    
    constructor(private https : HttpClient){}

    //Add new goal
    addNewGoal(data : GoalFormData):Observable<Goal>{
        return this.https.post<ApiResponse<Goal>>(`${this.apiURL}/goal/add-goal`,data,{withCredentials:true}).pipe(
            map(res => res.data),
            tap((goal)=>{
                this.allGoals.next([...this.allGoals.value,goal])
                this.changeCycle.next();
            }),
            catchError(this.handleError)
        )
    }

    //Get all incomes
    getAllIncomes():Observable<Goal[]>{
        return this.https.get<ApiResponse<Goal[]>>(`${this.apiURL}/goal/get-all-goals`,{withCredentials:true}).pipe(
            map(res => res.data),
            tap((goals)=> {
                this.allGoals.next(goals);
            }),
            catchError(this.handleError)
        )
    }

    // Add contributions
    addContribution(contribution : ContributionFormData){
      
    return this.https.post<ApiResponse<string>>(`${this.apiURL}/goal/add-contribution`,contribution,{withCredentials:true}).pipe(
      tap(()=> {
        this.changeCycle.next();
      }),
      catchError(this.handleError)
    )
    }

    // Edit goals
    editGoal(data : {id: number , data : GoalFormData}){
      const requestData = {
        goalId : data.id,
        title : data.data.title,
        targetAmount : data.data.targetAmount,
        categoryId  : data.data.categoryId,
        description : data.data.description,
        startDate : data.data.startDate,
        endDate : data.data.endDate
      }
      
      return this.https.put<ApiResponse<string>>(`${this.apiURL}/goal/edit-goal`,requestData,{withCredentials:true}).pipe(
        tap(()=>{
          this.changeCycle.next();
        })
      )
    }

    //Delete goals
    deleteGoal(goalId : number):Observable<void>{
      return this.https.delete<void>(`${this.apiURL}/goal/delete-goal`,{withCredentials : true,params:{id : goalId}}).pipe(
        tap(()=>{
          this.changeCycle.next();
        })
      )
    }

    // Goal summary stats
    goalSummary(){
      return this.https.get<ApiResponse<GoalStats>>(`${this.apiURL}/goal/summary`,{withCredentials:true}).pipe(
        map(res=> res.data),
        tap((stats) => this.goalStats.next(stats)),
        catchError(this.handleError)
      )
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