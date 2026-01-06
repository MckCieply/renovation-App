import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
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
    SharedModule,
    RouterModule.forChild(routes)
  ]
})
export class ContractorsModule {
}

