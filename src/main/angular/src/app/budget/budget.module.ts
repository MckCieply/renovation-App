import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {SharedModule} from '../shared/shared.module';
import {BudgetComponent} from './budget.component';

const routes: Routes = [
  {path: '', component: BudgetComponent}
];

@NgModule({
  declarations: [
    BudgetComponent
  ],
  imports: [
    SharedModule,
    RouterModule.forChild(routes)
  ]
})
export class BudgetModule {
}

