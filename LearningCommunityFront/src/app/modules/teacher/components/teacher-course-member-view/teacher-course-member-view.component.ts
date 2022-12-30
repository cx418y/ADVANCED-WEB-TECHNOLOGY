import {Component, Input, OnInit} from '@angular/core';
import {CourseService} from "../../../../services/course/course.service";
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {CourseMember} from "../../../../services/course/dtos/CourseMember";
import {NzMessageService} from "ng-zorro-antd/message";
import {SuccessfulPostResponse} from "../../../../reqrsp/SuccessfulPostResponse";

@Component({
  selector: 'app-teacher-course-member-view',
  templateUrl: './teacher-course-member-view.component.html',
  styleUrls: ['./teacher-course-member-view.component.css']
})
export class TeacherCourseMemberViewComponent implements OnInit {

  @Input()
  public courseCode: string | undefined

  public courseMembers: CourseMember[] = []


  /**
   * 获取课程成员的函数
   */
  public getCourseMemberFunc = () => {
    this.courseService.getCourseMember(
      this.courseCode as string,
      (httpResponse: HttpResponse<CourseMember[]>) => {
        this.courseMembers = httpResponse.body as CourseMember[];
      },
      (httpErrorResponse: HttpErrorResponse) => {
        this.message.error(JSON.stringify(httpErrorResponse.error))
      }
    )
  }

  constructor(private courseService: CourseService,
              private message: NzMessageService) {
  }


  public removeCourseStudentTA(username: string) {
    this.courseService.removeCourseStudentTA(
      this.courseCode as string,
      username,
      (httpResponse: HttpResponse<SuccessfulPostResponse>) => {
        //弹出成功提示
        this.message.success(httpResponse.body?.message as string)
        //重新获取课程成员
        this.getCourseMemberFunc()
      },
      (httpErrorResponse: HttpErrorResponse) => {
        this.message.error(JSON.stringify(httpErrorResponse.error))
      }
    )
  }

  reload(): void {
    this.getCourseMemberFunc()
  }


  ngOnInit(): void {
    this.getCourseMemberFunc()
  }


}
