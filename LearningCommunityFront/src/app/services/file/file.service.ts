import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpParams, HttpResponse} from "@angular/common/http";
import {CourseFileDTO} from "./dto/CourseFileDTO";
import {CommonService} from "../common/common.service";
import {PageQueryResult} from "../../reqrsp/PageQueryResult";
import {SuccessfulPostResponse} from "../../reqrsp/SuccessfulPostResponse";

@Injectable({
  providedIn: 'root'
})
export class FileService {

  private myHeaders = new HttpHeaders().set("Authorization", this.commonService.getJwt());

  constructor(private http: HttpClient,
              private commonService: CommonService) {
  }


  /**
   * 获取课程的文件
   * @param courseCode
   * @param pageNum
   * @param pageSize
   * @param next
   * @param error
   */
  public listCourseFiles(courseCode: string,
                         pageNum: number,
                         pageSize: number,
                         next: (httpResponse: HttpResponse<PageQueryResult<CourseFileDTO>>) => void,
                         error: (httpErrorResponse: HttpErrorResponse) => void) {
    let interfaceUrl = `/${courseCode}/files`
    let params = new HttpParams()
      .set("pageNum", pageNum)
      .set("pageSize", pageSize)

    this.http
      .get(
        this.commonService.getBackendUrlPrefix() + interfaceUrl,
        {observe: "response", headers: this.myHeaders, params: params}
      ).subscribe({
      next: next as (httpResponse: HttpResponse<any>) => void,
      error: error
    })

  }


  /**
   * 删除课程中的文件
   * @param courseCode
   * @param fileName
   * @param next
   * @param error
   */
  public deleteFile(courseCode: string,
                    fileName: string,
                    next: (httpResponse: HttpResponse<SuccessfulPostResponse>) => void,
                    error: (httpErrorResponse: HttpErrorResponse) => void) {
    let interfaceUrl = `/${courseCode}/files/${fileName}`

    this.http
      .delete(
        this.commonService.getBackendUrlPrefix() + interfaceUrl,
        {observe: "response", headers: this.myHeaders})
      .subscribe({
        next: next as (httpResponse: HttpResponse<any>) => void,
        error: error
      })
  }
}
