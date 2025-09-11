import { inject } from '@angular/core';
import {
  HttpInterceptorFn,
  HttpRequest,
  HttpHandlerFn,
  HttpErrorResponse,
  HttpEvent
} from '@angular/common/http';
import { Observable, BehaviorSubject, throwError } from 'rxjs';
import { catchError, switchMap, filter, take } from 'rxjs/operators';
import { AuthService } from '../public/auth/services/auth.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

let isRefreshing = false;
const refreshTokenSubject = new BehaviorSubject<boolean | null>(null);

export const authInterceptor: HttpInterceptorFn = (
  req: HttpRequest<any>,
  next: HttpHandlerFn
): Observable<HttpEvent<any>> => {
  const authService = inject(AuthService);
  const router = inject(Router);
  const toastr = inject(ToastrService);

  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      let errorMessage = 'An unknown error occurred.';
      const isAuthRoute = req.url.includes('/auth');

      if (error.status === 0) {
        errorMessage = 'Unable to connect to server. Please check your internet connection.';
        toastr.error(errorMessage);
        return throwError(() => new Error(errorMessage));
      }

      if (error.status === 401 && !req.url.includes('/auth/refresh-token') && !req.url.includes('/auth/login')) {
        return handle401Error(req, next, authService, router, toastr);
      }

      if ((error.status === 400 || error.status === 403) && isAuthRoute) {
        router.navigate(['/login']);
        toastr.error(error.error?.message || 'Authentication error', 'Error', { timeOut: 2000 });
        return throwError(() => error);
      }

      if(error.status === 403 && error.error.suspended){
        router.navigate(['/login']);
        toastr.error(error.error?.message || 'Account has Suspended by admin', 'Error', { timeOut: 2000 });
        return throwError(() => error);        
      }

      if(error.status === 402 && !error.error.hasActiveSubscription){
        router.navigate(['/user/plans']);
        return throwError(() => error);
      }

      // Handle other API errors (non-auth)
      if (typeof error.error === 'string') {
        errorMessage = error.error;
      } else if (error.error?.message) {
        errorMessage = error.error.message;
      }

      toastr.error(errorMessage);
      return throwError(() => ({
        message: errorMessage,
        status: error.status,
        errors: error.error?.errors || []
      }));
    })
  );
};

function handle401Error(
  req: HttpRequest<any>,
  next: HttpHandlerFn,
  authService: AuthService,
  router: Router,
  toastr: ToastrService
): Observable<HttpEvent<any>> {
  if (!isRefreshing) {
    isRefreshing = true;
    refreshTokenSubject.next(null);

    return authService.refreshToken().pipe(
      switchMap(() => {
        isRefreshing = false;
        refreshTokenSubject.next(true);
        return next(req);
      }),
      catchError((error) => {
        isRefreshing = false;
        router.navigate(['/login']);
        toastr.error('Session expired. Please log in again.');
        return throwError(() => error);
      })
    );
  }

  return refreshTokenSubject.pipe(
    filter((token) => token != null),
    take(1),
    switchMap(() => next(req))
  );
}

