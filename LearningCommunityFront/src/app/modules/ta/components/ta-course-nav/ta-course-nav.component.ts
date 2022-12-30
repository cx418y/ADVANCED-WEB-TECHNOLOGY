import {Component, OnInit, Input, ViewChild} from '@angular/core';
import {ActivatedRoute, Route, Router} from "@angular/router";
import {TACourseMemberViewComponent} from "../ta-course-member-view/ta-course-member-view.component";
import {TaGradeProblemComponent} from "../ta-grade-problem/ta-grade-problem.component";
import {HomeworkNotification} from "../../../../services/notification/dto/HomeworkNotification";
import {NotificationService} from "../../../../services/notification/notification.service";
import {NzNotificationModule, NzNotificationService} from "ng-zorro-antd/notification";

@Component({
  selector: 'app-ta-course-nav',
  templateUrl: './ta-course-nav.component.html',
  styleUrls: ['./ta-course-nav.component.css']
})
export class TACourseNavComponent implements OnInit {

  public courseCode: string = '';
  public data = [1, 2, 3, 4, 5]

  @ViewChild(TACourseMemberViewComponent)
  public taCourseMemberViewComponent: TACourseMemberViewComponent | undefined

  @ViewChild(TaGradeProblemComponent)
  public taGradeProblemComponent: (TaGradeProblemComponent | undefined)

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
