import {Component, OnInit, Input} from '@angular/core';
import {CourseService} from "../../../../services/course/course.service";
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {CourseMember} from "../../../../services/course/dtos/CourseMember";
import {NzMessageService} from "ng-zorro-antd/message";
import {SuccessfulPostResponse} from "../../../../reqrsp/SuccessfulPostResponse";

@Component({
  selector: 'app-student-course-member-view',
  templateUrl: './student-course-member-view.component.html',
  styleUrls: ['./student-course-member-view.component.css']
})
export class StudentCourseMemberViewComponent implements OnInit {
  @Input()
  public courseCode: string | undefined

  public courseMembers: CourseMember[] = []


  constructor(private courseService: CourseService,
              private message: NzMessageService) {
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

  ngOnInit(): void {
    this.getCourseMemberFunc()
  }

  public reload(): void {
    this.getCourseMemberFunc()
  }

}
