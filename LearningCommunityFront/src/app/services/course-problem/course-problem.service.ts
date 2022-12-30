import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpParams, HttpResponse} from "@angular/common/http";
import {CommonService} from "../common/common.service";
import {EssayProblemToHandOutDTO} from "./dto/EssayProblemToHandOutDTO";
import {SuccessfulPostResponse} from "../../reqrsp/SuccessfulPostResponse";

@Injectable({
  providedIn: 'root'
})
export class CourseProblemService {

  private myHeaders = new HttpHeaders().set("Authorization", this.commonService.getJwt());

  constructor(private http: HttpClient,
              private commonService: CommonService) {
  }


  /**
   * 教师获得课程中所有的论述题
   * @param courseCode
   * @param next
   * @param error
   */
  public courseEssayProblems(courseCode: string,
                             next: (httpResponse: HttpResponse<EssayProblemDTO[]>) => void,
                             error: (httpErrorResponse: HttpErrorResponse) => void
  ) {

    let interfaceUrl: string = '/courseEssayProblems'
    let params = new HttpParams().set("courseCode", courseCode);

    this.http.get(
      this.commonService.getBackendUrlPrefix() + interfaceUrl,
      {observe: 'response', headers: this.myHeaders, params: params}
    ).subscribe({
      next: next as (httpResponse: HttpResponse<any>) => void,
      error: error
    })
  }




  public handoutEssayProblems(essayProblemToHandOutList: EssayProblemToHandOutDTO[],
                              next: (httpResponse: HttpResponse<SuccessfulPostResponse>) => void,
                              error: (httpErrorResponse: HttpErrorResponse) => void) {
    let interfaceUrl: string = '/handoutEssayProblems'

    this.http.post(
      this.commonService.getBackendUrlPrefix() + interfaceUrl,
      essayProblemToHandOutList,
      {observe: 'response', headers: this.myHeaders}
    ).subscribe({
      next: next as (httpResponse: HttpResponse<any>) => void,
      error: error
    })

  }
}
