import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RouterModule} from '@angular/router';

// Material Imports
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatDialogModule} from "@angular/material/dialog";
import {MatInput} from "@angular/material/input";
import {
  MatCell,
  MatCellDef,
  MatColumnDef,
  MatFooterRow,
  MatHeaderCell,
  MatHeaderCellDef,
  MatHeaderRow,
  MatHeaderRowDef,
  MatRow,
  MatRowDef,
  MatTable
} from "@angular/material/table";
import {MatButton, MatFabButton, MatIconButton, MatMiniFabButton} from "@angular/material/button";
import {MatOption, provideNativeDateAdapter} from "@angular/material/core";
import {MatIcon} from "@angular/material/icon";
import {MatRadioButton, MatRadioGroup} from "@angular/material/radio";
import {MatTab, MatTabGroup} from "@angular/material/tabs";
import {MatCheckbox} from "@angular/material/checkbox";
import {MatSelect} from "@angular/material/select";
import {
  MatDatepicker,
  MatDatepickerActions,
  MatDatepickerApply,
  MatDatepickerCancel,
  MatDatepickerInput,
  MatDatepickerToggle,
  MatDateRangeInput,
  MatDateRangePicker
} from "@angular/material/datepicker";
import {MatSidenav, MatSidenavContainer, MatSidenavContent, MatSidenavModule} from "@angular/material/sidenav";
import {MatListItem, MatListItemIcon, MatNavList} from "@angular/material/list";
import {MatToolbar} from "@angular/material/toolbar";
import {MatGridList, MatGridTile} from "@angular/material/grid-list";
import {MatMenu, MatMenuItem, MatMenuTrigger} from "@angular/material/menu";
import {MatCard, MatCardContent, MatCardHeader, MatCardTitle} from "@angular/material/card";
import {MatSort, MatSortHeader} from "@angular/material/sort";
import {MatPaginator} from "@angular/material/paginator";
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import {MatTooltip} from "@angular/material/tooltip";
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {MaskitoDirective} from "@maskito/angular";

// Pipes
import {RelativeTimePipe} from './pipes/relative-time.pipe';
import {CurrencyMaskPipe} from './pipes/currency-mask.pipe';

// Components
import {LoadingSpinnerComponent} from './components/loading-spinner/loading-spinner.component';
import {RemoveDialogComponent} from '../dialogs/remove-dialog/remove-dialog.component';
import {ConfirmDialogComponent} from '../dialogs/confirm-dialog/confirm-dialog.component';

const MATERIAL_MODULES = [
  MatFormFieldModule,
  MatDialogModule,
  MatInput,
  MatFooterRow,
  MatButton,
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
  MatDateRangePicker,
  MatDatepicker,
  MatSidenavContent,
  MatSidenav,
  MatSidenavContainer,
  MatSidenavModule,
  MatNavList,
  MatListItem,
  MatToolbar,
  MatIconButton,
  MatListItemIcon,
  MatGridList,
  MatGridTile,
  MatTable,
  MatHeaderCellDef,
  MatHeaderCell,
  MatColumnDef,
  MatCell,
  MatCellDef,
  MatHeaderRow,
  MatHeaderRowDef,
  MatRowDef,
  MatRow,
  MatMenuTrigger,
  MatMenu,
  MatMenuItem,
  MatCard,
  MatCardContent,
  MatCardHeader,
  MatCardTitle,
  MatSortHeader,
  MatSort,
  MatPaginator,
  MatDatepickerActions,
  MatDatepickerCancel,
  MatDatepickerApply,
  MatProgressSpinnerModule,
  MatTooltip,
  MatSnackBarModule
];

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
    MaskitoDirective,
    ...MATERIAL_MODULES
  ],
  exports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    MaskitoDirective,
    ...MATERIAL_MODULES,
    RelativeTimePipe,
    CurrencyMaskPipe,
    LoadingSpinnerComponent,
    RemoveDialogComponent,
    ConfirmDialogComponent
  ],
  providers: [
    provideNativeDateAdapter()
  ]
})
export class SharedModule {
}

