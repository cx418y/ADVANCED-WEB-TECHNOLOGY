import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpParams, HttpResponse} from "@angular/common/http";
import {CourseConfigDTO} from "./dto/CourseConfigDTO";
import {CommonService} from "../common/common.service";
import {SuccessfulPostResponse} from "../../reqrsp/SuccessfulPostResponse";

@Injectable({
  providedIn: 'root'
})
export class CourseConfigService {

  private myHeaders = new HttpHeaders().set("Authorization", this.commonService.getJwt());

  constructor(private http: HttpClient,
              private commonService: CommonService) {
  }

  /**
   * 教师根据课程代码，得到自己教的课程的设置
   * @param courseCode
   * @param next
   * @param error
   */
  public teacherGetCourseConfig(courseCode: string,
                                next: (httpResponse: HttpResponse<CourseConfigDTO>) => void,
                                error: (httpErrorResponse: HttpErrorResponse) => void): void {
    let interfaceUrl = "/courseConfig";
    let params = new HttpParams().set("courseCode", courseCode);

    this.http.get(
      this.commonService.getBackendUrlPrefix() + interfaceUrl,
      {headers: this.myHeaders, observe: 'response', params: params}
    ).subscribe({
      next: next as (httpResponse: HttpResponse<any>) => void,
      error: error
    })
  }

  /**
   * 教师根据课程代码，设置自己的课程
   * @param courseCode
   * @param courseConfig
   * @param next
   * @param error
   */
  public teacherConfigCourse(courseCode: string,
                             courseConfig: CourseConfigDTO,
                             next: (httpResponse: HttpResponse<SuccessfulPostResponse>) => void,
                             error: (httpErrorResponse: HttpErrorResponse) => void) {

    let interfaceUrl = "/configMyCourse";
    let body = {courseCode: courseCode, allowStudentAddProblem: courseConfig.allowStudentAddProblem};

    this.http.put(
      this.commonService.getBackendUrlPrefix() + interfaceUrl,
      body,
      {headers: this.myHeaders, observe: 'response'}
    ).subscribe({
      next: next as (httpResponse: HttpResponse<any>) => void,
      error: error
    })

  }


}
