import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule, Routes} from '@angular/router';
import {ReactiveFormsModule} from '@angular/forms';

// Material imports
import {MatTableModule} from '@angular/material/table';
import {MatSortModule} from '@angular/material/sort';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import {MatDialogModule} from '@angular/material/dialog';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatTooltipModule} from '@angular/material/tooltip';
import {provideNativeDateAdapter} from '@angular/material/core';

// Shared
import {SharedModule} from '../shared/shared.module';

import {RoomComponent} from './room.component';
import {RoomDialogComponent} from './room-dialog/room-dialog.component';

const routes: Routes = [
  {path: '', component: RoomComponent}
];

@NgModule({
  declarations: [
    RoomComponent,
    RoomDialogComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule.forChild(routes),
    SharedModule, // for pipes and dialogs
    // Material
    MatTableModule,
    MatSortModule,
    MatPaginatorModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatDialogModule,
    MatDatepickerModule,
    MatTooltipModule
  ],
  providers: [
    provideNativeDateAdapter()
  ]
})
export class RoomModule {
}

