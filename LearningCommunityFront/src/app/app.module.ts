import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {AppRoutingModule} from './app-routing.module';
import {RouterModule} from "@angular/router";
import {NZ_I18N} from 'ng-zorro-antd/i18n';
import {zh_CN, en_US} from 'ng-zorro-antd/i18n';
import {registerLocaleData} from '@angular/common';
import zh from '@angular/common/locales/zh';
import {FormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {UniversalModule} from "./modules/universal/universal.module";
import {AdminModule} from "./modules/admin/admin.module";
import {TeacherModule} from "./modules/teacher/teacher.module";
import{TAModule} from "./modules/ta/ta.module";
import{StudentModule} from"./modules/student/student.module";

registerLocaleData(zh);

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    RouterModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    UniversalModule,
    AdminModule,
    TeacherModule,
    TAModule,
    StudentModule
  ],
  providers: [{provide: NZ_I18N, useValue: zh_CN}],
  bootstrap: [AppComponent]
})
export class AppModule {
}
