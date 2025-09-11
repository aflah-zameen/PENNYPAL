import { Injectable } from "@angular/core"
import { type Observable, of, delay, BehaviorSubject, map, tap } from "rxjs"
import type {
  RewardPolicy,
  CreateRewardPolicyRequest,
  UpdateRewardPolicyRequest,
  ActionTypeOption,
} from "../models/reward.models"
import { environment } from "../../../environments/environment";
import { HttpClient } from "@angular/common/http";
import { ApiResponse } from "../../models/ApiResponse";

@Injectable({
  providedIn: "root",
})
export class RewardPolicyService {
  private apiUrl = environment.apiBaseUrl + '/api/private/admin/rewards';
  private policiesSubject = new BehaviorSubject<RewardPolicy[]>([]);
  public policies$ = this.policiesSubject.asObservable();

  constructor(private http: HttpClient) {}

  getPolicies(): Observable<RewardPolicy[]> {
    this.http.get<ApiResponse<RewardPolicy[]>>(this.apiUrl, { withCredentials: true })
      .pipe(
        map(res => res.data),
        tap(data => this.policiesSubject.next(data))
      )
      .subscribe();

    return this.policies$;
  }

  createPolicy(request: CreateRewardPolicyRequest): Observable<boolean> {
    return this.http.post<ApiResponse<RewardPolicy>>(`${this.apiUrl}/add`, request, { withCredentials: true }).pipe(
      map(res => res.data),
      tap(newPolicy => {
        const current = this.policiesSubject.value;
        this.policiesSubject.next([newPolicy, ...current]);
      }),
      map(() => true)
    );
  }

  updatePolicy(id: string, request: UpdateRewardPolicyRequest): Observable<boolean> {
    return this.http.put<ApiResponse<RewardPolicy>>(`${this.apiUrl}/${id}/edit`, request, { withCredentials: true }).pipe(
      map(res => res.data),
      tap(updatedPolicy => {
        const current = this.policiesSubject.value;
        const updated = current.map(p => p.id === updatedPolicy.id ? updatedPolicy : p);
        this.policiesSubject.next(updated);
      }),
      map(() => true)
    );
  }

  deletePolicy(id: string): Observable<boolean> {
    return this.http.delete<ApiResponse<RewardPolicy>>(`${this.apiUrl}/${id}/delete`, { withCredentials: true }).pipe(
      map(res => res.data),
      tap(deletedPolicy => {
        const current = this.policiesSubject.value;
        const updated = current.map(p => p.id === deletedPolicy.id ? deletedPolicy : p);
        this.policiesSubject.next(updated);
      }),
      map(() => true)
    );
  }

  togglePolicyStatus(id: string,isActive :boolean): Observable<boolean> {
    return this.http.put<ApiResponse<RewardPolicy>>(`${this.apiUrl}/${id}/toggle-status`, {}, { withCredentials: true,params :{active : isActive} }).pipe(
      map(res => res.data),
      tap(updatedPolicy => {
        const current = this.policiesSubject.value;
        const updated = current.map(p => p.id === updatedPolicy.id ? updatedPolicy : p);
        this.policiesSubject.next(updated);
      }),
      map(() => true)
    );
  }
}
