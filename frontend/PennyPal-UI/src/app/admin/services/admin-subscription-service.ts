import { Injectable } from "@angular/core"
import { type Observable, of, delay, throwError, BehaviorSubject, map, tap } from "rxjs"
import type {
  AdminSubscriptionPlan,
  CreatePlanRequest,
  PlanSubscriber,
} from "../models/admin-subscription-model"
import { environment } from "../../../environments/environment"
import { HttpClient } from "@angular/common/http"
import { ApiResponse } from "../../models/ApiResponse"

@Injectable({
  providedIn: "root",
})
export class AdminSubscriptionService {
  private apiUrl = environment.apiBaseUrl + '/api/private/admin/plans'
  private plansSubject = new BehaviorSubject<AdminSubscriptionPlan[]>([]);
  public plans$ = this.plansSubject.asObservable();

  constructor(private http : HttpClient){}

  getPlans(): Observable<AdminSubscriptionPlan[]> {
    this.http.get<ApiResponse<AdminSubscriptionPlan[]>>(this.apiUrl,{withCredentials : true}).pipe(
      map(res => res.data),
      tap(data => this.plansSubject.next(data))
    ).subscribe();
    return this.plans$;
  }


  createPlan(request: CreatePlanRequest):Observable<true>{
    return this.http.post<ApiResponse<AdminSubscriptionPlan>>(`${this.apiUrl}/add`,request,{withCredentials:true}).pipe(
      map(res => res.data),
      tap(newPlan => {
        const current = this.plansSubject.value;
        this.plansSubject.next([newPlan, ...current]) 
      }),
      map(() => true)
    )
  }

  updatePlan(planId: string,request: CreatePlanRequest):Observable<boolean>{
    return this.http.put<ApiResponse<AdminSubscriptionPlan>>(`${this.apiUrl}/${planId}/edit`,request,{withCredentials : true}).pipe(
      map(res => res.data),
      tap(editedPlan => {
      const current = this.plansSubject.value;
      const updated = current.map(plan =>
        plan.id === editedPlan.id ? editedPlan : plan
      );
      this.plansSubject.next(updated);
      }),
      map(() => true)
    )
  }

  deletePlan(id: string):Observable<true>{
    return this.http.delete<ApiResponse<AdminSubscriptionPlan>>(`${this.apiUrl}/${id}/delete`,{withCredentials:true}).pipe(
      map(res => res.data),
      tap(editedPlan => {
      const current = this.plansSubject.value;
      const updated = current.map(plan =>
        plan.id === editedPlan.id ? editedPlan : plan
      );
      this.plansSubject.next(updated);
      }),
      map(() => true)
    )
  }

  // getPlanSubscribers(planId: string): Observable<PlanSubscriber[]> {
  //   // In real implementation, filter by planId
  //   return of(this.mockSubscribers).pipe(delay(600))
  // }
}
