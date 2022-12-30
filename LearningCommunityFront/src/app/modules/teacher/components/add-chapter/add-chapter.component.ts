import {Component, OnInit, Input} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms"
import {CommonService} from "../../../../services/common/common.service";
import {Chapter} from 'src/app/services/course/dtos/Chapter';
import {CourseService} from 'src/app/services/course/course.service';
import {SuccessfulPostResponse} from "../../../../reqrsp/SuccessfulPostResponse";
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpResponse} from "@angular/common/http";
import {NzMessageService} from "ng-zorro-antd/message";
import {asLiteral} from '@angular/compiler/src/render3/view/util';
import {TeacherCourseNavComponent} from "../teacher-course-nav/teacher-course-nav.component";

@Component({
  selector: 'app-add-chapter',
  templateUrl: './add-chapter.component.html',
  styleUrls: ['./add-chapter.component.css']
})
export class AddChapterComponent implements OnInit {
  @Input()
  public courseCode: string | undefined

  validateForm!: FormGroup;

  constructor(private teacherCourseNavComponent: TeacherCourseNavComponent,
              private commonService: CommonService,
              private courseService: CourseService,
              private message: NzMessageService,
              private fb: FormBuilder) {
  }


  ngOnInit(): void {
    this.validateForm = this.fb.group({
      chapterName: [null, [Validators.required]],
      chapterNum: [null, [Validators.required, Validators.pattern('[1-9][0-9]*')]]
    });
  }


  submitForm(): void {
    this.commonService.formValidation(this.validateForm);

    //创建要添加的章节对象
    let chapter: Chapter = new Chapter();
    chapter.chapterName = this.validateForm.value.chapterName;
    chapter.chapterNum = this.validateForm.value.chapterNum;
    chapter.courseCode = this.courseCode;


    //进入service层添加章节
    this.courseService.addChapter(
      chapter,
      (httpResponse: HttpResponse<SuccessfulPostResponse>) => {
        this.message.success(httpResponse.body?.message as string);

        //修改上一次章节更新的时间
        this.teacherCourseNavComponent.chapterLastUpdated = new Date()
      },
      (httpErrorResponse: HttpErrorResponse) => {
        this.message.error(JSON.stringify(httpErrorResponse.error))
      }
    )
  }


}
