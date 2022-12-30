import {Component, OnInit, Input, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {StudentCourseMemberViewComponent} from "../student-course-member-view/student-course-member-view.component";
import {DoProblemComponent} from "../do-problem/do-problem.component";
import {GradeProblemComponent} from "../grade-problem/grade-problem.component";
import {NotificationService} from "../../../../services/notification/notification.service";
import {HomeworkNotification} from "../../../../services/notification/dto/HomeworkNotification";
import {NzNotificationService} from "ng-zorro-antd/notification";


@Component({
  selector: 'app-student-course-nav',
  templateUrl: './student-course-nav.component.html',
  styleUrls: ['./student-course-nav.component.css']
})
export class StudentCourseNavComponent implements OnInit {

  public courseCode: string = '';
  public data = [1, 2, 3, 4, 5]

  @ViewChild(StudentCourseMemberViewComponent)
  public studentCourseMemberViewComponent: StudentCourseMemberViewComponent | undefined

  @ViewChild(DoProblemComponent)
  public doProblemComponent: DoProblemComponent | undefined

  @ViewChild(GradeProblemComponent)
  public gradeProblemsComponent: GradeProblemComponent | undefined;

  public dot: boolean = true

  constructor(private router: Router,
              private activatedRoute: ActivatedRoute,
              private notificationService: NotificationService,
              private notification: NzNotificationService) {

    this.activatedRoute.queryParams.subscribe(params => {
      this.courseCode = params['courseCode']
    })
  }

  ngOnInit(): void {
    this.popHomeworkNotifications()
  }

  public getLimit(): number {
    return this.doProblemComponent?.essayProblemToBeDoneList?.length as number;
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
