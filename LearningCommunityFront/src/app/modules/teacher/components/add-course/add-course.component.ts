import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpResponse} from "@angular/common/http";
import {Router} from "@angular/router";
import {CommonService} from "../../../../services/common/common.service";
import {NzMessageService} from "ng-zorro-antd/message";
import {Md5} from "ts-md5";
import {SuccessfulPostResponse} from "../../../../reqrsp/SuccessfulPostResponse";
import {CourseService} from "../../../../services/course/course.service";
import {CourseDTO} from "../../../../services/course/dtos/CourseDTO";

@Component({
  selector: 'app-add-course',
  templateUrl: './add-course.component.html',
  styleUrls: ['./add-course.component.css']
})
export class AddCourseComponent implements OnInit {

  validateForm!: FormGroup;
  public type: string = "Teacher"

  result: any = {};

  constructor(private fb: FormBuilder,
              public http: HttpClient,
              private router: Router,
              private commonService: CommonService,
              private courseService: CourseService,
              private message: NzMessageService) {
  }


  submitForm(): void {
    this.commonService.formValidation(this.validateForm);

    //创建要添加的课程对象
    let course: CourseDTO = new CourseDTO();
    course.courseName = this.validateForm.value.courseName;
    course.courseCode = this.validateForm.value.courseCode;
    course.description = this.validateForm.value.description;

    //进入service层添加课程
    this.courseService.addCourse(
      course,
      (httpResponse: HttpResponse<SuccessfulPostResponse>) => {
        this.message.success(httpResponse.body?.message as string);
      },
      (httpErrorResponse: HttpErrorResponse) => {
        this.message.error(JSON.stringify(httpErrorResponse.error))
      }
    )
  }

  confirmationValidator = (control: FormControl): { [s: string]: boolean } => {
    if (!control.value) {
      return {required: true};
    } else if (control.value !== this.validateForm.controls['password'].value) {
      return {confirm: true, error: true};
    }
    return {};
  };


  ngOnInit(): void {
    this.validateForm = this.fb.group({
      courseCode: [null, [Validators.required, Validators.pattern('[A-Z]{4}[0-9]{6}')]],
      courseName: [null, [Validators.required]],
      description: [null, [Validators.required]]
    });
  }
}
