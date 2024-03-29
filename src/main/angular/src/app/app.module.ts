import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { RoomComponent } from './room/room.component';
import { WorkTypeComponent } from './work-type/work-type.component';
import { ContractorsComponent } from './contractors/contractors.component';
import {HttpClientModule} from "@angular/common/http";
import { RoomDialogComponent } from './room/room-dialog/room-dialog.component';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatDialogModule} from "@angular/material/dialog";
import {MatInput} from "@angular/material/input";
import {MatFooterRow} from "@angular/material/table";
import {MatButton, MatFabButton, MatMiniFabButton} from "@angular/material/button";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {
  ErrorStateMatcher,
  MatOption,
  provideNativeDateAdapter,
  ShowOnDirtyErrorStateMatcher
} from "@angular/material/core";
import {MatIcon} from "@angular/material/icon";
import { RemoveDialogComponent } from './dialogs/remove-dialog/remove-dialog.component';
import { BudgetComponent } from './budget/budget.component';
import { WorkTypeDialogComponent } from './work-type/work-type-dialog/work-type-dialog.component';
import { ContractorDialogComponent } from './contractors/contractor-dialog/contractor-dialog.component';
import {MatRadioButton, MatRadioGroup} from "@angular/material/radio";
import {MatTab, MatTabGroup} from "@angular/material/tabs";
import { WorkComponent } from './work/work.component';
import { WorkDialogComponent } from './work/work-dialog/work-dialog.component';
import {MatCheckbox} from "@angular/material/checkbox";
import {MatSelect} from "@angular/material/select";
import {
  MatDatepickerInput,
  MatDatepickerToggle,
  MatDateRangeInput,
  MatDateRangePicker
} from "@angular/material/datepicker";

@NgModule({
  declarations: [
    AppComponent,
    SidebarComponent,
    DashboardComponent,
    RoomComponent,
    WorkTypeComponent,
    ContractorsComponent,
    RoomDialogComponent,
    RemoveDialogComponent,
    BudgetComponent,
    WorkTypeDialogComponent,
    ContractorDialogComponent,
    WorkComponent,
    WorkDialogComponent,
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
    MatMiniFabButton,
    MatRadioButton,
    MatRadioGroup,
    MatTabGroup,
    MatTab,
    MatCheckbox,
    MatSelect,
    MatOption,
    MatDatepickerInput,
    MatDatepickerToggle,
    MatDateRangeInput,
    MatDateRangePicker
  ],
  providers: [
    provideAnimationsAsync('noop'),
    {provide: ErrorStateMatcher, useClass: ShowOnDirtyErrorStateMatcher},
    provideNativeDateAdapter()
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
