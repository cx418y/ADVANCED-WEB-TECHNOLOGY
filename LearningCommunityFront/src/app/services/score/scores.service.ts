import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpParams, HttpResponse} from "@angular/common/http";
import {CommonService} from "../common/common.service";
import { ScoreDTO } from './dto/scoreDTO';
import { SuccessfulLoginResponse } from 'src/app/modules/universal/reqrsp/SuccessfulLoginResponse';
import { SuccessfulPostResponse } from 'src/app/reqrsp/SuccessfulPostResponse';

@Injectable({
  providedIn: 'root'
})
export class ScoresService {

  private myHeaders = new HttpHeaders().set("Authorization", this.commonService.getJwt());

  constructor(private http: HttpClient,
              private commonService: CommonService) {
  }

  /**
   * 学生/助教/老师获得全部课程全部学生评分
   * @param courseCode 
   * @param next 
   * @param error 
   */
  public getScores(courseCode:string,
                  next: (response: HttpResponse<ScoreDTO>) => void,
                  error: (errorResponse: HttpErrorResponse) => void){
    
    let interfaceUrl = '/scores'
    let params = new HttpParams().set('courseCode', courseCode)

    this.http.get(
      this.commonService.getBackendUrlPrefix() + interfaceUrl,
      {headers: this.myHeaders, observe: 'response', params: params}
    ).subscribe({
      next: next as (httpResponse: HttpResponse<any>) => void,
      error: error
    })
  }

  public giveScore(
                  username:string,
                  courseCode:string,
                  score:number,
                  next: (response: HttpResponse<SuccessfulPostResponse>) => void,
                  error: (errorResponse: HttpErrorResponse) => void
  ){

    let interfaceUrl = '/giveScore'

    let body = {username:username,courseCode:courseCode,score:score}

    this.http.post(
      this.commonService.getBackendUrlPrefix() + interfaceUrl,
      body,
      {headers: this.myHeaders, observe: 'response'}
    ).subscribe({
      next: next as (httpResponse: HttpResponse<any>) => void,
      error: error
    })

  }

}
