import {Component, OnInit, Input} from '@angular/core';
import {
  EssayProblemToBeTeacherTAGradedDTO
} from "../../../../services/gradeProblem/dto/essayProblemToBeTeacherTAGraded";
import {GradeProblemService} from "../../../../services/gradeProblem/grade-problem.service";
import {NzMessageService} from "ng-zorro-antd/message";
import {NzTableQueryParams} from "ng-zorro-antd/table";


@Component({
  selector: 'app-teacher-grade-problem',
  templateUrl: './teacher-grade-problem.component.html',
  styleUrls: ['./teacher-grade-problem.component.css']
})
export class TeacherGradeProblemComponent implements OnInit {

  @Input()
  public courseCode: string = ''

  public pageNum: number = 1
  public pageSize: number = 10
  public totalPage: number = 1
  public totalElement: number = 0


  public essayProblemToBeTeacherTAGradedList: EssayProblemToBeTeacherTAGradedDTO [] = []
  public gradeList: number[] = []

  public loading: boolean = false

  constructor(private gradeProblemService: GradeProblemService,
              private message: NzMessageService) {
  }

  ngOnInit(): void {
    this.getEssayProblemsToBeTeacherTAGraded()
  }


  public hasProblemToBeGraded(): boolean {
    return this.essayProblemToBeTeacherTAGradedList.length > 0
  }

  public getEssayProblemsToBeTeacherTAGraded() {
    this.loading = true;
    this.gradeProblemService.essayProblemsToBeTeacherTAGraded(
      this.courseCode,
      this.pageNum,
      this.pageSize,
      httpResponse => {
        this.loading = false;
        this.totalPage = httpResponse.body?.totalPage as number
        this.totalElement = httpResponse.body?.totalElement as number
        this.essayProblemToBeTeacherTAGradedList = httpResponse.body?.data as EssayProblemToBeTeacherTAGradedDTO[]
        this.gradeList = new Array(this.essayProblemToBeTeacherTAGradedList.length).fill(0);
      },
      httpErrorResponse => {
        this.loading = false;
        this.message.error(JSON.stringify(httpErrorResponse.error))
      }
    )
  }

  public onQueryParamsChange(params: NzTableQueryParams) {
    const {pageSize, pageIndex, sort, filter} = params;
    this.pageSize = pageSize;
    this.pageNum = pageIndex;
    this.getEssayProblemsToBeTeacherTAGraded();
  }

  public submit(i: number) {
    if (this.gradeList[i] === null || this.gradeList[i] === undefined) {
      this.message.error("分数不能为空");
      return
    }

    if (this.gradeList[i] > this.essayProblemToBeTeacherTAGradedList[i].maxPoint) {
      this.message.error("评分不能超过最大分数");
      return
    }

    let grade: number = this.gradeList[i];

    this.gradeProblemService.TeacherTAGradeEssayProblem(
      this.essayProblemToBeTeacherTAGradedList[i].essayProblemToBeTeacherTAGradedID,
      grade,
      httpResponse => {
        this.message.success(httpResponse.body?.message as string)
        this.essayProblemToBeTeacherTAGradedList.splice(i, 1)
        this.gradeList.splice(i, 1)
        this.getEssayProblemsToBeTeacherTAGraded()
      },
      httpErrorResponse => {
        this.message.error(JSON.stringify(httpErrorResponse.error))
      }
    )
  }

}
