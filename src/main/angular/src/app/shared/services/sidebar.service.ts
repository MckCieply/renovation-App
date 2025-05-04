import { Injectable } from '@angular/core';
import {BehaviorSubject} from "rxjs";
import {BreakpointObserver, Breakpoints} from "@angular/cdk/layout";

@Injectable({
  providedIn: 'root'
})
export class SidebarService {
  private _isSmallScreen$ = new BehaviorSubject<boolean>(false);
  isSmallScreen$ = this._isSmallScreen$.asObservable();

  private _expanded$ = new BehaviorSubject<boolean>(false);
  isExpanded$ = this._expanded$.asObservable();

  constructor(private breakpointObserver: BreakpointObserver) {
    this.breakpointObserver.observe([Breakpoints.Handset, Breakpoints.Tablet])
      .subscribe(result => {
        this._isSmallScreen$.next(result.matches)
      });
  }

  toggleSidenav() {
    this._expanded$.next(!this._expanded$.value);
  }
}
