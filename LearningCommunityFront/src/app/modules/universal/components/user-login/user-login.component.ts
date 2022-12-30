import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpResponse} from "@angular/common/http";
import {Router} from '@angular/router';
import {NzMessageService} from "ng-zorro-antd/message";
import {CommonService} from "../../../../services/common/common.service";
import {Md5} from "ts-md5";
import {SuccessfulLoginResponse} from "../../reqrsp/SuccessfulLoginResponse";
import jwt_decode from "jwt-decode";

const httpOptions = {headers: new HttpHeaders({'Content-Type': 'application/json'})};

@Component({
  selector: 'app-user-login',
  templateUrl: './user-login.component.html',
  styleUrls: ['./user-login.component.css']
})
export class UserLoginComponent implements OnInit {
  validateForm!: FormGroup;
  radioValue = "Student";
  result: any = {};
  submitData: any = {}

  constructor(private fb: FormBuilder,
              public http: HttpClient,
              private router: Router,
              private message: NzMessageService,
              private commonService: CommonService
  ) {
  }

  ngOnInit(): void {
    this.validateForm = this.fb.group({
      username: [null, [Validators.required]],
      password: [null, [Validators.required]],
      radioValue: [null, [Validators.required, Validators.pattern("(Teacher)|(TA)|(Student)")]]
    });
  }

  //提交表单
  userLogin(): void {

    if (!this.validateForm.valid) {
      Object.values(this.validateForm.controls).forEach(control => {
        if (control.invalid) {
          control.markAsDirty();
          control.updateValueAndValidity({onlySelf: true});
        }
      });
      return
    }

    //根据用户是老师或助教得到对应的接口url
    let interfaceUrl: string = "/auth/login"
    this.submitData.username = this.validateForm.value.username;
    this.submitData.password = new Md5().appendStr(this.validateForm.value.password).end();

    this.http.post(this.commonService.getBackendUrlPrefix() + interfaceUrl, this.submitData, {observe: 'response'}).subscribe({
      next: (httpResponse: HttpResponse<Object>) => {
        let data: SuccessfulLoginResponse = httpResponse.body as SuccessfulLoginResponse

        //存储jwt
        this.commonService.setJwt(data.jwt);

        //检查后台返回的用户身份，是否与用户选择的一致
        let jwtPayload: any = jwt_decode(data.jwt);
        let actualRole = jwtPayload.role.toLowerCase()
        let roleChosen = this.validateForm.value.radioValue.toLowerCase()

        if (actualRole === roleChosen) {
          this.message.success('登陆成功');
          this.router.navigate([actualRole]);
        } else {
          this.message.error(`角色选择错误，实际应该是${actualRole}，而你却选择了${roleChosen}`)
        }

      },
      error: (httpErrorResponse: HttpErrorResponse) => {
        this.message.error(JSON.stringify(httpErrorResponse.error))
      }
    })

  }


}
