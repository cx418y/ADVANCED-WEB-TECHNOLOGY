import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpParams, HttpResponse} from "@angular/common/http";
import {EssayProblemToBeDoneDTO} from "./dto/EssayProblemToBeDoneDTO";
import {CommonService} from "../common/common.service";
import {SuccessfulPostResponse} from "../../reqrsp/SuccessfulPostResponse";
import {PageQueryResult} from "../../reqrsp/PageQueryResult";

@Injectable({
  providedIn: 'root'
})
export class DoProblemService {

  private myHeaders = new HttpHeaders().set("Authorization", this.commonService.getJwt());

  constructor(private http: HttpClient,
              private commonService: CommonService) {
  }

  /**
   * 学生根据课程编号，获得自己待做的所有论述题
   * @param courseCode
   * @param pageNum
   * @param pageSize
   * @param next
   * @param error
   */
  public essayProblemsToBeDone(courseCode: string,
                               pageNum: number,
                               pageSize: number,
                               next: (httpResponse: HttpResponse<PageQueryResult<EssayProblemToBeDoneDTO>>) => void,
                               error: (httpErrorResponse: HttpErrorResponse) => void
  ) {

    let interfaceUrl: string = '/student/essayProblemsToBeDone'

    let params = new HttpParams()
      .set("courseCode", courseCode)
      .set("pageNum", pageNum)
      .set("pageSize", pageSize)

    this.http.get(
      this.commonService.getBackendUrlPrefix() + interfaceUrl,
      {observe: 'response', headers: this.myHeaders, params: params}
    ).subscribe({
      next: next as (httpResponse: HttpResponse<any>) => void,
      error: error
    })
  }

  public doEssayProblem(essayProblemToBeDoneID: string,
                        answer: string,
                        next: (httpResponse: HttpResponse<SuccessfulPostResponse>) => void,
                        error: (httpErrorResponse: HttpErrorResponse) => void) {

    let interfaceUrl = '/student/doEssayProblem'

    let body = {
      essayProblemToBeDoneID: essayProblemToBeDoneID,
      answer: answer
    }

    this.http.post(
      this.commonService.getBackendUrlPrefix() + interfaceUrl,
      body,
      {observe: 'response', headers: this.myHeaders}
    ).subscribe({
      next: next as (httpResponse: HttpResponse<any>) => void,
      error: error
    })

  }

}
