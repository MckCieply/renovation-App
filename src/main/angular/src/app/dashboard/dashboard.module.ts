import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule, Routes} from '@angular/router';
import {NgxChartsModule} from '@swimlane/ngx-charts';

// Material imports
import {MatGridListModule} from '@angular/material/grid-list';
import {MatIconModule} from '@angular/material/icon';

// Shared
import {SharedModule} from '../shared/shared.module';

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
    CommonModule,
    RouterModule.forChild(routes),
    NgxChartsModule,
    SharedModule, // for CurrencyMaskPipe
    // Material
    MatGridListModule,
    MatIconModule
  ]
})
export class DashboardModule {
}

