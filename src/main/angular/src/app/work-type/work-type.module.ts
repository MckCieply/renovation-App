import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {SharedModule} from '../shared/shared.module';
import {WorkTypeComponent} from './work-type.component';
import {WorkTypeDialogComponent} from './work-type-dialog/work-type-dialog.component';

const routes: Routes = [
  {path: '', component: WorkTypeComponent}
];

@NgModule({
  declarations: [
    WorkTypeComponent,
    WorkTypeDialogComponent
  ],
  imports: [
    SharedModule,
    RouterModule.forChild(routes)
  ]
})
export class WorkTypeModule {
}

