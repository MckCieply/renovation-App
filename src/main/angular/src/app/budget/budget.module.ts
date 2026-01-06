import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule, Routes} from '@angular/router';
import {FormsModule} from '@angular/forms';

// Material imports
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';

import {BudgetComponent} from './budget.component';

const routes: Routes = [
  {path: '', component: BudgetComponent}
];

@NgModule({
  declarations: [
    BudgetComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    RouterModule.forChild(routes),
    // Material
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule
  ]
})
export class BudgetModule {
}

