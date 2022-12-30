import {Component, OnInit, Input} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {CourseService} from 'src/app/services/course/course.service';
import {SuccessfulPostResponse} from "../../../../reqrsp/SuccessfulPostResponse";
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpResponse} from "@angular/common/http";
import {NzMessageService} from "ng-zorro-antd/message";
import {CommonService} from "../../../../services/common/common.service";

@Component({
  selector: 'app-add-problem',
  templateUrl: './add-problem.component.html',
  styleUrls: ['./add-problem.component.css']
})
export class AddProblemComponent implements OnInit {

  @Input()
  public courseCode: string = ''

  @Input()
  public title: string = ''

  @Input()
  public isStudent: boolean = false;

  validateForm!: FormGroup;

  constructor(private courseService: CourseService,
              private message: NzMessageService,
              private fb: FormBuilder,
              private commonService: CommonService) {
  }

  ngOnInit(): void {

    this.validateForm = this.fb.group({
      problemType: [null, [Validators.required]],
      title: [null, [Validators.required]],
      referenceAnswer: [null, [Validators.required]]
    });
  }

  submitForm(): void {
    this.commonService.formValidation(this.validateForm);
    console.log(this.courseCode);
    console.log(this.validateForm.value.title);
    console.log(this.validateForm.value.referenceAnswer);


    this.courseService.addEssayProblem(
      this.isStudent,
      this.courseCode,
      this.validateForm.value.title,
      this.validateForm.value.referenceAnswer,

      (httpResponse: HttpResponse<SuccessfulPostResponse>) => {
        console.log("success")
        this.message.success(httpResponse.body?.message as string);
      },
      (httpErrorResponse: HttpErrorResponse) => {
        this.message.error(JSON.stringify(httpErrorResponse.error))
      }
    )
  }

}
