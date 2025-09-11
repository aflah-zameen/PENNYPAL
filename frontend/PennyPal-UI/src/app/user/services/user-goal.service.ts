import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject, catchError, map, Observable, Subject, tap, throwError } from "rxjs";
import { Goal, GoalFormData, GoalStats } from "../models/goal.model";
import { environment } from "../../../environments/environment";
import { ApiResponse } from "../../models/ApiResponse";
import { ContributionFormData, ContributionResponse } from "../models/contribution-form-date.model";
import { AuthService } from "../../public/auth/services/auth.service";

@Injectable({
    providedIn : 'root'
})
export class UserGoalService{
    

    private apiURL = environment.apiBaseUrl+"/api/private/user"
    private addGoalSubject = new Subject<void>();
    allGoals = new BehaviorSubject<Goal[]>([]);
    goalStats = new BehaviorSubject<GoalStats|null>(null)
    changeCycle = new Subject<void>();


    addGoal$ = this.addGoalSubject.asObservable();
    
    constructor(private https : HttpClient,private authService : AuthService){}

    //Add new goal
    addNewGoal(data : GoalFormData):Observable<Goal>{
        return this.https.post<ApiResponse<Goal>>(`${this.apiURL}/goal/add-goal`,data,{withCredentials:true}).pipe(
            map(res => res.data),
            tap((goal)=>{
                this.allGoals.next([...this.allGoals.value,goal])
                this.changeCycle.next();
            }),
        )
    }

    //Get all incomes
    getAllIncomes():Observable<Goal[]>{
        return this.https.get<ApiResponse<Goal[]>>(`${this.apiURL}/goal/get-all-goals`,{withCredentials:true}).pipe(
            map(res => res.data),
            tap((goals)=> {
                this.allGoals.next(goals);
            }),
        )
    }

    // Add contributions
    addContribution(contribution : ContributionFormData){
      
    return this.https.post<ApiResponse<ContributionResponse>>(`${this.apiURL}/goal/add-contribution`,contribution,{withCredentials:true}).pipe(
      map(res => res.data),
      map(data => data.coins),
      tap(coins => {        
        if(coins)
          this.authService.updateUserCoins(coins);
      }),
      tap(()=> {
        this.changeCycle.next();
      }),
    )
    }

    // Edit goals
    editGoal(data : {id: string , data : GoalFormData}){
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
    deleteGoal(goalId : string):Observable<void>{
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
      )
    }
    // withdraw from goal
    withdrawFromGoal(id: string) {
      return this.https.post<ApiResponse<string>>(`${this.apiURL}/goal/withdraw-money`,{},{withCredentials:true,params:{goalId : id}}).pipe(
        tap(() => {
          this.changeCycle.next();
        })
      );
    }


}