import {Component, OnInit, Input, OnChanges, SimpleChanges} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms"
import {CommonService} from "../../../../services/common/common.service";
import {Chapter} from 'src/app/services/course/dtos/Chapter';
import {CourseService} from 'src/app/services/course/course.service';
import {SuccessfulPostResponse} from "../../../../reqrsp/SuccessfulPostResponse";
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpResponse} from "@angular/common/http";
import {NzMessageService} from "ng-zorro-antd/message";
import {asLiteral} from '@angular/compiler/src/render3/view/util';
import {Section} from 'src/app/services/course/dtos/Section';

@Component({
  selector: 'app-add-section',
  templateUrl: './add-section.component.html',
  styleUrls: ['./add-section.component.css']
})
export class AddSectionComponent implements OnInit, OnChanges {
  @Input()
  public courseCode: string = ''

  //上一次章节更新的时间，在"添加章"的组件中，如果添加了章，则"添加小节"组件的下拉框需要重新获取章
  //使用onChange钩子
  @Input()
  public chapterLastUpdated: Date | undefined = new Date()

  public chapters: Array<Chapter> = []

  validateForm!: FormGroup;

  constructor(private commonService: CommonService,
              private courseService: CourseService,
              private message: NzMessageService,
              private fb: FormBuilder) {
  }


  // 在刚加载时加尝试获得课程所有章节
  public getChapter() {
    this.courseService.getChapter(this.courseCode,
      (httpResponse: HttpResponse<Array<Chapter>>) => {
        this.chapters = httpResponse.body as Array<Chapter>;
      },
      (httpErrorResponse: HttpErrorResponse) => {
        this.message.info(JSON.stringify(httpErrorResponse))
      }
    )
  }

  ngOnInit(): void {
    this.getChapter();

    this.validateForm = this.fb.group({
      chapterID: [null, [Validators.required]],
      sectionName: [null, [Validators.required]],
      sectionNum: [null, [Validators.required, Validators.pattern('^[1-9][0-9]*$')]]
    });
  }

  ngOnChanges(changes: SimpleChanges): void {
    //如果在"添加小节"的组件里检测到了章节更新
    if (changes['chapterLastUpdated']) {
      this.getChapter()
    }
  }


  submitForm(): void {
    this.commonService.formValidation(this.validateForm);

    let section: Section = new Section();
    section.courseID = this.courseCode;
    section.sectionName = this.validateForm.value.sectionName;
    section.sectionNum = this.validateForm.value.sectionNum;
    section.chapterID = this.validateForm.value.chapterID;
    //创建要添加的章节对象


    //进入service层添加章节
    this.courseService.addSection(
      section,
      (httpResponse: HttpResponse<SuccessfulPostResponse>) => {
        this.message.success(httpResponse.body?.message as string);
      },
      (httpErrorResponse: HttpErrorResponse) => {
        this.message.error(JSON.stringify(httpErrorResponse.error))
      }
    )
  }


}
