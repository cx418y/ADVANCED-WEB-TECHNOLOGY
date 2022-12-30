import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpParams, HttpResponse} from "@angular/common/http";
import {EssayProblemsToBePeerGradedDTO} from "./dto/essayProblemsToBePeerGraded";
import {CommonService} from "../common/common.service";
import {SuccessfulPostResponse} from "../../reqrsp/SuccessfulPostResponse";
import {EssayProblemToBeDoneDTO} from "../do-problem/dto/EssayProblemToBeDoneDTO";
import {PageQueryResult} from "../../reqrsp/PageQueryResult";
import {EssayProblemToBeTeacherTAGradedDTO} from "./dto/essayProblemToBeTeacherTAGraded";

@Injectable({
  providedIn: 'root'
})
export class GradeProblemService {

  private myHeaders = new HttpHeaders().set("Authorization", this.commonService.getJwt());

  constructor(private http: HttpClient,
              private commonService: CommonService) {
  }

  /**
   * 学生根据课程编号，获得自己待评价的所有题
   * @param courseCode
   * @param pageNum
   * @param pageSize
   * @param next
   * @param error
   */
  public essayProblemsToBePeerGraded(courseCode: string,
                                     pageNum: number,
                                     pageSize: number,
                                     next: (httpResponse: HttpResponse<PageQueryResult<EssayProblemsToBePeerGradedDTO>>) => void,
                                     error: (httpErrorResponse: HttpErrorResponse) => void
  ) {

    let interfaceUrl: string = '/student/essayProblemsToBePeerGraded'

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

  /**
   * 教师根据课程编号，获得自己待评价的所有题
   * @param courseCode
   * @param pageNum
   * @param pageSize
   * @param next
   * @param error
   */
  public essayProblemsToBeTeacherTAGraded(courseCode: string,
                                          pageNum: number,
                                          pageSize: number,
                                          next: (httpResponse: HttpResponse<PageQueryResult<EssayProblemToBeTeacherTAGradedDTO>>) => void,
                                          error: (httpErrorResponse: HttpErrorResponse) => void
  ) {

    let interfaceUrl: string = '/teacherTA/essayProblemsToBeGraded'

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

  /**
   * 学生提交互评分数
   * @param essayProblemToBePeerGradedID
   * @param point
   */
  public peerGradeEssayProblem(essayProblemToBePeerGradedID: string,
                               point: number,
                               next: (httpResponse: HttpResponse<SuccessfulPostResponse>) => void,
                               error: (httpErrorResponse: HttpErrorResponse) => void) {

    let interfaceUrl = '/student/peerGradeEssayProblem'

    let body = {
      essayProblemToBePeerGradedID: essayProblemToBePeerGradedID,
      point: point
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

  /**
   * 提交互评分数
   * @param essayProblemToBePeerGradedID
   * @param point
   */
  public TeacherTAGradeEssayProblem(essayProblemToBeTeacherTAGradedID: string,
                                    point: number,
                                    next: (httpResponse: HttpResponse<SuccessfulPostResponse>) => void,
                                    error: (httpErrorResponse: HttpErrorResponse) => void) {

    let interfaceUrl = '/teacherTA/gradeEssayProblem'

    let body = {
      essayProblemToBeTeacherTAGradedID: essayProblemToBeTeacherTAGradedID,
      point: point
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
