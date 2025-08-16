import { HttpClient, HttpErrorResponse, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { catchError, map, Observable, throwError } from "rxjs";
import { environment } from "../../../environments/environment";
import { PagedResultDTO } from "../../models/PagedResultDTO";
import { User } from "../../models/User";
import { ApiResponse } from "../../models/ApiResponse";
import { FiltersDTO } from "../../models/FiltersDTO";

@Injectable({
    providedIn : 'root'
})
export class UserManagementService{

    private apiURL = `${environment.apiBaseUrl}/api/private`;

    constructor(private http : HttpClient){}
    public fetchUsers(params: {
    page: number;
    size: number;
    keyword?: string;
  } & FiltersDTO):Observable<PagedResultDTO<User>>{
    let httpParams = new HttpParams()
      .set('page', params.page)
      .set('size', params.size);

    if (params.keyword) {
      httpParams = httpParams.set('keyword', params.keyword);
    }

    //check it is not null and add each fields
    if (params.status) httpParams = httpParams.set('status', params.status === 'ACTIVE' ? true : false);
    if (params.role) httpParams = httpParams.set('role', params.role);
    if(params.joinedAfter) httpParams = httpParams.set('joinedAfter',params.joinedAfter.toISOString());
    if(params.joinedBefore) httpParams = httpParams.set('joinedBefore',params.joinedBefore.toISOString());
    if (params.verified !== undefined) httpParams = httpParams.set('verified', String(params.verified));


        return this.http.get<ApiResponse<PagedResultDTO<User>>>(`${this.apiURL}/admin/fetch-filtered-users`, { withCredentials: true,params : httpParams })
      .pipe(
        map(res => res.data),
        catchError(this.handleError)
      );
    }

    public toggleUserActive(email: string, active: boolean): Observable<any> {
    return this.http
      .patch<any>(
        `${this.apiURL}/admin/users/${email}/toggle-active`,
        { active },
        { withCredentials: true }
      )
      .pipe(catchError(this.handleError));
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    let errorMessage = 'An unknown error occurred!';
    if (error.error instanceof ErrorEvent) {
      errorMessage = `Error: ${error.error.message}`;
    } else if (error.error && error.error.message) {
      errorMessage = error.error.message;
    } else {
      errorMessage = `Error code: ${error.status}\nMessage: ${error.message}`;
    }
    return throwError(() => new Error(errorMessage));
  }
}