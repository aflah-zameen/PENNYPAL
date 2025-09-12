import { Routes } from '@angular/router';
import { AuthGuard } from './guards/auth.guard';

export const routes: Routes = [{
    path : '',
    loadChildren : () => import('./public/public.routes').then( m=> m.publicRoutes)
},{
    path : 'user',
    loadChildren : () => import('./user/user-routes').then(m=>m.userRoutes),
    canActivate : [AuthGuard],
    data : {roles :['USER']}
},{
    path : 'admin',
    loadChildren : () => import('./admin/admin.routes').then(m => m.adminRoutes),
    canActivate : [AuthGuard],
    data : {roles :['ADMIN','SUPER_ADMIN']}
},{
    path : '**',
    loadComponent: () => import('./shared/components/error-page/error-page.component').then(m => m.ErrorPageComponent)  
}
];
