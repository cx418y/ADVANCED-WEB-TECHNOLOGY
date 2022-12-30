import { Component, OnInit,Input } from '@angular/core';
import { Section } from 'src/app/services/course/dtos/Section';
import {CourseService} from "../../../../services/course/course.service";
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import { Chapter } from 'src/app/services/course/dtos/Chapter';
import {NzMessageService} from "ng-zorro-antd/message";
import { CommonService } from 'src/app/services/common/common.service';


@Component({
  selector: 'app-section',
  templateUrl: './section.component.html',
  styleUrls: ['./section.component.css']
})
export class SectionComponent implements OnInit {
  @Input()
  public chapterID: string = ''
  @Input()
  public courseCode: string = ''
  @Input()
  public isStudent : Boolean = false

  public sections: Array<Section> = []

  constructor(private courseService: CourseService,private message: NzMessageService,private commonService: CommonService) {
  }



  ngOnInit(): void {
    this.getSection()
  }

  // 尝试获得章的所有小节
  public getSection() {
    this.courseService.getSection(this.chapterID,
      (httpResponse: HttpResponse<Array<Section>>) => {
        this.sections = httpResponse.body as Array<Section>;

      },
      (httpErrorResponse: HttpErrorResponse) => {
        this.message.info(JSON.stringify(httpErrorResponse))
      }
    )
  }

}
