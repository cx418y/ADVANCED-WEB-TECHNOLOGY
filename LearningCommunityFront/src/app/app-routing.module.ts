import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {Routes} from "@angular/router";
import {RouterModule} from "@angular/router";
import {UniversalComponent} from "./modules/universal/universal.component";
import {StudentRegisterComponent} from "./modules/universal/components/student-register/student-register.component";
import {AdminHomeComponent} from "./modules/admin/admin-home.component";
import {AddTeacherTaStudentComponent} from "./modules/admin/components/add-student/add-teacher-ta-student.component";
import {UserLoginComponent} from './modules/universal/components/user-login/user-login.component';
import {AdminLoginComponent} from './modules/universal/components/admin-login/admin-login.component';
import {TeacherHomeComponent} from "./modules/teacher/teacher-home.component";
import {AddCourseComponent} from "./modules/teacher/components/add-course/add-course.component";
import {TeacherCourseNavComponent} from "./modules/teacher/components/teacher-course-nav/teacher-course-nav.component";
import {TAHomeComponent} from './modules/ta/ta-home.component';
import {TACourseNavComponent} from './modules/ta/components/ta-course-nav/ta-course-nav.component';
import {StudentHomeComponent} from './modules/student/student-home.component';
import {StudentCourseNavComponent} from './modules/student/components/student-course-nav/student-course-nav.component';
import {ExitCourseComponent} from './modules/student/components/exit-course/exit-course.component';
import {JoinCourseComponent} from './modules/student/components/join-course/join-course.component';
import {PersonalInfoComponent} from "./modules/universal/components/personal-info/personal-info.component";
import {
  ViewEditHomeworkComponent
} from "./modules/universal/components/view-edit-homework/view-edit-homework.component";

const routes: Routes = [
  {
    path: "universal",
    component: UniversalComponent,
    children: [
      {path: "studentRegister", component: StudentRegisterComponent},
      {path: "userLogin", component: UserLoginComponent},
      {path: "adminLogin", component: AdminLoginComponent},
      {path: "viewEditHomework", component: ViewEditHomeworkComponent}
    ]
  },

  {
    path: "admin",
    component: AdminHomeComponent,
    children: [
      {path: "addTeacherTaStudent", component: AddTeacherTaStudentComponent}
    ]
  },

  {
    path: "teacher",
    component: TeacherHomeComponent,
    children: [
      {path: "addCourse", component: AddCourseComponent},
      {path: "teacherCourseNav", component: TeacherCourseNavComponent, runGuardsAndResolvers: 'always'},
      {path: "personalInfo", component: PersonalInfoComponent}
    ]
  },

  {
    path: "ta",
    component: TAHomeComponent,
    children: [
      {path: "taCourseNav", component: TACourseNavComponent, runGuardsAndResolvers: 'always'},
      {path: "personalInfo", component: PersonalInfoComponent}
    ]
  },

  {
    path: "student",
    component: StudentHomeComponent,
    children: [
      {path: "studentCourseNav", component: StudentCourseNavComponent, runGuardsAndResolvers: 'always'},
      {path: "exitCourse", component: ExitCourseComponent},
      {path: "joinCourse", component: JoinCourseComponent},
      {path: "personalInfo", component: PersonalInfoComponent}
    ]
  }
]

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    RouterModule.forRoot(routes, {onSameUrlNavigation: 'reload'})
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
