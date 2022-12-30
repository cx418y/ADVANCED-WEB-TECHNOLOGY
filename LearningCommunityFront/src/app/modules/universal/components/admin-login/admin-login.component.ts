import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpResponse} from "@angular/common/http";
import {Router} from '@angular/router';
import {Md5} from "ts-md5";
import {SuccessfulLoginResponse} from "../../reqrsp/SuccessfulLoginResponse";
import {CommonService} from "../../../../services/common/common.service";
import {NzMessageService} from "ng-zorro-antd/message";
import jwt_decode from "jwt-decode"

const httpOptions = {headers: new HttpHeaders({'Content-Type': 'application/json'})};

@Component({
  selector: 'app-admin-login',
  templateUrl: './admin-login.component.html',
  styleUrls: ['./admin-login.component.css']
})
export class AdminLoginComponent implements OnInit {
  validateForm!: FormGroup;
  result: any = {};
  submitData: any = {};

  constructor(private fb: FormBuilder,
              public http: HttpClient,
              private router: Router,
              private commonService: CommonService,
              private message: NzMessageService) {
  }

  ngOnInit(): void {
    this.validateForm = this.fb.group({
      username: [null, [Validators.required]],
      password: [null, [Validators.required]],
      remember: [true]
    });
  }

  submitForm(): void {

    this.commonService.formValidation(this.validateForm);

    //根据用户是老师或助教得到对应的接口url
    let interfaceUrl: string = "/auth/login"
    this.submitData.username = this.validateForm.value.username;
    this.submitData.password = new Md5().appendStr(this.validateForm.value.password).end();

    this.http.post(this.commonService.getBackendUrlPrefix() + interfaceUrl, this.submitData, {observe: 'response'}).subscribe({
      next: (httpResponse: HttpResponse<Object>) => {
        let data: SuccessfulLoginResponse = httpResponse.body as SuccessfulLoginResponse

        //存储jwt
        this.commonService.setJwt(data.jwt);

        //解析获取得到的jwt payload
        let jwtPayload: any = jwt_decode(data.jwt);

        if (jwtPayload.role.toLowerCase() === 'admin') {
          this.message.success('登陆成功');
          this.router.navigate(['admin']);
        } else {
          this.message.error(`此账号不是管理员，而是${jwtPayload.role}`)
        }
      },
      error: (httpErrorResponse: HttpErrorResponse) => {
        this.message.error(JSON.stringify(httpErrorResponse.error))
      }
    })
  }
}
