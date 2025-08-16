import { Injectable } from "@angular/core";
import { catchError, map, Observable, ObservableInput, throwError } from "rxjs";
import { User } from "../../models/User";
import { environment } from "../../../environments/environment";
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { ApiResponse } from "../../models/ApiResponse";
import { UserCategoryResponse } from "../models/user-category.model";
import { ContributionFormData } from "../models/contribution-form-date.model";

@Injectable({
    providedIn : 'root'
})
export class UserService{

  private apiURL :string;

    constructor(private https : HttpClient){
      this.apiURL = environment.apiBaseUrl+"/api/private/user"
    }
  
    //update-user
  updateUser(userPartial : Partial<User>,profilePicture : File | null):Observable<User>{
    const updateProfileFormData : FormData = new FormData();
    if(userPartial.userName)
      updateProfileFormData.append('name',userPartial.userName);
    if(userPartial.email)
      updateProfileFormData.append('email',userPartial.email);
    if(userPartial.phone)
      updateProfileFormData.append('phone',userPartial.phone.toString());
    if(profilePicture)
      updateProfileFormData.append('profilePicture',profilePicture);

    return this.https.patch<ApiResponse<User>>(`${this.apiURL}/update-user`,updateProfileFormData,{withCredentials:true})
    .pipe(
        map((res) => res.data)
      );
  }

  getCategories():Observable<UserCategoryResponse[]>{
    return this.https.get<ApiResponse<UserCategoryResponse[]>>(`${this.apiURL}/get-categories`,{withCredentials:true})
    .pipe(
      map((res) => res.data),
    );
  }



}