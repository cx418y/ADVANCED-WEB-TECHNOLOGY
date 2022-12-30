import {Component, OnInit, Input} from '@angular/core';
import {CourseService} from "../../../../services/course/course.service";
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {CourseMember} from "../../../../services/course/dtos/CourseMember";
import {NzMessageService} from "ng-zorro-antd/message";
import {SuccessfulPostResponse} from "../../../../reqrsp/SuccessfulPostResponse";

@Component({
  selector: 'app-ta-course-member-view',
  templateUrl: './ta-course-member-view.component.html',
  styleUrls: ['./ta-course-member-view.component.css']
})
export class TACourseMemberViewComponent implements OnInit {
  @Input()
  public courseCode: string | undefined

  public courseMembers: CourseMember[] = []


  constructor(private courseService: CourseService,
              private message: NzMessageService) {
  }


  ngOnInit(): void {
    this.getCourseMemberFunc()
  }

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

  public reload(): void {
     this.getCourseMemberFunc()
  }

  public removeCourseStudent(username: string) {
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

}
