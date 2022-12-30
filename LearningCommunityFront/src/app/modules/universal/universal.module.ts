import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {StudentRegisterComponent} from './components/student-register/student-register.component';
import {ReactiveFormsModule} from "@angular/forms";
import {NzFormModule} from "ng-zorro-antd/form";
import {NzInputModule} from "ng-zorro-antd/input";
import {NzSelectModule} from "ng-zorro-antd/select";
import {NzCheckboxModule} from "ng-zorro-antd/checkbox";
import {NzButtonModule} from "ng-zorro-antd/button";
import {NzLayoutModule} from "ng-zorro-antd/layout";
import {NzIconModule} from "ng-zorro-antd/icon";
import {NzCardModule} from "ng-zorro-antd/card";
import {NzRadioModule} from 'ng-zorro-antd/radio';
import {UniversalComponent} from './universal.component';
import {RouterModule} from "@angular/router";
import {UserLoginComponent} from './components/user-login/user-login.component';
import {FormsModule} from '@angular/forms';
import {AdminLoginComponent} from './components/admin-login/admin-login.component';
import {NzMessageService} from "ng-zorro-antd/message";
import { ChapterComponent } from './components/chapter/chapter.component';
import { NzCollapseModule } from 'ng-zorro-antd/collapse';
import { Chapter } from 'src/app/services/course/dtos/Chapter';
import { SectionComponent } from './components/section/section.component';
import { NzListModule } from 'ng-zorro-antd/list';
import { PersonalInfoComponent } from './components/personal-info/personal-info.component';
import {NzTypographyModule} from "ng-zorro-antd/typography";
import {NzAvatarModule} from "ng-zorro-antd/avatar";
import {NzUploadModule} from "ng-zorro-antd/upload";
import { CourseHeaderComponent } from './components/course-header/course-header.component';
import { AddProblemComponent } from './components/add-problem/add-problem.component';
import {NzDescriptionsModule} from "ng-zorro-antd/descriptions";
import { GradeProblemComponent } from '../student/components/grade-problem/grade-problem.component';
import {NzInputNumberModule} from "ng-zorro-antd/input-number";
import {NzPageHeaderModule} from "ng-zorro-antd/page-header";
import {NzTagModule} from "ng-zorro-antd/tag";
import {NzTableModule} from "ng-zorro-antd/table";
import { EditEssayProblemAnswerComponent } from './components/edit-essay-problem-answer/edit-essay-problem-answer.component';
import {NzModalModule} from "ng-zorro-antd/modal";
import {EditorModule} from "@tinymce/tinymce-angular";
import { ViewEssayProblemAnswerComponent } from './components/view-essay-problem-answer/view-essay-problem-answer.component';
import {NzTabsModule} from "ng-zorro-antd/tabs";
import { AddEssayProblemComponent } from './components/add-problem/add-essay-problem/add-essay-problem.component';
import {NzAlertModule} from "ng-zorro-antd/alert";
import { CourseFileComponent } from './components/course-file/course-file.component';
import { ViewCourseFileComponent } from './components/course-file/view-course-file/view-course-file.component';
import { UploadCourseFileComponent } from './components/course-file/upload-course-file/upload-course-file.component';
import { HomeworkListComponent } from './components/homework-list/homework-list.component';
import { AddHomeworkComponent } from './components/add-homework/add-homework.component';
import { NzSpaceModule } from 'ng-zorro-antd/space';
import { ViewEditHomeworkComponent } from './components/view-edit-homework/view-edit-homework.component';
import {NzTimelineModule} from "ng-zorro-antd/timeline";
import {NzDrawerModule} from "ng-zorro-antd/drawer";
import { HistoryVersionDrawerComponent } from './components/view-edit-homework/history-version-drawer/history-version-drawer.component';
import { ScoresComponent } from './components/scores/scores.component';


@NgModule({
  declarations: [
    StudentRegisterComponent,
    UniversalComponent,
    UserLoginComponent,
    AdminLoginComponent,
    ChapterComponent,
    SectionComponent,
    PersonalInfoComponent,
    CourseHeaderComponent,
    AddProblemComponent,
    GradeProblemComponent,
    EditEssayProblemAnswerComponent,
    ViewEssayProblemAnswerComponent,
    AddEssayProblemComponent,
    CourseFileComponent,
    ViewCourseFileComponent,
    UploadCourseFileComponent,
    HomeworkListComponent,
    AddHomeworkComponent,
    ViewEditHomeworkComponent,
    HistoryVersionDrawerComponent,
    ScoresComponent,
  ],
  providers: [{provide: NzMessageService}],
  imports: [
    NzFormModule,
    ReactiveFormsModule,
    NzInputModule,
    NzSelectModule,
    NzButtonModule,
    NzCheckboxModule,
    NzLayoutModule,
    NzIconModule,
    NzCardModule,
    CommonModule,
    RouterModule,
    NzRadioModule,
    FormsModule,
    NzCollapseModule,
    NzListModule,
    NzTypographyModule,
    NzAvatarModule,
    NzUploadModule,
    NzDescriptionsModule,
    NzInputNumberModule,
    NzPageHeaderModule,
    NzTagModule,
    NzTableModule,
    NzModalModule,
    EditorModule,
    NzTabsModule,
    NzAlertModule,
    NzSpaceModule,
    NzTimelineModule,
    NzDrawerModule
  ],
    exports: [ChapterComponent, CourseHeaderComponent, AddProblemComponent, GradeProblemComponent, EditEssayProblemAnswerComponent, ViewEssayProblemAnswerComponent, CourseFileComponent]
})
export class UniversalModule {
}
