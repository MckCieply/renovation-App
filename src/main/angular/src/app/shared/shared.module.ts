import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RouterModule} from '@angular/router';

// Material Imports for shared components
import {MatDialogModule} from "@angular/material/dialog";
import {MatButtonModule} from "@angular/material/button";
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";

// Pipes
import {RelativeTimePipe} from './pipes/relative-time.pipe';
import {CurrencyMaskPipe} from './pipes/currency-mask.pipe';

// Components
import {LoadingSpinnerComponent} from './components/loading-spinner/loading-spinner.component';
import {RemoveDialogComponent} from '../dialogs/remove-dialog/remove-dialog.component';
import {ConfirmDialogComponent} from '../dialogs/confirm-dialog/confirm-dialog.component';

@NgModule({
  declarations: [
    RelativeTimePipe,
    CurrencyMaskPipe,
    LoadingSpinnerComponent,
    RemoveDialogComponent,
    ConfirmDialogComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    // Material for shared components
    MatDialogModule,
    MatButtonModule,
    MatProgressSpinnerModule
  ],
  exports: [
    // Common modules
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    // Pipes
    RelativeTimePipe,
    CurrencyMaskPipe,
    // Components
    LoadingSpinnerComponent,
    RemoveDialogComponent,
    ConfirmDialogComponent
  ]
})
export class SharedModule {
}

