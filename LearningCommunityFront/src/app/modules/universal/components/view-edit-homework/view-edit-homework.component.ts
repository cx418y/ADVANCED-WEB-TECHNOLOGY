import {Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {DetailedHomework} from "../../../../services/homework/dtos/DetailedHomework";
import {HomeworkService} from "../../../../services/homework/homework.service";
import {NzMessageService} from "ng-zorro-antd/message";
import {CommonService} from "../../../../services/common/common.service";
import {HistoryVersionDrawerComponent} from "./history-version-drawer/history-version-drawer.component";
import {BriefVersion} from "../../../../services/homework/dtos/BriefVersion";
import {DetailedVersion} from "../../../../services/homework/dtos/DetailedVersion";

@Component({
  selector: 'app-view-edit-homework',
  templateUrl: './view-edit-homework.component.html',
  styleUrls: ['./view-edit-homework.component.css']
})
export class ViewEditHomeworkComponent implements OnInit {

  //要查看的作业的homeworkID
  public homeworkID: string = ''

  public detailedHomework: DetailedHomework | undefined ;

  public latestBriefVersion: BriefVersion | undefined
  public historyBriefVersions: BriefVersion[] | undefined

  //编辑器上显示的内容
  public homeworkContent: string = ''
  //界面上显示的最后编辑者
  public fromUsername: string = ''
  //界面上显示的创建时间
  public createdTime: Date = new Date()

  //已修改，但是未保存的flag
  public notSaved: boolean = false;


  //查看历史版本的子组件
  @ViewChild(HistoryVersionDrawerComponent)
  public historyVersionDrawer: HistoryVersionDrawerComponent | undefined

  constructor(private route: ActivatedRoute,
              private homeworkService: HomeworkService,
              public commonService: CommonService,
              private message: NzMessageService) {

    //初始化homeworkID
    route.queryParams.subscribe(p => {
      this.homeworkID = p['homeworkID']
    })

  }

  ngOnInit(): void {
    //加载作业详细
    this.loadHomeworkDetails()
    //加载最新版本和历史版本
    this.loadAllBriefVersions()
  }

  /**
   * 加载作业详情
   * @private
   */
  private loadHomeworkDetails() {
    this.homeworkService.getHomeworkDetails(
      this.homeworkID,
      response => {
        this.detailedHomework = response.body as DetailedHomework
      },
      errorResponse => {
        this.message.error(JSON.stringify(errorResponse))
      }
    )
  }


  /**
   * 打开查看历史版本抽屉
   */
  public openHistoryVersionDrawer() {
    this.historyVersionDrawer?.open()
  }

  private loadAllBriefVersions() {
    this.homeworkService.getAllBriefVersions(
      this.homeworkID,
      response => {

        //历史版本简要
        this.historyBriefVersions = response.body?.oldVersions as BriefVersion[]

        //最新版本简要
        this.latestBriefVersion = response.body?.latestVersion as BriefVersion

        //获取最新版本详细
        this.homeworkService.getDetailedVersion(
          this.latestBriefVersion?.homeworkVersionID,
          response1 => {

            //设定页面上显示的最新版本的信息
            this.homeworkContent = response1.body?.content as string
            this.fromUsername = response1.body?.fromUsername as string
            this.createdTime = response1.body?.createdTime as Date

          },
          errorResponse => {
            this.message.error(JSON.stringify(errorResponse.error))
          }
        )

      },
      errorResponse => {
        this.message.error(JSON.stringify(errorResponse.error))
      }
    )
  }

  /**
   * 子组件发射历史作业版本的content给父组件，父组件在这里接收！
   */
  onHomeworkContentChanged(content: string) {
    this.homeworkContent = content

    //修改未保存flag
    this.notSaved=true
  }

  /**
   * 点击保存以后的调用
   */
  save() {
    this.homeworkService.saveHomework(
      this.homeworkID,
      this.homeworkContent,
      response => {
        this.message.success(response.body?.message as string)

        //重新加载最新的作业信息
        this.ngOnInit()

        //重新加载最新的历史版本信息
        this.historyVersionDrawer?.ngOnInit()

        //修改未保存的flag
        this.notSaved = false
      },
      errorResponse => {
        this.message.error(JSON.stringify(errorResponse.error))
      }
    )

  }


  onHomeworkContentLocalChange() {
    this.notSaved = true
  }

}
