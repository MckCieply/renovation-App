import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {HttpClientModule, provideHttpClient, withInterceptors} from "@angular/common/http";
import {ErrorStateMatcher, ShowOnDirtyErrorStateMatcher} from "@angular/material/core";

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {SidebarComponent} from './sidebar/sidebar.component';
import {ToolbarComponent} from './toolbar/toolbar.component';
import {SharedModule} from './shared/shared.module';
import {authInterceptor} from "./shared/interceptors/auth.interceptor";
import {loadingInterceptor} from "./shared/interceptors/loading.interceptor";

@NgModule({
  declarations: [
    AppComponent,
    SidebarComponent,
    ToolbarComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    AppRoutingModule,
    SharedModule
  ],
  providers: [
    {provide: ErrorStateMatcher, useClass: ShowOnDirtyErrorStateMatcher},
    provideHttpClient(withInterceptors([authInterceptor, loadingInterceptor])),
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
