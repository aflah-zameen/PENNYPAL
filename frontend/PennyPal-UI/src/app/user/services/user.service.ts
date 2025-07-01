import { Injectable } from "@angular/core";
import { catchError, map, Observable, ObservableInput, throwError } from "rxjs";
import { User } from "../../models/User";
import { environment } from "../../../environments/environment";
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { ApiResponse } from "../../models/ApiResponse";

@Injectable({
    providedIn : 'root'
})
export class UserService{

  private apiURL :string;

    constructor(private https : HttpClient){
      this.apiURL = environment.apiBaseUrl+"/api/user"
    }
  
    //update-user
  updateUser(userPartial : Partial<User>,profilePicture : File | null):Observable<User>{
    const updateProfileFormData : FormData = new FormData();
    if(userPartial.name)
      updateProfileFormData.append('name',userPartial.name);
    if(userPartial.email)
      updateProfileFormData.append('email',userPartial.email);
    if(userPartial.phone)
      updateProfileFormData.append('phone',userPartial.phone.toString());
    if(profilePicture)
      updateProfileFormData.append('profilePicture',profilePicture);

    return this.https.patch<ApiResponse<User>>(`${this.apiURL}/update-user`,updateProfileFormData,{withCredentials:true})
    .pipe(
        map((res) => res.data)
    ,
    catchError(this.handleError));
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