import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "../../../environments/environment";
import { catchError, throwError } from "rxjs";

@Injectable({
    providedIn : 'root',
})
export class UserService{
    constructor(private https : HttpClient){}

    getSomething(){
        return this.https.get(`${environment.apiBaseUrl}/api/user/something`,{withCredentials:true}).pipe(
            catchError((err) => throwError(() => err.errorBody))
        );
    }
}