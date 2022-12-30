import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpParams, HttpResponse} from "@angular/common/http";
import {CommonService} from "../common/common.service";
import {EssayProblemFinishedDTO} from "./dto/EssayProblemFinishedDTO";
import {PageQueryResult} from "../../reqrsp/PageQueryResult";

@Injectable({
  providedIn: 'root'
})
export class FinishedProblemService {

  private myHeaders = new HttpHeaders().set("Authorization", this.commonService.getJwt());

  constructor(private http: HttpClient,
              private commonService: CommonService) {
  }

  /**
   * 学生获得自己已经完成（已经评阅过的）题目（答题记录）
   * @param courseCode
   * @param pageNum
   * @param pageSize
   * @param next
   * @param error
   */
  public getEssayProblemFinished(courseCode: string,
                                 pageNum: number,
                                 pageSize: number,
                                 next: (httpResponse: HttpResponse<PageQueryResult<EssayProblemFinishedDTO>>) => void,
                                 error: (httpErrorResponse: HttpErrorResponse) => void) {

    let interfaceUrl: string = '/student/essayProblemFinished'

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

}
