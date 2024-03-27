import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {DashboardComponent} from "./dashboard/dashboard.component";
import {RoomComponent} from "./room/room.component";
import {WorkComponent} from "./work/work.component";
import {ContractorsComponent} from "./contractors/contractors.component";
import {WorkTypeComponent} from "./work-type/work-type.component";
import {BudgetComponent} from "./budget/budget.component";

const routes: Routes = [
  { path: 'Dashboard', component: DashboardComponent },
  { path: 'Work', component: WorkComponent },
  { path: 'Contractors', component: ContractorsComponent },
  { path: 'WorkType', component: WorkTypeComponent},
  { path: 'Room', component: RoomComponent},
  { path: 'Budget', component: BudgetComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
