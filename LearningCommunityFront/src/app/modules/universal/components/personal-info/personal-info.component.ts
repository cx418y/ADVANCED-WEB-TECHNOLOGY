import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {HttpClient, HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {Router} from "@angular/router";
import {CommonService} from "../../../../services/common/common.service";
import {NzMessageService} from "ng-zorro-antd/message";
import {UserInfoServiceService} from "../../../../services/user-info/user-info-service.service";
import {UserInfoDTO} from "../../../../services/user-info/dtos/UserInfoDTO";
import {SuccessfulPostResponse} from "../../../../reqrsp/SuccessfulPostResponse";
import {NzUploadFile} from "ng-zorro-antd/upload";

@Component({
  selector: 'app-personal-info',
  templateUrl: './personal-info.component.html',
  styleUrls: ['./personal-info.component.css']
})
export class PersonalInfoComponent implements OnInit {


  public avatarSrc: string = ''


  public userInfo: UserInfoDTO = new UserInfoDTO();

  validateForm!: FormGroup;
  result: any = {};

  /**
   * 用户选择完头像后，会调用这个函数
   * @param nzUploadFile
   */
  public chooseAvatar = (nzUploadFile: NzUploadFile) => {

    const getBase64 = (file: NzUploadFile) => {
      return new Promise((resolve, reject) => {
        let reader = new FileReader();
        reader.readAsDataURL(file as unknown as Blob)
        reader.onload = () => {
          resolve(reader.result)
        }
        reader.onerror = () => {
          reject(reader.error)
        }
      })
    }

    let re = new RegExp('image/[a-zA-Z0-9]+')

    if (!re.test(nzUploadFile.type as string)) {
      this.message.error('选择的文件应该是图片')
      return false;
    }

    if (nzUploadFile.size as number > 1024 * 1024) {
      this.message.error('上传的头像不应该大于1MB')
      return false;
    }

    getBase64(nzUploadFile)
      .then(value => {
        this.avatarSrc = value as string
      })
      .catch(reason => {
        this.message.error(reason)
      })

    return false;
  }


  constructor(private fb: FormBuilder,
              public http: HttpClient,
              private router: Router,
              private commonService: CommonService,
              private userInfoService: UserInfoServiceService,
              private message: NzMessageService) {

    //获得用户自己的个人信息
    this.getUserInfo();
    this.getAvatar();
  }

  ngOnInit(): void {
    this.validateForm = this.fb.group({
      email: [null, [Validators.required, Validators.email]],
    });
  }

  submitForm(): void {
    this.commonService.formValidation(this.validateForm);
    this.updateUserInfo()
  }

  /**
   * 获得用户自己的头像
   */
  getAvatar(): void {
    this.userInfoService.getSelfAvatarSrc(
      httpResponse => {
        if (!httpResponse.body['avatar']) {
          this.avatarSrc = ''
        } else {
          this.avatarSrc = httpResponse.body['avatar'] as string
        }
      },
      httpErrorResponse => {
        this.message.error(JSON.stringify(httpErrorResponse.error))
      }
    )
  }

  /**
   * 用户更新自己的头像
   */
  updateAvatar(): void {
    if (!this.avatarSrc) {
      this.message.error("请先选择头像")
      return
    }
    this.userInfoService.updateSelfAvatarSrc(
      this.avatarSrc,
      (httpResponse: HttpResponse<SuccessfulPostResponse>) => {
        this.message.success(httpResponse.body?.message as string);
      },
      (httpErrorResponse: HttpErrorResponse) => {
        this.message.error(JSON.stringify(httpErrorResponse))
      }
    )
  }

  /**
   * 获得用户自己的个人信息
   */
  getUserInfo(): void {
    this.userInfoService.getSelfUserInfo(
      (httpResponse: HttpResponse<UserInfoDTO>) => {
        this.userInfo = httpResponse.body as UserInfoDTO
      },
      (httpErrorResponse: HttpErrorResponse) => {
        this.message.error(JSON.stringify(httpErrorResponse))
      }
    )
  }

  /**
   * 更新用户自己的个人信息
   */
  updateUserInfo(): void {
    this.userInfoService.updateSelfUserInfo(
      this.userInfo.email,
      (httpResponse: HttpResponse<SuccessfulPostResponse>) => {
        this.message.success(httpResponse.body?.message as string);
      },
      (httpErrorResponse: HttpErrorResponse) => {
        this.message.error(JSON.stringify(httpErrorResponse))
      }
    )
  }

}
