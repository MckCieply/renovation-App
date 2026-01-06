import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule, Routes} from '@angular/router';
import {ReactiveFormsModule} from '@angular/forms';
import {MaskitoDirective} from '@maskito/angular';

// Material imports
import {MatTableModule} from '@angular/material/table';
import {MatSortModule} from '@angular/material/sort';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import {MatDialogModule} from '@angular/material/dialog';
import {MatRadioModule} from '@angular/material/radio';
import {MatTabsModule} from '@angular/material/tabs';
import {MatTooltipModule} from '@angular/material/tooltip';

// Shared
import {SharedModule} from '../shared/shared.module';

import {ContractorsComponent} from './contractors.component';
import {ContractorDialogComponent} from './contractor-dialog/contractor-dialog.component';

const routes: Routes = [
  {path: '', component: ContractorsComponent}
];

@NgModule({
  declarations: [
    ContractorsComponent,
    ContractorDialogComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule.forChild(routes),
    MaskitoDirective,
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
    MatRadioModule,
    MatTabsModule,
    MatTooltipModule
  ]
})
export class ContractorsModule {
}

