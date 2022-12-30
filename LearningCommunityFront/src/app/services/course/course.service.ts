import {Injectable} from '@angular/core';
import {CommonService} from "../common/common.service";
import {CourseDTO} from "./dtos/CourseDTO";
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpParams, HttpResponse} from "@angular/common/http";
import {SuccessfulPostResponse} from "../../reqrsp/SuccessfulPostResponse";
import {CourseMember} from "./dtos/CourseMember";
import {Chapter} from './dtos/Chapter';
import {Section} from './dtos/Section';

@Injectable({
  providedIn: 'root'
})
export class CourseService {

  private myHeaders = new HttpHeaders().set("Authorization", this.commonService.getJwt());


  constructor(
    private commonService: CommonService,
    public http: HttpClient) {
  }

  /**
   * 教师，学生，助教获得和自己相关联的所有课程
   * @param next 成功后的回调
   * @param error 出错后的回调
   */
  public getMyRelatedCourses(
    next: (httpResponse: HttpResponse<Array<CourseDTO>>) => void,
    error: (httpErrorResponse: HttpErrorResponse) => void) {

    let interfaceUrl = '/myCourse'

    this.http.get(
      this.commonService.getBackendUrlPrefix() + interfaceUrl,
      {headers: this.myHeaders, observe: 'response'})
      .subscribe({
        next: next as (httpResponse: HttpResponse<any>) => void,
        error: error
      })
  }

  /**
   * 教师，学生，助教根据课程代码获得和自己相关联的所有课程
   * @param courseCode 课程代码
   * @param next 成功后的回调
   * @param error 出错后的回调
   */
  public getMyRelatedCoursesByCourseCode(
    courseCode: string,
    next: (httpResponse: HttpResponse<CourseDTO>) => void,
    error: (httpErrorResponse: HttpErrorResponse) => void) {

    let interfaceUrl = '/myCourseByCourseCode'

    let params = new HttpParams().set("courseCode", courseCode);

    this.http.get(
      this.commonService.getBackendUrlPrefix() + interfaceUrl,
      {headers: this.myHeaders, observe: 'response', params: params})
      .subscribe({
        next: next as (httpResponse: HttpResponse<any>) => void,
        error: error
      })
  }

  /**
   *
   * @param course
   * @param next
   * @param error
   */
  public addCourse(course: CourseDTO,
                   next: (httpResponse: HttpResponse<SuccessfulPostResponse>) => void,
                   error: (httpErrorResponse: HttpErrorResponse) => void) {


    let interfaceUrl = '/addCourse'

    this.http.post(
      this.commonService.getBackendUrlPrefix() + interfaceUrl,
      course,
      {headers: this.myHeaders, observe: 'response'})
      .subscribe({
        next: next as (httpResponse: HttpResponse<any>) => void,
        error: error
      })
  }


  /**
   * 获取课程的所有成员信息，可能包括老师，助教，学生
   */
  public getCourseMember(courseCode: string,
                         next: (httpResponse: HttpResponse<CourseMember[]>) => void,
                         error: (httpErrorResponse: HttpErrorResponse) => void) {

    let interfaceUrl = '/courseMember'
    const myParams = new HttpParams().set("courseCode", courseCode);

    this.http.get(this.commonService.getBackendUrlPrefix() + interfaceUrl,
      {headers: this.myHeaders, observe: 'response', params: myParams}
    ).subscribe({
      next: next as (httpResponse: HttpResponse<any>) => void,
      error: error
    })

  }

  /**
   * 给课程添加助教
   * @param courseCode 课程代码
   * @param taUsername 助教用户名
   * @param next 成功后回调
   * @param error 失败后回调
   */
  public inviteTA(courseCode: string,
                  taUsername: string,
                  next: (httpResponse: HttpResponse<SuccessfulPostResponse>) => void,
                  error: (httpErrorResponse: HttpErrorResponse) => void) {
    let interfaceUrl = '/addTAtoCourse'
    let data = {courseCode: courseCode, username: taUsername}

    this.http.post(
      this.commonService.getBackendUrlPrefix() + interfaceUrl,
      data,
      {headers: this.myHeaders, observe: 'response'})
      .subscribe({
        next: next as (httpResponse: HttpResponse<any>) => void,
        error: error
      })
  }

  public removeCourseStudentTA(courseCode: string,
                               username: string,
                               next: (httpResponse: HttpResponse<SuccessfulPostResponse>) => void,
                               error: (httpErrorResponse: HttpErrorResponse) => void) {

    let interfaceUrl = '/removeCourseStudentTA'
    let data = {courseCode: courseCode, username: username}

    this.http.post(
      this.commonService.getBackendUrlPrefix() + interfaceUrl,
      data,
      {headers: this.myHeaders, observe: 'response'})
      .subscribe({
        next: next as (httpResponse: HttpResponse<any>) => void,
        error: error
      })
  }


  /**
   * 退出课程
   */
  public exitCourse(courseCode: string,
                    next: (httpResponse: HttpResponse<SuccessfulPostResponse>) => void,
                    error: (httpErrorResponse: HttpErrorResponse) => void) {
    let interfaceUrl = '/exitCourse'
    let data = {courseCode: courseCode}

    this.http.post(
      this.commonService.getBackendUrlPrefix() + interfaceUrl,
      data,
      {headers: this.myHeaders, observe: 'response'})
      .subscribe({
        next: next as (httpResponse: HttpResponse<any>) => void,
        error: error
      })

  }

  /**
   * 加入课程
   */

  public joinCourse(courseCode: string,
                    next: (httpResponse: HttpResponse<SuccessfulPostResponse>) => void,
                    error: (httpErrorResponse: HttpErrorResponse) => void) {
    let interfaceUrl = '/joinCourse'
    let data = {courseCode: courseCode}

    this.http.post(
      this.commonService.getBackendUrlPrefix() + interfaceUrl,
      data,
      {headers: this.myHeaders, observe: 'response'})
      .subscribe({
        next: next as (httpResponse: HttpResponse<any>) => void,
        error: error
      })

  }

  /**
   * 获得课程的全部章节
   */
  public getChapter(courseCode: string,
                    next: (httpResponse: HttpResponse<Array<Chapter>>) => void,
                    error: (httpErrorResponse: HttpErrorResponse) => void) {
    let interfaceUrl = '/courseChapter'
    const myParams = new HttpParams().set("courseCode", courseCode);
    this.http.get(this.commonService.getBackendUrlPrefix() + interfaceUrl,
      {headers: this.myHeaders, observe: 'response', params: myParams}
    ).subscribe({
      next: next as (httpResponse: HttpResponse<any>) => void,
      error: error
    })
  }

  /**
   * 获得章的小节
   */
  public getSection(chapterID: string,
                    next: (httpResponse: HttpResponse<Array<Section>>) => void,
                    error: (httpErrorResponse: HttpErrorResponse) => void) {
    let interfaceUrl = '/chapterSection'
    const myParams = new HttpParams().set("chapterID", chapterID);
    this.http.get(this.commonService.getBackendUrlPrefix() + interfaceUrl,
      {headers: this.myHeaders, observe: 'response', params: myParams}
    ).subscribe({
      next: next as (httpResponse: HttpResponse<any>) => void,
      error: error
    })
  }


  /**
   *
   * @param chapter
   * @param next
   * @param error
   */
  public addChapter(chapter: Chapter,
                    next: (httpResponse: HttpResponse<SuccessfulPostResponse>) => void,
                    error: (httpErrorResponse: HttpErrorResponse) => void) {


    let interfaceUrl = '/addChapter'

    this.http.post(
      this.commonService.getBackendUrlPrefix() + interfaceUrl,
      chapter,
      {headers: this.myHeaders, observe: 'response'})
      .subscribe({
        next: next as (httpResponse: HttpResponse<any>) => void,
        error: error
      })
  }

  /**
   *
   * @param section
   * @param next
   * @param error
   */
  public addSection(section: Section,
                    next: (httpResponse: HttpResponse<SuccessfulPostResponse>) => void,
                    error: (httpErrorResponse: HttpErrorResponse) => void) {

    let interfaceUrl = '/addSection'

    this.http.post(
      this.commonService.getBackendUrlPrefix() + interfaceUrl,
      section,
      {headers: this.myHeaders, observe: 'response'})
      .subscribe({
        next: next as (httpResponse: HttpResponse<any>) => void,
        error: error
      })
  }

  /**
   * 学生出题
   * @param isStudent
   * @param courseCode
   * @param title
   * @param referenceAnswer
   * @param next
   * @param error
   */
  public addEssayProblem(isStudent: boolean, courseCode: string,
                         title: string,
                         referenceAnswer: string,
                         next: (httpResponse: HttpResponse<SuccessfulPostResponse>) => void,
                         error: (httpErrorResponse: HttpErrorResponse) => void) {

    let interfaceUrl = isStudent ? '/studentAddEssayProblem' : "/teacherAddEssayProblem"
    let data = {courseCode: courseCode, title: title, referenceAnswer: referenceAnswer}
    this.http.post(
      this.commonService.getBackendUrlPrefix() + interfaceUrl,
      data,
      {headers: this.myHeaders, observe: 'response'})
      .subscribe({
        next: next as (httpResponse: HttpResponse<any>) => void,
        error: error
      })
  }

  /**
   * 教师出题
   * @param courseCode
   * @param title
   * @param referenceAnswer
   * @param next
   * @param error
   */
  public teacherAddEssayProblem(courseName: string,
                                title: string,
                                referenceAnswer: string,
                                next: (httpResponse: HttpResponse<SuccessfulPostResponse>) => void,
                                error: (httpErrorResponse: HttpErrorResponse) => void) {

    let interfaceUrl = '/teacherAddEssayProblem'
    let data = {courseName: courseName, title: title, referenceAnswer: referenceAnswer}
    this.http.post(
      this.commonService.getBackendUrlPrefix() + interfaceUrl,
      data,
      {headers: this.myHeaders, observe: 'response'})
      .subscribe({
        next: next as (httpResponse: HttpResponse<any>) => void,
        error: error
      })
  }


  public getCourse(courseName:string,
                   pageNum:number,
                   pageSize:number,
                   next: (httpResponse: HttpResponse<any>) => void,
                   error: (httpErrorResponse: HttpErrorResponse) => void
                  ){
    let interfaceUrl = '/courses'
    const myParams = new HttpParams().set("courseName", courseName).set("pageNum",pageNum).set("pageSize",pageSize);
    this.http.get(this.commonService.getBackendUrlPrefix() + interfaceUrl,
      {headers: this.myHeaders, observe: 'response', params: myParams}
    ).subscribe({
      next: next as (httpResponse: HttpResponse<any>) => void,
      error: error
    })

  }

}
