import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {TeacherHomeComponent} from './teacher-home.component';
import {NzGridModule} from "ng-zorro-antd/grid";
import {NzDropDownModule} from "ng-zorro-antd/dropdown";
import {NzIconModule} from "ng-zorro-antd/icon";
import {RouterModule} from "@angular/router";
import {NzLayoutModule} from 'ng-zorro-antd/layout';
import {AddCourseComponent} from './components/add-course/add-course.component';
import {NzFormModule} from "ng-zorro-antd/form";
import {NzInputModule} from "ng-zorro-antd/input";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {NzButtonModule} from "ng-zorro-antd/button";
import {TeacherCourseNavComponent} from './components/teacher-course-nav/teacher-course-nav.component';
import {NzTabsModule} from "ng-zorro-antd/tabs";
import {NzListModule} from "ng-zorro-antd/list";
import {NzTypographyModule} from "ng-zorro-antd/typography";
import {
  TeacherCourseMemberViewComponent
} from './components/teacher-course-member-view/teacher-course-member-view.component';
import {NzTagModule} from "ng-zorro-antd/tag";
import {NzModalModule} from "ng-zorro-antd/modal";
import {UniversalModule} from '../universal/universal.module';
import {AddChapterComponent} from './components/add-chapter/add-chapter.component';
import {AddSectionComponent} from './components/add-section/add-section.component';
import {NzSelectModule} from 'ng-zorro-antd/select';
import {NzAvatarModule} from "ng-zorro-antd/avatar";
import {CourseConfigComponent} from './components/course-config/course-config.component';
import {NzCardModule} from "ng-zorro-antd/card";
import {NzSwitchModule} from "ng-zorro-antd/switch";
import {ProblemHandoutComponent} from './components/problem-handout/problem-handout.component';
import {NzCollapseModule} from "ng-zorro-antd/collapse";
import {NzInputNumberModule} from "ng-zorro-antd/input-number";
import {NzAffixModule} from "ng-zorro-antd/affix";
import {TeacherGradeProblemComponent} from './components/teacher-grade-problem/teacher-grade-problem.component';
import {NzBadgeModule} from "ng-zorro-antd/badge";
import {NzDescriptionsModule} from "ng-zorro-antd/descriptions";
import {NzTableModule} from "ng-zorro-antd/table";
import {NzNotificationModule} from "ng-zorro-antd/notification";


@NgModule({
  declarations: [
    TeacherHomeComponent,
    AddCourseComponent,
    TeacherCourseNavComponent,
    TeacherCourseMemberViewComponent,
    AddChapterComponent,
    AddSectionComponent,
    CourseConfigComponent,
    ProblemHandoutComponent,
    TeacherGradeProblemComponent,
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
    NzModalModule,
    UniversalModule,
    NzSelectModule,
    NzAvatarModule,
    NzCardModule,
    NzSwitchModule,
    FormsModule,
    NzCollapseModule,
    NzInputNumberModule,
    NzAffixModule,
    NzBadgeModule,
    NzDescriptionsModule,
    NzTableModule,
    NzNotificationModule
  ]
})
export class TeacherModule {
}
