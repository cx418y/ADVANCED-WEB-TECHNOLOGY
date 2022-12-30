import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpResponse} from "@angular/common/http";
import {Router} from '@angular/router';
import {CommonService} from "../../../../services/common/common.service";
import {Md5} from "ts-md5";
import {SuccessfulPostResponse} from "../../../../reqrsp/SuccessfulPostResponse";
import {NzMessageService} from "ng-zorro-antd/message";

const httpOptions = {headers: new HttpHeaders({'Content-Type': 'application/json'})};

@Component({
  selector: 'app-add-student',
  templateUrl: './add-teacher-ta-student.component.html',
  styleUrls: ['./add-teacher-ta-student.component.css']
})
export class AddTeacherTaStudentComponent implements OnInit {

  validateForm!: FormGroup;
  public type: string = "Teacher"

  result: any = {};

  constructor(private fb: FormBuilder,
              public http: HttpClient,
              private router: Router,
              private commonService: CommonService,
              private message: NzMessageService) {
  }


  submitForm(): void {
    this.commonService.formValidation(this.validateForm);

    let interfaceUrl = "/admin/addTeacherTAStudent"
    this.validateForm.value.password = new Md5().appendStr(this.validateForm.value.password).end()

    //在提交数据对象中删掉checkPassword
    let submitData = JSON.parse(JSON.stringify(this.validateForm.value))
    delete submitData.checkPassword

    const myHeaders = new HttpHeaders().set("Authorization", this.commonService.getJwt());

    this.http.post(
      this.commonService.getBackendUrlPrefix() + interfaceUrl,
      submitData,
      {headers: myHeaders, observe: 'response'}
    ).subscribe(
      {
        next: (httpResponse: HttpResponse<Object>) => {
          let data: SuccessfulPostResponse = httpResponse.body as SuccessfulPostResponse
          this.message.success(data.message);
        },
        error: (httpErrorResponse: HttpErrorResponse) => {
          this.message.error(JSON.stringify(httpErrorResponse.error))
        }
      }
    )
  }

  updateConfirmValidator(): void {
    /** wait for refresh value */
    Promise.resolve().then(() => this.validateForm.controls['checkPassword'].updateValueAndValidity());
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
      email: [null, [Validators.email, Validators.required]],
      password: [null, [Validators.required]],
      checkPassword: [null, [Validators.required, this.confirmationValidator]],
      username: [null, [Validators.required, Validators.pattern("[0-9a-zA-Z]{6,12}")]],
      realname: [null, [Validators.required, Validators.pattern("[^\x00-\xff]{2,4}")]],
      type: [null, [Validators.required, Validators.pattern("(Teacher)|(TA)|(Student)")]]
    });
  }

}
