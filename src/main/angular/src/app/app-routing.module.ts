import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {DashboardComponent} from "./dashboard/dashboard.component";
import {RoomComponent} from "./room/room.component";
import {WorksComponent} from "./works/works.component";
import {ContractorsComponent} from "./contractors/contractors.component";
import {WorkTypeComponent} from "./work-type/work-type.component";
import {AddDialogComponent} from "./room/add-dialog/add-dialog.component";
import {BudgetComponent} from "./budget/budget.component";

const routes: Routes = [
  { path: 'Dashboard', component: DashboardComponent },
  { path: 'Works', component: WorksComponent },
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
