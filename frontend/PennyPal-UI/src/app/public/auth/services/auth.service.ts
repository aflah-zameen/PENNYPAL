import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject, catchError, map, Observable, of, tap, throwError } from "rxjs";
import { environment } from "../../../../environments/environment";
import { LoginRequest, LoginResponse, SignupRequest, SignupResponse } from "../models/auth.model";
import { Router } from "@angular/router";
import { User } from "../../../models/User";
import { Roles } from "../../../models/Roles";
import { UserResponse } from "../../../models/UserResponse";
import { UserMapper } from "../../../mapper/UserMapper";
import { ApiResponse } from "../../../models/ApiResponse";
import { PresenceService } from "../../../user/services/websocket/presence-service";
import { NotificationService } from "../../../external-service/notification.service";
import { WebsocketService } from "../../../external-service/websocket.service";



@Injectable({
    providedIn : 'root'
})
export class AuthService{
  //   private apiURL = `${environment.apiBaseUrl}/api`;
  //   private userSubject = new BehaviorSubject<any>(null);
  //   user$ = this.userSubject.asObservable();

  //   constructor(private http : HttpClient,private router : Router,private toastr  : ToastrService){}

  //   sendOtp(email : string){
  //       return this.http.post(`${this.apiURL}/auth/sent-otp`,null,{params : {
  //           "email" : email
  //       }})
  //   }

  //   signup(user : SignupRequest):Observable<any>{
  //       user.role = "USER";
  //       return this.http.post<any>(`${this.apiURL}/auth/signup`,user)
  //           .pipe(
  //               catchError(this.handleError)
  //           )
  //   }

  //   login(credentials : LoginRequest){
  //       this.http.post<LoginResponse>(`${this.apiURL}/auth/login`,credentials,{withCredentials : true}).pipe(
  //           catchError(this.handleError)
  //       ).subscribe({
  //     next: (response: any) => {
  //       console.log(response);
  //       this.userSubject.next(response.data);
  //       this.toastr.success(response.message || 'Login successful', 'Success');
  //       this.router.navigate(['/home']);
  //     },
  //     error: (err) => {
  //       this.toastr.error(err.message || 'Login failed. Please try again.', 'Error');
  //     },
  //   });
  //   }

  //   adminLogin(credentials : LoginRequest):Observable<LoginResponse>{
  //       return this.http.post<LoginResponse>(`${this.apiURL}/auth/admin-login`,credentials,{withCredentials:true}).pipe(
  //           catchError(this.handleError)
  //       )
  //   }

  //   updatePassword(password : string,email : string){
  //       return this.http.patch(`${this.apiURL}/auth/update-password`,null,{params : {password : password,email :email }}).pipe(
  //           catchError(this.handleError)
  //       );
  //   }

  //   verifyOtp(otpData : {email : string , otp :string}, isPasswordReset : boolean,email:string): Observable<any> {
  //   return this.http
  //     .post<any>(`${this.apiURL}/auth/verify-otp`, otpData)
  //     .pipe(
  //       tap(()=>{
  //           if (!isPasswordReset) {
  //           this.loadUserFromToken();
  //           this.router.navigate(['/login']);
  //           this.toastr.info("Registration successful. Please login with your credentials");
  //         } else {
  //           this.router.navigate(['/set-new-password'], { queryParams: { email } });
  //         }
  //       }),
  //       catchError(this.handleError

  //       ));
  // }

  // refreshToken(): Observable<any> {
  //   return this.http
  //     .post(`${this.apiURL}/auth/refresh-token`, {}, { withCredentials: true })
  //     .pipe(
  //       tap(() => this.loadUserFromToken()),
  //       catchError((error) => {
  //         this.logout();
  //         return throwError(() => error);
  //       })
  //     );
  // }

  // logout(): void {
  //   this.http
  //     .post(`${this.apiURL}/auth/logout`, {}, { withCredentials: true })
  //     .subscribe({
  //       next: () => {
  //         this.userSubject.next(null);
  //         this.router.navigate(['/login']);
  //       },
  //       error: () => {
  //         this.userSubject.next(null);
  //         this.router.navigate(['/login']);
  //       }
  //     });
  // }

  // isAuthenticated(): Observable<boolean> {
  //   if(this.userSubject.value)
  //     return of(true)
  //   else{
  //     return this.loadUserFromToken().pipe(
  //       map(()=> !!this.userSubject.value),
  //       catchError(()=> of(false))
  //     );
  //   }
  // }

  // hasRole(role: string): boolean {
  //   const user = this.userSubject.value;
  //   return user ? user.roles.includes(role.toUpperCase()) : false;
  // }

  // private loadUserFromToken(): Observable<any> {
  //   return this.http
  //     .get<any>(`${this.apiURL}/auth/authenticate`,{withCredentials:true})
  //     .pipe(
  //       tap((user) => this.userSubject.next(user)),
  //       catchError((err) => {
  //         this.userSubject.next(null);
  //         return throwError(() => err);
  //       })
  //     );
  // }

  //   private handleError(error : HttpErrorResponse):Observable<never>{
  //       let errorMessage = 'An unknown error occured!';
  //       if(error.error instanceof ErrorEvent){
  //           errorMessage = `Error : ${error.error.message}`;
  //       }
  //       else{
  //           errorMessage = `Error code : ${error.status}\nMessage : ${error.message}`;
  //           if(error.error && error.error.message){
  //               errorMessage = error.error.message;
  //           }
  //       }
  //       console.error(errorMessage);
  //       return throwError(()=>new Error(errorMessage));
  //   }
  private apiURL : string;
  private userSubject : BehaviorSubject<User|null>;
  otpTimerSubject : BehaviorSubject<Date | null>;
  user$ : Observable<User | null>;


  constructor(private https : HttpClient,
    private router : Router,
    private userMapper : UserMapper,
    private presenceService: PresenceService,
  ){
    this.apiURL = `${environment.apiBaseUrl}/api/public/auth`;
    this.userSubject = new BehaviorSubject<User|null>(null);
    this.otpTimerSubject = new BehaviorSubject<Date | null>(null);
    this.user$ = this.userSubject.asObservable();
  }

  //check user authentication
  isAuthenticated():Observable<boolean>{
    if(this.userSubject.value)
      return of(true);
    else{
      return this.loadUserFromToken().pipe(
        map((user) => !!user),
        catchError(this.handleError)
      );
    }

  }

  //load user data using token
  //cache the data to userSubject
  loadUserFromToken(): Observable<User>{
    return this.https.get<ApiResponse<UserResponse>>(`${this.apiURL}/authenticate`,{withCredentials:true})
    .pipe(
      map(res => this.userMapper.toEntity(res.data)),
      tap(user => {
        this.userSubject.next(user);
      }),
      catchError((err) => {
        this.userSubject.next(null);
        return throwError(() => err);
      })
    )
  }


  //verify the role of user
  hasRole(role : Roles) : boolean {
      return this.userSubject.value ? this.userSubject.value.roles.has(role) : false;
  }

  //user login
  login(loginData : LoginRequest):Observable<User>{
    return this.https.post<ApiResponse<UserResponse>>(`${this.apiURL}/login`,loginData,{withCredentials : true})
    .pipe(
      tap(res => {
        sessionStorage.setItem('accessToken', res.data.accessToken || '');
      }),
      map((res) => this.userMapper.toEntity(res.data)),
      tap(user =>{
        this.userSubject.next(user);
        // this.presenceService.connect(user.id); // Connect to presence service
      }),
      catchError((this.handleError))
    )
  }

  //register user
  signup(signupData : SignupRequest):Observable<{id : string, email : string , expiry: string}>{
    const formData = new FormData();
    formData.append('name',signupData.name);
    formData.append('email',signupData.email);
    formData.append('phone',signupData.phone+"");
    formData.append('password',signupData.password);
    formData.append('profilePicture',signupData.profilePicture);
    // formData.append('profilePicture',signupData.profilePicture)
    return this.https.post<ApiResponse<{id : string, email : string , expiry: string}>>(`${this.apiURL}/user-register`,formData)
    .pipe(
      map(res => res.data),
      catchError((this.handleError))
    );
  }

  //logout
  logout():Observable<string>{
    return this.https.post<ApiResponse<string>>(`${this.apiURL}/logout`,null,{withCredentials:true})
    .pipe(
      map(res => res.message),
      tap(() => {
        this.userSubject.next(null);
        this.presenceService.close(); // Close presence service connection
      }),
      catchError(this.handleError)
    );
  }

  //refresh-token
  refreshToken():Observable<string>{
    return this.https.post<ApiResponse<string>>(`${this.apiURL}/refresh-token`,null,{withCredentials:true})
    .pipe(
      map(res => res.message),
      catchError(this.handleError)
    );
  }

  //update password
  updatePassword(password :string , email : string):Observable<string>{
    return this.https.patch<ApiResponse<string>>(`${this.apiURL}/update-password`,null,{params:{email : email,password : password}})
    .pipe(
      map(res => res.message),
      catchError(this.handleError)
    );
  }

  //sent-otp
  sentOtp(email : string):Observable<Date>{
    return this.https.post<ApiResponse<{expiresAt : string}>>(`${this.apiURL}/sent-otp`,null,{params : {email : email}})
    .pipe(
      map(res => new Date(res.data.expiresAt)),
      catchError(this.handleError)
    )
  }

  //verify-otp
  verifyOtp(email : string , otp : string):Observable<string>{
    return this.https.post<ApiResponse<null>>(`${this.apiURL}/verify-otp`,{email : email,otp : otp},{withCredentials:true})
    .pipe(
      map(res => res.message),
      catchError(this.handleError)
    )
  }

  //resend-otp
  resendOtp(email : string):Observable<Date>{
    return this.https.post<ApiResponse<{expiresAt :string}>>(`${this.apiURL}/resend-otp`,{email : email})
    .pipe(
      map(res => new Date(res.data.expiresAt)),
      catchError(this.handleError)
    )
  }

  //clear user
  clearUser(){
    this.userSubject.next(null);
  }

  //update-user
  updateUser(user : User | null){
    if(user){
      this.userSubject.next(user);
    }
  }

  //check-email uniqueness

  checkEmailAvailability(email : string):Observable<boolean>{
    return this.https.get<ApiResponse<boolean>>(`${this.apiURL}/check-email`,{params :{email : email}})
    .pipe(
      map((res) => res.data),
      // catchError(this.handleError)
    );
  }


  //Initial authentication checking



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