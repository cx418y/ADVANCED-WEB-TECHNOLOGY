import {Component, Input, OnInit} from '@angular/core';
import {EssayProblemToBeDoneDTO} from "../../../../services/do-problem/dto/EssayProblemToBeDoneDTO";
import {CourseProblemService} from "../../../../services/course-problem/course-problem.service";
import {NzMessageService} from "ng-zorro-antd/message";
import {DoProblemService} from "../../../../services/do-problem/do-problem.service";
import {NzTableQueryParams} from "ng-zorro-antd/table";

@Component({
  selector: 'app-do-problem',
  templateUrl: './do-problem.component.html',
  styleUrls: ['./do-problem.component.css']
})
export class DoProblemComponent implements OnInit {

  @Input()
  public courseCode: string = ''
  public pageNum: number = 1
  public pageSize: number = 10
  public totalPage: number = 1
  public totalElement: number = 0

  public essayProblemToBeDoneList: EssayProblemToBeDoneDTO[] = []
  public answerList: string[] = []
  public isVisible: boolean[] = []

  public loading: boolean = false

  constructor(private doProblemService: DoProblemService,
              private message: NzMessageService) {
  }

  ngOnInit(): void {
    this.getEssayProblemsToBeDone();
  }

  public hasProblemToBeDone(): boolean {
    return this.essayProblemToBeDoneList.length > 0
  }

  public getEssayProblemsToBeDone() {
    this.loading = true
    this.doProblemService.essayProblemsToBeDone(
      this.courseCode,
      this.pageNum,
      this.pageSize,
      httpResponse => {
        this.loading = false;
        this.totalPage = httpResponse.body?.totalPage as number;
        this.totalElement = httpResponse.body?.totalElement as number;
        this.essayProblemToBeDoneList = httpResponse.body?.data as EssayProblemToBeDoneDTO[]
      },
      httpErrorResponse => {
        this.loading = false
        this.message.error(JSON.stringify(httpErrorResponse.error))
      }
    )
  }

  public submit(i: number) {
    if (!this.answerList[i]) {
      this.message.error("填写的答案不能为空");
      return
    }

    let answer: string = this.answerList[i];

    this.doProblemService.doEssayProblem(
      this.essayProblemToBeDoneList[i].essayProblemToBeDoneID,
      answer,
      httpResponse => {
        this.message.success(httpResponse.body?.message as string)
        this.essayProblemToBeDoneList.splice(i, 1)
        this.answerList.splice(i, 1)
        this.getEssayProblemsToBeDone()
      },
      httpErrorResponse => {
        this.message.error(JSON.stringify(httpErrorResponse.error))
      }
    )
  }

  public onQueryParamsChange(params: NzTableQueryParams) {
    const {pageSize, pageIndex, sort, filter} = params;
    this.pageSize = pageSize;
    this.pageNum = pageIndex;
    this.getEssayProblemsToBeDone()
  }

  public showModal(i: number) {
    this.isVisible[i] = true
  }

  public closeModal(i: number) {
    this.isVisible[i] = false
  }


}
