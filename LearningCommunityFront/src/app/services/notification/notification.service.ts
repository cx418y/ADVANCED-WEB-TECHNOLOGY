import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpParams, HttpResponse} from "@angular/common/http";
import {CommonService} from "../common/common.service";
import {HomeworkNotification} from "./dto/HomeworkNotification";
import {SuccessfulPostResponse} from "../../reqrsp/SuccessfulPostResponse";

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  private myHeaders = new HttpHeaders().set("Authorization", this.commonService.getJwt());

  constructor(private http: HttpClient,
              private commonService: CommonService) {
  }

  /**
   * 根据courseCode获得所有通知
   * @param courseCode
   * @param next
   * @param error
   */
  public getAllHomeworkNotifications(courseCode: string,
                                     next: (response: HttpResponse<HomeworkNotification[]>) => void,
                                     error: (errorResponse: HttpErrorResponse) => void) {

    let interfaceUrl = '/allHomeworkNotifications'

    let params = new HttpParams().set('courseCode', courseCode)

    this.http.get(
      this.commonService.getBackendUrlPrefix() + interfaceUrl,
      {headers: this.myHeaders, observe: 'response', params: params}
    ).subscribe({
      next: next as (httpResponse: HttpResponse<any>) => void,
      error: error
    })
  }

  /**
   * 根据notification删除通知
   * @param homeworkNotificationID
   * @param next
   * @param error
   */
  public dismissHomeworkNotification(homeworkNotificationID: string,
                                     next: (response: HttpResponse<SuccessfulPostResponse>) => void,
                                     error: (errorResponse: HttpErrorResponse) => void) {
    let interfaceUrl = '/dismissHomeworkNotification'

    let body = {homeworkNotificationID: homeworkNotificationID}

    this.http.delete(
      this.commonService.getBackendUrlPrefix() + interfaceUrl,
      {headers: this.myHeaders, observe: 'response', body: body}
    ).subscribe({
      next: next as (httpResponse: HttpResponse<any>) => void,
      error: error
    })
  }

}
