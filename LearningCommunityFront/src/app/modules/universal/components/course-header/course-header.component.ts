import {Component, Input, OnInit} from '@angular/core';
import {CourseService} from "../../../../services/course/course.service";
import {NzMessageService} from "ng-zorro-antd/message";
import {CourseDTO} from "../../../../services/course/dtos/CourseDTO";

@Component({
  selector: 'app-course-header',
  templateUrl: './course-header.component.html',
  styleUrls: ['./course-header.component.css']
})
export class CourseHeaderComponent implements OnInit {

  @Input()
  public courseCode: string = ''

  public courseDTO: CourseDTO | null | undefined

  constructor(private courseService: CourseService,
              private message: NzMessageService) {
  }

  ngOnInit(): void {
    this.courseService.getMyRelatedCoursesByCourseCode(
      this.courseCode,
      httpResponse => {
        this.courseDTO = httpResponse.body;
      },
      httpErrorResponse => {
        this.message.error(JSON.stringify(httpErrorResponse.error))
      }
    )
  }

}
