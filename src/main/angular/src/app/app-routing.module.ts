import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {DashboardComponent} from "./dashboard/dashboard.component";
import {RoomComponent} from "./room/room.component";
import {WorkComponent} from "./work/work.component";
import {ContractorsComponent} from "./contractors/contractors.component";
import {WorkTypeComponent} from "./work-type/work-type.component";
import {BudgetComponent} from "./budget/budget.component";
import {LoginComponent} from "./auth/login/login.component";
import {RegisterComponent} from "./auth/register/register.component";
import {authGuard} from "./auth/guards/auth.guard";
import {UserComponent} from "./user/user.component";
import {HomeComponent} from "./home/home.component";

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'Login', component: LoginComponent },
  { path: 'Register', component: RegisterComponent },
  { path: 'Dashboard', component: DashboardComponent, canActivate: [authGuard] },
  { path: 'Work', component: WorkComponent, canActivate: [authGuard] },
  { path: 'Contractors', component: ContractorsComponent, canActivate: [authGuard] },
  { path: 'WorkType', component: WorkTypeComponent, canActivate: [authGuard] },
  { path: 'Room', component: RoomComponent, canActivate: [authGuard] },
  { path: 'Budget', component: BudgetComponent, canActivate: [authGuard] },
  {path: 'User', component: UserComponent, canActivate: [authGuard] },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
