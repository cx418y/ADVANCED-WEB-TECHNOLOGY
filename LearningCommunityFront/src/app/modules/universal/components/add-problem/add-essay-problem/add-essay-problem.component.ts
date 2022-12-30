import {Component, Input, OnInit} from '@angular/core';
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {SuccessfulPostResponse} from "../../../../../reqrsp/SuccessfulPostResponse";
import {CourseService} from "../../../../../services/course/course.service";

@Component({
  selector: 'app-add-essay-problem',
  templateUrl: './add-essay-problem.component.html',
  styleUrls: ['./add-essay-problem.component.css']
})

export class AddEssayProblemComponent implements OnInit {
  @Input()
  public courseCode: string = '';

  @Input()
  public isStudent: boolean = false;

  public data: NewEssayProblem[] = []

  constructor(private courseService: CourseService) {
    this.add()
  }

  ngOnInit(): void {
  }

  delete(i: number) {
    this.data.splice(i, 1);
  }

  /**
   * 点击添加按钮后触发，添加一个论述题
   */
  add() {
    this.data.push(new NewEssayProblem('', '', 0))
  }

  /**
   * 提交
   */
  submit() {
    this.data.forEach((value, index) => {

      //手动检验一下是否为空
      if (!value.title) {
        value.message = '题目不能为空'
        value.status = 2
        return
      }
      if (!value.referenceAnswer) {
        value.message = '参考答案不能为空'
        value.status = 2
        return;
      }

      this.courseService.addEssayProblem(
        this.isStudent,
        this.courseCode,
        value.title,
        value.referenceAnswer,

        () => {
          value.status = 1;
          value.message = '成功，2秒后消失'
          setTimeout(() => this.delete(index), 2000)
        },
        (httpErrorResponse: HttpErrorResponse) => {
          value.status = 2;
          value.message = httpErrorResponse.error['message'] ? httpErrorResponse.error['message'] : 'Failed';
        }
      )


    })

  }

}


class NewEssayProblem {

  public title: string
  public referenceAnswer: string
  public status: number; //0:pending,1:success,2:failed
  public message: string = ''


  constructor(title: string, referenceAnswer: string, status: number) {
    this.title = title;
    this.referenceAnswer = referenceAnswer;
    this.status = status;
  }
}
