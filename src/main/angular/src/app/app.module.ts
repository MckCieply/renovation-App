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
import { RoomFormComponent } from './room/room-form/room-form.component';
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

@NgModule({
  declarations: [
    AppComponent,
    SidebarComponent,
    DashboardComponent,
    RoomComponent,
    WorkTypeComponent,
    WorksComponent,
    ContractorsComponent,
    RoomFormComponent,
    RemoveDialogComponent
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
