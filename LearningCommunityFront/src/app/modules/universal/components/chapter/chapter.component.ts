import {Component, OnInit, Input, OnChanges, SimpleChanges} from '@angular/core';
import {CommonService} from "../../../../services/common/common.service";
import {CourseDTO} from "../../../../services/course/dtos/CourseDTO";
import {CourseService} from "../../../../services/course/course.service";
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {Chapter} from 'src/app/services/course/dtos/Chapter';
import {NzMessageService} from "ng-zorro-antd/message";


@Component({
  selector: 'app-chapter',
  templateUrl: './chapter.component.html',
  styleUrls: ['./chapter.component.css']
})
export class ChapterComponent implements OnInit, OnChanges {
  @Input()
  public courseCode: string = ''
  @Input()
  public isStudent : Boolean = false

  public chapters: Array<Chapter> = []

  constructor(private courseService: CourseService, private message: NzMessageService) {
  }

  ngOnInit(): void {
  }

  /**
   * 当监测到courseCode或chapterLastUpdated发生改变时，就重新获取章节！
   * @param changes
   */
  ngOnChanges(changes: SimpleChanges): void {
    this.getChapter();
  }

  reload(): void {
    this.getChapter()
  }


  // 在刚加载时加尝试获得课程所有章节
  public getChapter() {
    this.courseService.getChapter(this.courseCode,
      (httpResponse: HttpResponse<Array<Chapter>>) => {
        this.chapters = httpResponse.body as Array<Chapter>;
      },
      (httpErrorResponse: HttpErrorResponse) => {
        this.message.info(JSON.stringify(httpErrorResponse.error))
      }
    )
  }

}
