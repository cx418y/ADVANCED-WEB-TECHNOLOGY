import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpParams, HttpResponse} from "@angular/common/http";
import {CommonService} from "../common/common.service";
import {HomeworkDTO} from './dtos/homework';
import {SuccessfulPostResponse} from "../../reqrsp/SuccessfulPostResponse";
import {DetailedHomework} from "./dtos/DetailedHomework";
import {BriefVersionCollection} from "./dtos/BriefVersionCollection";
import {DetailedVersion} from "./dtos/DetailedVersion";
import { MyHomework } from './dtos/MyHomework';

@Injectable({
  providedIn: 'root'
})
export class HomeworkService {
  private myHeaders = new HttpHeaders().set("Authorization", this.commonService.getJwt());

  constructor(private http: HttpClient,
              private commonService: CommonService) {
  }


  /**
   * 根据sectionID获得作业简要信息
   * @param sectionID
   * @param next
   * @param error
   */
  public getAllHomeworkBriefing(sectionID: string,
                                next: (httpResponse: HttpResponse<Array<HomeworkDTO>>) => void,
                                error: (httpErrorResponse: HttpErrorResponse) => void
  ) {

    let interfaceUrl: string = '/allHomeworkBriefing'

    let params = new HttpParams()
      .set("sectionID", sectionID)

    this.http.get(
      this.commonService.getBackendUrlPrefix() + interfaceUrl,
      {observe: 'response', headers: this.myHeaders, params: params}
    ).subscribe({
      next: next as (httpResponse: HttpResponse<any>) => void,
      error: error
    })
  }

  public releaseHomework(courseCode: string,
                         sectionID: string,
                         title: string,
                         details: string,
                         oldMax: number,
                         oldTTL: number,
                         next: (httpResponse: HttpResponse<SuccessfulPostResponse>) => void,
                         error: (httpErrorResponse: HttpErrorResponse) => void
  ) {

    let
      interfaceUrl = '/releaseHomework'

    let
      body = {
        courseCode: courseCode,
        sectionID: sectionID,
        title: title,
        details: details,
        oldMax: oldMax,
        oldTTL: oldTTL
      }

    this.http
      .post(
        this.commonService.getBackendUrlPrefix() + interfaceUrl,
        body,
        {
          observe: 'response',
          headers: this.myHeaders
        }
      ).subscribe({
      next: next as (httpResponse: HttpResponse<any>) => void,
      error: error
    })

  }


  /**
   * 根据homeworkID，获得homework的详细信息
   * @param homeworkID
   * @param next
   * @param error
   */
  public getHomeworkDetails(homeworkID: string,
                            next: (response: HttpResponse<DetailedHomework>) => void,
                            error: (errorResponse: HttpErrorResponse) => void
  ) {

    let interfaceUrl = '/homeworkDetails'

    let params = new HttpParams().set('homeworkID', homeworkID)

    this.http.get(
      this.commonService.getBackendUrlPrefix() + interfaceUrl,
      {headers: this.myHeaders, observe: 'response', params: params}
    ).subscribe({
      next: next as (httpResponse: HttpResponse<any>) => void,
      error: error
    })

  }

  /**
   * 根据homeworkID，获得所有的版本简要信息
   * @param homeworkID
   * @param next
   * @param error
   */
  public getAllBriefVersions(homeworkID: string,
                             next: (response: HttpResponse<BriefVersionCollection>) => void,
                             error: (errorResponse: HttpErrorResponse) => void
  ) {

    let interfaceUrl = '/allVersionBriefing'

    let params = new HttpParams().set('homeworkID', homeworkID)

    this.http.get(
      this.commonService.getBackendUrlPrefix() + interfaceUrl,
      {headers: this.myHeaders, observe: 'response', params: params}
    ).subscribe({
      next: next as (httpResponse: HttpResponse<any>) => void,
      error: error
    })
  }

  public getDetailedVersion(versionID: string,
                            next: (response: HttpResponse<DetailedVersion>) => void,
                            error: (errorResponse: HttpErrorResponse) => void
  ) {

    let interfaceUrl = '/versionDetails'

    let params = new HttpParams().set('homeworkVersionID', versionID)

    this.http.get(
      this.commonService.getBackendUrlPrefix() + interfaceUrl,
      {headers: this.myHeaders, observe: 'response', params: params}
    ).subscribe({
      next: next as (httpResponse: HttpResponse<any>) => void,
      error: error
    })

  }


  public saveHomework(homeworkID: string,
                      content: string,
                      next: (response: HttpResponse<SuccessfulPostResponse>) => void,
                      error: (errorResponse: HttpErrorResponse) => void
  ) {
    let interfaceUrl = '/editHomework'

    let body = {homeworkID: homeworkID, content: content}

    this.http.post(
      this.commonService.getBackendUrlPrefix() + interfaceUrl,
      body,
      {headers: this.myHeaders, observe: 'response'}
    ).subscribe({
      next: next as (httpResponse: HttpResponse<any>) => void,
      error: error
    })

  }


  /**
   * 学生获得自己的全部作业
   * @param next 
   * @param error 
   */
  public getMyHomeWork(
                      next: (response: HttpResponse<MyHomework>) => void,
                      error: (errorResponse: HttpErrorResponse) => void
  ){
    let interfaceUrl = '/myHomework'
    this.http.get(
      this.commonService.getBackendUrlPrefix() + interfaceUrl,
      {headers: this.myHeaders, observe: 'response'}
    ).subscribe({
      next: next as (httpResponse: HttpResponse<any>) => void,
      error: error
    })
  }
}



