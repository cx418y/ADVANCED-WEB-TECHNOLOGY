import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {BriefVersion} from "../../../../../services/homework/dtos/BriefVersion";
import {HomeworkService} from "../../../../../services/homework/homework.service";
import {NzMessageService} from "ng-zorro-antd/message";
import {DetailedHomework} from "../../../../../services/homework/dtos/DetailedHomework";

@Component({
  selector: 'app-history-version-drawer',
  templateUrl: './history-version-drawer.component.html',
  styleUrls: ['./history-version-drawer.component.css']
})
export class HistoryVersionDrawerComponent implements OnInit {

  public visible: boolean = false

  @Output()
  public homeworkContentChanged = new EventEmitter()

  @Input()
  public historyBriefVersions: BriefVersion[] | undefined;

  @Input()
  public detailedHomework: DetailedHomework | undefined;

  constructor(private homeworkService: HomeworkService,
              private message: NzMessageService) {
  }

  ngOnInit(): void {
  }

  close(): void {
    this.visible = false
  }

  open(): void {
    console.log(this.historyBriefVersions)
    this.visible = true
  }


  backtrackHistoryVersion(homeworkVersionID: string) {
    this.homeworkService.getDetailedVersion(
      homeworkVersionID,
      response => {
        this.message.success("回溯成功，请查看")
        this.homeworkContentChanged.emit(response.body?.content)
      },
      errorResponse => {
        this.message.error(JSON.stringify(errorResponse.error))
      }
    )
  }

  historyVersionDescription(version: BriefVersion) {
    return `${version.fromUsername} \n
            创建于：${version.createdTime}\n
            即将过期于：${version.expireTime}`
  }

}
