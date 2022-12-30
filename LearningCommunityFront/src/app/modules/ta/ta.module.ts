import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {TAHomeComponent} from './ta-home.component';
import {TACourseNavComponent} from './components/ta-course-nav/ta-course-nav.component';
import {TACourseMemberViewComponent} from './components/ta-course-member-view/ta-course-member-view.component';

import {NzGridModule} from "ng-zorro-antd/grid";
import {NzDropDownModule} from "ng-zorro-antd/dropdown";
import {NzIconModule} from "ng-zorro-antd/icon";
import {RouterModule} from "@angular/router";
import {NzLayoutModule} from 'ng-zorro-antd/layout';
import {NzFormModule} from "ng-zorro-antd/form";
import {NzInputModule} from "ng-zorro-antd/input";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {NzButtonModule} from "ng-zorro-antd/button";
import {NzTabsModule} from "ng-zorro-antd/tabs";
import {NzListModule} from "ng-zorro-antd/list";
import {NzTypographyModule} from "ng-zorro-antd/typography";
import {NzTagModule} from "ng-zorro-antd/tag";
import {UniversalModule} from '../universal/universal.module';
import {NzAvatarModule} from "ng-zorro-antd/avatar";
import {TaGradeProblemComponent} from './components/ta-grade-problem/ta-grade-problem.component';
import {NzInputNumberModule} from "ng-zorro-antd/input-number";
import {NzCardModule} from "ng-zorro-antd/card";
import {NzBadgeModule} from "ng-zorro-antd/badge";
import {NzDescriptionsModule} from "ng-zorro-antd/descriptions";
import {NzTableModule} from "ng-zorro-antd/table";
import {NzNotificationModule} from "ng-zorro-antd/notification";


@NgModule({
  declarations: [
    TAHomeComponent,
    TACourseNavComponent,
    TACourseMemberViewComponent,
    TaGradeProblemComponent,
  ],
  imports: [
    CommonModule,
    NzLayoutModule,
    NzGridModule,
    NzDropDownModule,
    NzIconModule,
    RouterModule,
    NzFormModule,
    NzInputModule,
    ReactiveFormsModule,
    NzButtonModule,
    NzTabsModule,
    NzListModule,
    NzTypographyModule,
    NzTagModule,
    UniversalModule,
    NzAvatarModule,
    NzInputNumberModule,
    FormsModule,
    NzCardModule,
    NzBadgeModule,
    NzDescriptionsModule,
    NzTableModule,
    NzNotificationModule
  ]
})
export class TAModule {
}
