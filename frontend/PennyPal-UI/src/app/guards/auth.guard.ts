// src/app/guards/auth.guard.ts
import { Injectable } from '@angular/core';
import { CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot, GuardResult, MaybeAsync } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from '../public/auth/services/auth.service';
import { map, Observable, of, tap } from 'rxjs';
import { Roles } from '../models/Roles';

// @Injectable({
//   providedIn: 'root'
// })
// export class AuthGuard implements CanActivate {
//   constructor(
//     private authService: AuthService,
//     private router: Router,
//     private toastr: ToastrService
//   ) {}

//   // canActivate(
//   //   route: ActivatedRouteSnapshot,
//   //   state: RouterStateSnapshot
//   // ): Observable<boolean> {
//   //   return this.authService.isAuthenticated().pipe(
//   //     map((isAuthenticated) => {
//   //       if (isAuthenticated && state.url.includes('/login')) {
//   //         this.router.navigate(['/home']);
//   //         return false;
//   //       }
//   //       if (!isAuthenticated && !state.url.includes('/login')) {
//   //         this.toastr.error('Please log in to access this page', undefined, { timeOut: 3000 });
//   //         this.router.navigate(['/login']);
//   //         return false;
//   //       }
//   //       if (!isAuthenticated) {
//   //         return false;
//   //       }
//   //       const requiredRoles = Array.isArray(route.data['roles']) ? route.data['roles'] as string[] : [];
//   //       if (requiredRoles.length > 0 && !requiredRoles.some((role) => this.authService.hasRole(role))) {
//   //         this.toastr.error('You do not have permission to access this page', undefined, { timeOut: 3000 });
//   //         this.router.navigate(['/home']);
//   //         return false;
//   //       }
//   //       return true;
//   //     })
//   //   );
//   // }
// }

@Injectable({
  providedIn : 'root'
})
export class AuthGuard implements CanActivate{

  constructor( private authService : AuthService,
    private toastr : ToastrService,
    private router : Router
  ){}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> {
      return this.authService.isAuthenticated().pipe(
        map((isAuthenticated) => {
          if(isAuthenticated && state.url.includes('/login')){
            this.toastr.info("You are already logged in.");
            return false;
          }
          if(!isAuthenticated && !state.url.includes("/login")){
            this.toastr.error("Please log in to access this page",'UN_AUTHORIZED',{timeOut :3000});
            this.router.navigate(['/home']);
            return false;
          }
          if(!isAuthenticated)
            return false;

          const requiredRoles = Array.isArray(route.data['roles']) ? route.data['roles'] as Roles[] : [];          
          if(requiredRoles.length > 0 && !requiredRoles.some(role => this.authService.hasRole(role))){
            this.toastr.error("You do not have permission to access this page.",'UN_AUTHORIZED',{timeOut :3000});
            return false;
          }
          return true;
        })
      )
  }

} 