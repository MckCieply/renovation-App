import {Component, EventEmitter, Output, ViewChild} from '@angular/core';
@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrl: './toolbar.component.scss'
})
export class ToolbarComponent {

  @Output() sidenavEmit = new EventEmitter<void>();

    constructor() {
    }

    toggleSidenav(){
      this.sidenavEmit.emit()
    }
}
