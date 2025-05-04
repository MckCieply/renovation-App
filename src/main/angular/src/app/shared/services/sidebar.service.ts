import { Injectable } from '@angular/core';
import {BehaviorSubject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class SidebarService {
  private _expanded$ = new BehaviorSubject<boolean>(false);
  isExpanded$ = this._expanded$.asObservable();

  toggleSidenav() {
    this._expanded$.next(!this._expanded$.value);
  }
}
