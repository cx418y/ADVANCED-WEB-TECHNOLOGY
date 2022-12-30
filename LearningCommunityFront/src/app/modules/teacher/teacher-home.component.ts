import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {CommonService} from "../../services/common/common.service";
import {CourseDTO} from "../../services/course/dtos/CourseDTO";
import {CourseService} from "../../services/course/course.service";
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {NzMessageService} from "ng-zorro-antd/message";
import {LoginRequiredComponent} from "../LoginRequiredComponent";
import {UserInfoServiceService} from "../../services/user-info/user-info-service.service";

@Component({
  selector: 'app-teacher',
  templateUrl: './teacher-home.component.html',
  styleUrls: ['./teacher-home.component.css']
})
export class TeacherHomeComponent extends LoginRequiredComponent implements OnInit {

  public myRelatedCourses: Array<CourseDTO> = []

  //用户名和头像
  public username = this.commonService.getUsernameByJwt();
  public myAvatarSrc = ''

  constructor(public router: Router,
              public commonService: CommonService,
              private courseService: CourseService,
              private userInfoServiceService: UserInfoServiceService,
              private message: NzMessageService) {
    super(router, commonService)
  }

  ngOnInit(): void {

    //获得我关联的课程
    this.getMyRelatedCourses();

    //获得我的头像
    this.userInfoServiceService.getSelfAvatarSrc(
      httpResponse => {
        this.myAvatarSrc = httpResponse.body['avatar']
      },
      httpErrorResponse => {
      }
    )
  }

  /**
   * 在教师的页面开始加载时，就尝试获取自己的课程
   */
  public getMyRelatedCourses() {
    this.courseService.getMyRelatedCourses(
      (httpResponse: HttpResponse<Array<CourseDTO>>) => {
        console.log(`获取到课程`)
        console.log(httpResponse.body)
        this.myRelatedCourses = httpResponse.body as Array<CourseDTO>;
      },
      (httpErrorResponse: HttpErrorResponse) => {
        this.message.info(JSON.stringify(httpErrorResponse))
      }
    )
  }


  public logout(): void {
    this.commonService.deleteJwt();
    this.router.navigate(['universal', 'userLogin'])
  }

}
