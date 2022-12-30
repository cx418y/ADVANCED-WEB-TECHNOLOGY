import {Component, OnInit, Input} from '@angular/core';
import {EssayProblemsToBePeerGradedDTO} from "../../../../services/gradeProblem/dto/essayProblemsToBePeerGraded";
import {GradeProblemService} from "../../../../services/gradeProblem/grade-problem.service";
import {NzMessageService} from "ng-zorro-antd/message";
import {NzTableQueryParams} from "ng-zorro-antd/table";

@Component({
  selector: 'app-grade-problem',
  templateUrl: './grade-problem.component.html',
  styleUrls: ['./grade-problem.component.css']
})
export class GradeProblemComponent implements OnInit {

  @Input()
  public courseCode: string = ''

  public pageNum: number = 1
  public pageSize: number = 10
  public totalPage: number = 1
  public totalElement: number = 0

  public essayProblemsToBePeerGradedList: EssayProblemsToBePeerGradedDTO[] = []
  public gradeList: number[] = []

  public loading: boolean = false;

  constructor(private gradeProblemService: GradeProblemService,
              private message: NzMessageService) {
  }

  ngOnInit(): void {
    this.getEssayProblemsToBePeerGraded()
  }

  public hasProblemToBeGraded(): boolean {
    return this.essayProblemsToBePeerGradedList.length > 0
  }

  public getEssayProblemsToBePeerGraded() {
    this.loading = true;
    this.gradeProblemService.essayProblemsToBePeerGraded(
      this.courseCode,
      this.pageNum,
      this.pageSize,
      httpResponse => {
        this.loading = false;
        this.totalPage = httpResponse.body?.totalPage as number
        this.totalElement=httpResponse.body?.totalElement as number
        this.essayProblemsToBePeerGradedList = httpResponse.body?.data as EssayProblemsToBePeerGradedDTO[]
        this.gradeList = new Array(this.essayProblemsToBePeerGradedList.length).fill(0);
      },
      httpErrorResponse => {
        this.loading = false
        this.message.error(JSON.stringify(httpErrorResponse.error))
      }
    )
  }


  public submit(i: number) {
    if (this.gradeList[i] === null || this.gradeList[i] === undefined) {
      this.message.error("分数不能为空");
      return
    }

    if (this.gradeList[i] > this.essayProblemsToBePeerGradedList[i].maxPoint) {
      this.message.error("评分不能超过最大分数");
      return
    }

    let grade: number = this.gradeList[i];

    this.gradeProblemService.peerGradeEssayProblem(
      this.essayProblemsToBePeerGradedList[i].essayProblemToBePeerGradedID,
      grade,
      httpResponse => {
        this.message.success(httpResponse.body?.message as string)
        this.essayProblemsToBePeerGradedList.splice(i, 1)
        this.gradeList.splice(i, 1)
        this.getEssayProblemsToBePeerGraded()
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
    this.getEssayProblemsToBePeerGraded();
  }
}
