import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpParams, HttpResponse} from "@angular/common/http";
import {CommonService} from "../common/common.service";
import {UserInfoDTO} from "./dtos/UserInfoDTO";
import {SuccessfulPostResponse} from "../../reqrsp/SuccessfulPostResponse";

@Injectable({
  providedIn: 'root'
})
export class UserInfoServiceService {

  private myHeaders = new HttpHeaders().set("Authorization", this.commonService.getJwt());

  constructor(public http: HttpClient,
              public commonService: CommonService) {
  }

  /**
   * 查询自己的个人信息
   * @param next
   * @param error
   */
  public getSelfUserInfo(next: (httpResponse: HttpResponse<UserInfoDTO>) => void,
                         error: (httpErrorResponse: HttpErrorResponse) => void) {
    let interfaceUrl = '/userInfo'

    this.http.get(
      this.commonService.getBackendUrlPrefix() + interfaceUrl,
      {headers: this.myHeaders, observe: "response"}
    ).subscribe({
      next: next as (httpResponse: HttpResponse<any>) => void,
      error: error
    })
  }

  /**
   * 添加自己的个人信息
   * @param email
   * @param next
   * @param error
   */
  public updateSelfUserInfo(email: string,
                            next: (httpResponse: HttpResponse<SuccessfulPostResponse>) => void,
                            error: (httpErrorResponse: HttpErrorResponse) => void) {

    let interfaceUrl = '/updateUserInfo'
    const params = new HttpParams().set("email", email);
    this.http.put(
      this.commonService.getBackendUrlPrefix() + interfaceUrl,
      "",
      {headers: this.myHeaders, observe: 'response', params: params}
    ).subscribe({
      next: next as (httpResponse: HttpResponse<any>) => void,
      error: error
    })
  }


  /**
   * 获得自己的头像
   * @param next
   * @param error
   */
  public getSelfAvatarSrc(next: (httpResponse: HttpResponse<any>) => void,
                          error: (httpErrorResponse: HttpErrorResponse) => void) {

    let interfaceUrl = '/userAvatar'

    this.http.get(
      this.commonService.getBackendUrlPrefix() + interfaceUrl,
      {headers: this.myHeaders, observe: 'response'}
    ).subscribe({
      next: next,
      error: error
    })

  }

  /**
   * 更新自己的个人头像
   * @param avatarSrc
   * @param next
   * @param error
   */
  public updateSelfAvatarSrc(avatarSrc: string,
                             next: (httpResponse: HttpResponse<SuccessfulPostResponse>) => void,
                             error: (httpErrorResponse: HttpErrorResponse) => void) {

    let interfaceUrl = '/updateAvatar'
    this.http.put(
      this.commonService.getBackendUrlPrefix() + interfaceUrl,
      {avatar: avatarSrc},
      {headers: this.myHeaders, observe: 'response'}
    ).subscribe({
      next: next as (httpResponse: HttpResponse<any>) => void,
      error: error
    })
  }
}
