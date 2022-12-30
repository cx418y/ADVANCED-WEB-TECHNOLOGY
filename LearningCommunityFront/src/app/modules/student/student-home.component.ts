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
  selector: 'app-student-home',
  templateUrl: './student-home.component.html',
  styleUrls: ['./student-home.component.css']
})
export class StudentHomeComponent extends LoginRequiredComponent implements OnInit {
  public myRelatedCourses: Array<CourseDTO> = []

  public username: string = this.commonService.getUsernameByJwt()
  public myAvatarSrc = ''


  constructor(public router: Router,
              public commonService: CommonService,
              private courseService: CourseService,
              private userInfoService: UserInfoServiceService,
              private message: NzMessageService) {
    super(router, commonService)
  }

  // 在页面加载时就尝试获取自己的课程
  public getMyRelatedCourses() {
    this.courseService.getMyRelatedCourses(
      (httpResponse: HttpResponse<Array<CourseDTO>>) => {
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


  ngOnInit(): void {
    this.getMyRelatedCourses();
    this.userInfoService.getSelfAvatarSrc(
      httpResponse => {
        this.myAvatarSrc = httpResponse.body['avatar']
      },
      httpErrorResponse => {
        this.message.error(httpErrorResponse.error)
      }
    )

  }

}
