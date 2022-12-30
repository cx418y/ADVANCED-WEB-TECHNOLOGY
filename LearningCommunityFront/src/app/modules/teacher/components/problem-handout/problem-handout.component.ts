import {Component, Input, OnInit} from '@angular/core';
import {CourseProblemService} from "../../../../services/course-problem/course-problem.service";
import {NzMessageService} from "ng-zorro-antd/message";
import {EssayProblemToHandOutDTO} from "../../../../services/course-problem/dto/EssayProblemToHandOutDTO";

@Component({
  selector: 'app-problem-handout',
  templateUrl: './problem-handout.component.html',
  styleUrls: ['./problem-handout.component.css']
})
export class ProblemHandoutComponent implements OnInit {

  @Input()
  public courseCode: string = ''

  public essayProblemList: EssayProblemDTO[] = []

  public essayProblemToHandOutList: EssayProblemToHandOutDTO[] = []

  public essayProblemToHandOutTitleList: string[] = []

  constructor(private courseProblemService: CourseProblemService,
              private message: NzMessageService) {
  }

  ngOnInit(): void {
    this.getAllEssayProblems();
  }

  reload(): void {
    this.getAllEssayProblems();
  }

  getAllEssayProblems() {
    this.courseProblemService.courseEssayProblems(
      this.courseCode,
      httpResponse => {
        this.essayProblemList = httpResponse.body as EssayProblemDTO[]
      },
      httpErrorResponse => {
        this.message.error(JSON.stringify(httpErrorResponse));
      }
    )
  }

  handoutEssayProblems() {
    this.courseProblemService.handoutEssayProblems(
      this.essayProblemToHandOutList,
      httpResponse => {
        this.message.success(httpResponse.body?.message as string)
        this.essayProblemToHandOutList = []
        this.essayProblemToHandOutTitleList = []
      },
      httpErrorResponse => {
        this.message.error(JSON.stringify(httpErrorResponse));
      }
    )
  }

  pickEssayProblem(i: number) {
    let problem: EssayProblemDTO = this.essayProblemList[i]
    this.essayProblemToHandOutList.push(new EssayProblemToHandOutDTO(problem.essayProblemID, true, 5))
    this.essayProblemToHandOutTitleList.push(problem.title)
  }

  dropEssayProblemToHandOut(i: number) {
    this.essayProblemToHandOutTitleList.splice(i, 1);
    this.essayProblemToHandOutList.splice(i, 1);
  }

}
