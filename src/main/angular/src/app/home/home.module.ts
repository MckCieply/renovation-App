import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule, Routes} from '@angular/router';

// Material imports
import {MatButtonModule} from '@angular/material/button';

import {HomeComponent} from './home.component';

const routes: Routes = [
  {path: '', component: HomeComponent}
];

@NgModule({
  declarations: [
    HomeComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    // Material
    MatButtonModule
  ]
})
export class HomeModule {
}

