import { Injectable } from "@angular/core"
import { type Observable, of, delay, map } from "rxjs"
import type { SubscriptionPlan } from "../models/subscription-plan-model"
import { environment } from "../../../environments/environment";
import { HttpClient } from "@angular/common/http";
import { ApiResponse } from "../../models/ApiResponse";

@Injectable({
  providedIn: "root",
})
export class SubscriptionService {
  private readonly apiURL = `${environment.apiBaseUrl}/api/user/plans`;

  constructor(private http : HttpClient){}

  getSubscriptionPlans(): Observable<SubscriptionPlan[]> {
    return this.http.get<ApiResponse<SubscriptionPlan[]>>(this.apiURL,{withCredentials:true}).pipe(
      map(res => res.data)
    );
  }

  purchasePlan(planId: string,sessionId : string): Observable<any> {
    return this.http.post<any>(`${this.apiURL}/purchase`,{
      planId : planId,
      sessionId : sessionId
    },{withCredentials :true})
  }
}
