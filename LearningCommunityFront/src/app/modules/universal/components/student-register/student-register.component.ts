import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpResponse} from "@angular/common/http";
import {Router} from '@angular/router';
import {CommonService} from "../../../../services/common/common.service";
import {SuccessfulPostResponse} from "../../../../reqrsp/SuccessfulPostResponse";
import {UnSuccessfulPostResponse} from "../../../../reqrsp/UnSuccessfulPostResponse";
import {NzMessageService} from "ng-zorro-antd/message";
import {Md5} from "ts-md5";


@Component({
  selector: 'app-student-register',
  templateUrl: './student-register.component.html',
  styleUrls: ['./student-register.component.css']
})
export class StudentRegisterComponent implements OnInit {

  validateForm!: FormGroup;
  result: any = {};

  constructor(private fb: FormBuilder,
              public http: HttpClient,
              private router: Router,
              private commonService: CommonService,
              private message: NzMessageService) {
  }


  ngOnInit(): void {
    this.validateForm = this.fb.group({
      email: [null, [Validators.email, Validators.required]],
      password: [null, [Validators.required]],
      checkPassword: [null, [Validators.required, this.confirmationValidator]],
      username: [null, [Validators.required, Validators.pattern("[0-9a-zA-Z]{6,12}")]],
      realname: [null, [Validators.required, Validators.pattern("[^\x00-\xff]{2,4}")]],
    });
  }

  submitForm(): void {
    this.commonService.formValidation(this.validateForm);

    this.validateForm.value.password = new Md5().appendStr(this.validateForm.value.password).end();

    //克隆一个validateForm.value，不要提交checkPassword属性
    let submitData = JSON.parse(JSON.stringify(this.validateForm.value))
    delete submitData.checkPassword

    console.log('submit', submitData);

    let interfaceUrl = "/studentRegister";

    this.http.post(
      this.commonService.getBackendUrlPrefix() + interfaceUrl,
      submitData,
      {observe: "response"})
      .subscribe(
        {
          next: (httpResponse: HttpResponse<Object>) => {
            let data: SuccessfulPostResponse = httpResponse.body as SuccessfulPostResponse;
            this.message.success(data.message)

            this.router.navigate(['universal', 'userLogin'])

          },
          error: (httpResponse: HttpErrorResponse) => {
            let data: UnSuccessfulPostResponse = httpResponse.error as UnSuccessfulPostResponse;
            this.message.error(JSON.stringify(data));
          }
        })
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

}
