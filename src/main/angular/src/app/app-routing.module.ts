import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {authGuard} from "./auth/guards/auth.guard";
import {adminGuard} from "./auth/guards/admin.guard";

const routes: Routes = [
  {
    path: '',
    loadChildren: () => import('./home/home.module').then(m => m.HomeModule)
  },
  {
    path: 'Auth',
    loadChildren: () => import('./auth/auth.module').then(m => m.AuthModule)
  },
  {
    path: 'Dashboard',
    loadChildren: () => import('./dashboard/dashboard.module').then(m => m.DashboardModule),
    canActivate: [authGuard]
  },
  {
    path: 'Work',
    loadChildren: () => import('./work/work.module').then(m => m.WorkModule),
    canActivate: [authGuard]
  },
  {
    path: 'Contractors',
    loadChildren: () => import('./contractors/contractors.module').then(m => m.ContractorsModule),
    canActivate: [authGuard]
  },
  {
    path: 'WorkType',
    loadChildren: () => import('./work-type/work-type.module').then(m => m.WorkTypeModule),
    canActivate: [authGuard]
  },
  {
    path: 'Room',
    loadChildren: () => import('./room/room.module').then(m => m.RoomModule),
    canActivate: [authGuard]
  },
  {
    path: 'Budget',
    loadChildren: () => import('./budget/budget.module').then(m => m.BudgetModule),
    canActivate: [authGuard]
  },
  {
    path: 'User',
    loadChildren: () => import('./user/user.module').then(m => m.UserModule),
    canActivate: [authGuard]
  },
  {
    path: 'Admin',
    loadChildren: () => import('./admin/admin.module').then(m => m.AdminModule),
    canActivate: [adminGuard]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
