import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {SharedModule} from '../shared/shared.module';
import {NgxChartsModule} from '@swimlane/ngx-charts';
import {DashboardComponent} from './dashboard.component';
import {CustomLegendComponent} from './components/custom-legend/custom-legend.component';

const routes: Routes = [
  {path: '', component: DashboardComponent}
];

@NgModule({
  declarations: [
    DashboardComponent,
    CustomLegendComponent
  ],
  imports: [
    SharedModule,
    NgxChartsModule,
    RouterModule.forChild(routes)
  ]
})
export class DashboardModule {
}

