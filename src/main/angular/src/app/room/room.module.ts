import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {SharedModule} from '../shared/shared.module';
import {RoomComponent} from './room.component';
import {RoomDialogComponent} from './room-dialog/room-dialog.component';

const routes: Routes = [
  {path: '', component: RoomComponent}
];

@NgModule({
  declarations: [
    RoomComponent,
    RoomDialogComponent
  ],
  imports: [
    SharedModule,
    RouterModule.forChild(routes)
  ]
})
export class RoomModule {
}

