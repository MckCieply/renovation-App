import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { RoomComponent } from './room/room.component';
import { WorkTypeComponent } from './work-type/work-type.component';
import { WorksComponent } from './works/works.component';
import { ContractorsComponent } from './contractors/contractors.component';
import {HttpClientModule} from "@angular/common/http";
import { AddRoomDialogComponent } from './room/add-room-dialog/add-room-dialog.component';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatDialogModule} from "@angular/material/dialog";
import {MatInput} from "@angular/material/input";
import {MatFooterRow} from "@angular/material/table";
import {MatButton, MatFabButton, MatMiniFabButton} from "@angular/material/button";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {ErrorStateMatcher, ShowOnDirtyErrorStateMatcher} from "@angular/material/core";
import {MatIcon} from "@angular/material/icon";
import { RemoveDialogComponent } from './dialogs/remove-dialog/remove-dialog.component';
import { EditRoomDialogComponent } from './room/edit-room-dialog/edit-room-dialog.component';
import { BudgetComponent } from './budget/budget.component';
import { AddWorkTypeDialogComponent } from './work-type/add-work-type-dialog/add-work-type-dialog.component';
import { EditWorkTypeDialogComponent } from './work-type/edit-work-type-dialog/edit-work-type-dialog.component';

@NgModule({
  declarations: [
    AppComponent,
    SidebarComponent,
    DashboardComponent,
    RoomComponent,
    WorkTypeComponent,
    WorksComponent,
    ContractorsComponent,
    AddRoomDialogComponent,
    RemoveDialogComponent,
    EditRoomDialogComponent,
    BudgetComponent,
    AddWorkTypeDialogComponent,
    EditWorkTypeDialogComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    MatFormFieldModule,
    MatDialogModule,
    MatInput,
    MatFooterRow,
    MatButton,
    FormsModule,
    ReactiveFormsModule,
    MatIcon,
    MatFabButton,
    MatMiniFabButton
  ],
  providers: [
    provideAnimationsAsync('noop'),
    {provide: ErrorStateMatcher, useClass: ShowOnDirtyErrorStateMatcher}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
