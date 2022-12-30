import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, NavigationEnd, Route, Router} from "@angular/router";
import {CourseService} from "../../../../services/course/course.service";
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {SuccessfulPostResponse} from "../../../../reqrsp/SuccessfulPostResponse";
import {NzMessageService} from "ng-zorro-antd/message";
import {AddChapterComponent} from "../add-chapter/add-chapter.component";
import {ChapterComponent} from "../../../universal/components/chapter/chapter.component";
import {CourseDTO} from "../../../../services/course/dtos/CourseDTO";
import {TeacherCourseMemberViewComponent} from "../teacher-course-member-view/teacher-course-member-view.component";
import {ProblemHandoutComponent} from "../problem-handout/problem-handout.component";
import {TeacherGradeProblemComponent} from "../teacher-grade-problem/teacher-grade-problem.component";
import {HomeworkNotification} from "../../../../services/notification/dto/HomeworkNotification";
import {NotificationService} from "../../../../services/notification/notification.service";
import {NzNotificationService} from "ng-zorro-antd/notification";

@Component({
  selector: 'app-teacher-course-nav',
  templateUrl: './teacher-course-nav.component.html',
  styleUrls: ['./teacher-course-nav.component.css']
})
export class TeacherCourseNavComponent implements OnInit {

  public courseCode: string = '';

  @ViewChild(ChapterComponent)
  public chapterComponent: ChapterComponent | undefined

  public chapterLastUpdated = new Date()

  @ViewChild(TeacherCourseMemberViewComponent)
  public teacherCourseMemberViewComponent: TeacherCourseMemberViewComponent | undefined

  @ViewChild(ProblemHandoutComponent)
  public problemHandoutComponent: ProblemHandoutComponent | undefined;

  @ViewChild(TeacherGradeProblemComponent)
  public teacherGradeProblemComponent: TeacherGradeProblemComponent | undefined;

  constructor(private router: Router,
              private activatedRoute: ActivatedRoute,
              private courseService: CourseService,
              private message: NzMessageService,
              private notificationService: NotificationService,
              private notification: NzNotificationService) {

    //接受路由传来的courseCode值
    this.activatedRoute.queryParams.subscribe(params => {
      this.courseCode = params['courseCode']
    })


  }

  ngOnInit(): void {
    this.popHomeworkNotifications()
  }


  inviteTA() {
    let ta = prompt("请输入要邀请的助教用户名(username)")
    if (ta === null) {
      return
    }

    this.courseService.inviteTA(
      this.courseCode,
      ta,
      (httpResponse: HttpResponse<SuccessfulPostResponse>) => {
        let info = httpResponse.body?.message as string
        this.message.success(info)
      },
      (httpErrorResponse: HttpErrorResponse) => {
        this.message.error(JSON.stringify(httpErrorResponse.error))
      })

  }

  public popHomeworkNotifications() {
    this.notificationService.getAllHomeworkNotifications(
      this.courseCode,
      response => {

        //获得通知
        let notifications: HomeworkNotification[] = response.body as HomeworkNotification[]

        //逐个弹出通知
        for (let i = 0; i < notifications.length; i++) {

          //把notificationID保存在本地，之后在闭包里调用
          let localNotificationID = notifications[i].homeworkNotificationID

          //弹出通知，并设置关闭时的回掉
          this.notification.blank(
            notifications[i].type,
            notifications[i].message)
            .onClose.subscribe(() => {

            //删除通知
            this.notificationService.dismissHomeworkNotification(
              localNotificationID,
              response1 => {
              },
              errorResponse => {
              }
            )
          })
        }


      },
      errorResponse => {
      }
    )
  }

}
