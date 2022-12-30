import {Component, OnInit, Input} from '@angular/core';
import {Section} from 'src/app/services/course/dtos/Section';
import {NzMessageService} from "ng-zorro-antd/message";
import {HomeworkService} from 'src/app/services/homework/homework.service';
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {HomeworkDTO} from 'src/app/services/homework/dtos/homework';
import {CommonService} from "../../../../services/common/common.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-homework-list',
  templateUrl: './homework-list.component.html',
  styleUrls: ['./homework-list.component.css']
})
export class HomeworkListComponent implements OnInit {
  @Input()
  public sectionID: string = ''

  public allHomework: Array<HomeworkDTO> = []

  constructor(private message: NzMessageService,
              private homeworkService: HomeworkService
  ) {
  }


  ngOnInit(): void {
    this.getSection()
  }

  public getSection() {
    this.homeworkService.getAllHomeworkBriefing(this.sectionID,
      (httpResponse: HttpResponse<Array<HomeworkDTO>>) => {
        this.allHomework = httpResponse.body as Array<HomeworkDTO>;
      },
      (httpErrorResponse: HttpErrorResponse) => {
        this.message.info(JSON.stringify(httpErrorResponse))
      }
    )
  }


  public jumpToViewEditHomework(homeworkID: string) {
    window.open(`/universal/viewEditHomework?homeworkID=${homeworkID}`)
  }


}
