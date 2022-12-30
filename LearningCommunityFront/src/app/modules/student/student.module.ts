import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {StudentHomeComponent} from './student-home.component';
import {
  StudentCourseMemberViewComponent
} from './components/student-course-member-view/student-course-member-view.component';
import {StudentCourseNavComponent} from './components/student-course-nav/student-course-nav.component';
import {NzGridModule} from "ng-zorro-antd/grid";
import {NzDropDownModule} from "ng-zorro-antd/dropdown";
import {NzIconModule} from "ng-zorro-antd/icon";
import {RouterModule} from "@angular/router";
import {NzLayoutModule} from 'ng-zorro-antd/layout';
import {NzFormModule} from "ng-zorro-antd/form";
import {NzInputModule} from "ng-zorro-antd/input";
import {ReactiveFormsModule} from "@angular/forms";
import {NzButtonModule} from "ng-zorro-antd/button";
import {NzTabsModule} from "ng-zorro-antd/tabs";
import {NzListModule} from "ng-zorro-antd/list";
import {NzTypographyModule} from "ng-zorro-antd/typography";
import {NzTagModule} from "ng-zorro-antd/tag";
import {ExitCourseComponent} from './components/exit-course/exit-course.component';
import {JoinCourseComponent} from './components/join-course/join-course.component';
import {FormsModule} from '@angular/forms';
import {UniversalModule} from '../universal/universal.module';
import {NzAvatarModule} from "ng-zorro-antd/avatar";
import {NzBadgeModule} from "ng-zorro-antd/badge";
import {DoProblemComponent} from './components/do-problem/do-problem.component';
import {NzCardModule} from "ng-zorro-antd/card";
import {NzSwitchModule} from "ng-zorro-antd/switch";
import {AnswerRecordComponent} from './components/answer-record/answer-record.component';
import {NzInputNumberModule} from 'ng-zorro-antd/input-number';
import {NzTableModule} from "ng-zorro-antd/table";
import {NzModalModule} from "ng-zorro-antd/modal";
import {NzNotificationModule} from 'ng-zorro-antd/notification';
import { MyHomeworkComponent } from './components/my-homework/my-homework.component';

@NgModule({
  declarations: [
    StudentHomeComponent,
    StudentCourseMemberViewComponent,
    StudentCourseNavComponent,
    ExitCourseComponent,
    JoinCourseComponent,
    DoProblemComponent,
    AnswerRecordComponent,
    MyHomeworkComponent
  ],
  imports: [
    CommonModule,
    NzGridModule,
    NzDropDownModule,
    NzIconModule,
    RouterModule,
    NzLayoutModule,
    NzFormModule,
    NzInputModule,
    ReactiveFormsModule,
    NzButtonModule,
    NzTabsModule,
    NzListModule,
    NzTypographyModule,
    NzTagModule,
    FormsModule,
    UniversalModule,
    NzAvatarModule,
    NzBadgeModule,
    NzCardModule,
    NzSwitchModule,
    NzInputNumberModule,
    NzTableModule,
    NzModalModule,
    NzNotificationModule
  ]
})
export class StudentModule {
}
