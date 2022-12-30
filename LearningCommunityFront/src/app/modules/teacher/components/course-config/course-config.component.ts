import {Component, Input, OnInit} from '@angular/core';
import {CourseConfigDTO} from "../../../../services/course-config/dto/CourseConfigDTO";
import {CourseConfigService} from "../../../../services/course-config/course-config.service";
import {NzMessageService} from "ng-zorro-antd/message";

@Component({
  selector: 'app-course-config',
  templateUrl: './course-config.component.html',
  styleUrls: ['./course-config.component.css']
})
export class CourseConfigComponent implements OnInit {

  @Input()
  public courseCode: string = ''
  public courseConfigDTO: CourseConfigDTO = new CourseConfigDTO(false);

  constructor(private courseConfigService: CourseConfigService,
              private message: NzMessageService) {
  }

  ngOnInit(): void {
    this.getCourseConfig();
  }

  getCourseConfig() {
    this.courseConfigService.teacherGetCourseConfig(
      this.courseCode,
      httpResponse => {
        this.courseConfigDTO = httpResponse.body as CourseConfigDTO
      }, httpErrorResponse => {
        this.message.error(JSON.stringify(httpErrorResponse.error));
      }
    )
  }

  setCourseConfig() {
    this.courseConfigService.teacherConfigCourse(
      this.courseCode,
      this.courseConfigDTO,
      httpResponse => {
        this.message.success(httpResponse.body?.message as string)
      }, httpErrorResponse => {
        this.message.error(JSON.stringify(httpErrorResponse.error))
      }
    )
  }

}
