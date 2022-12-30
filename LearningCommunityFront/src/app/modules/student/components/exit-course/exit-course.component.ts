import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {CommonService} from "../../../../services/common/common.service";
import {CourseDTO} from "../../../../services/course/dtos/CourseDTO";
import {CourseService} from "../../../../services/course/course.service";
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {NzMessageService} from "ng-zorro-antd/message";
import {LoginRequiredComponent} from "../../../LoginRequiredComponent";
import {SuccessfulPostResponse} from "../../../../reqrsp/SuccessfulPostResponse";

@Component({
  selector: 'app-exit-course',
  templateUrl: './exit-course.component.html',
  styleUrls: ['./exit-course.component.css']
})
export class ExitCourseComponent implements OnInit {
  public myRelatedCourses: Array<CourseDTO> = []

  constructor(private router: Router,
              private courseService: CourseService,
              private message: NzMessageService) {
    this.getMyRelatedCourses();
  }

  // 在页面加载时就尝试获取自己的课程
  public getMyRelatedCourses() {
    this.courseService.getMyRelatedCourses(
      (httpResponse: HttpResponse<Array<CourseDTO>>) => {
        console.log(`获取到课程`)
        console.log(httpResponse.body)
        this.myRelatedCourses = httpResponse.body as Array<CourseDTO>;
      },
      (httpErrorResponse: HttpErrorResponse) => {
        this.message.info(JSON.stringify(httpErrorResponse))
      }
    )
  }

  // 退出课程()
  public exitCourse(courseCode: string) {
    this.courseService.exitCourse(
      courseCode,
      (httpResponse: HttpResponse<SuccessfulPostResponse>) => {
        //弹出成功提示
        this.message.success(httpResponse.body?.message as string)
        alert("退出成功")
        //重新获取课程成员
        this.getMyRelatedCourses()
      },
      (httpErrorResponse: HttpErrorResponse) => {
        this.message.error(JSON.stringify(httpErrorResponse.error))
      }
    )
  }

  ngOnInit(): void {
  }

}
