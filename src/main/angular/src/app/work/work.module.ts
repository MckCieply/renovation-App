import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {SharedModule} from '../shared/shared.module';
import {WorkComponent} from './work.component';
import {WorkDialogComponent} from './work-dialog/work-dialog.component';

const routes: Routes = [
  {path: '', component: WorkComponent}
];

@NgModule({
  declarations: [
    WorkComponent,
    WorkDialogComponent
  ],
  imports: [
    SharedModule,
    RouterModule.forChild(routes)
  ]
})
export class WorkModule {
}

