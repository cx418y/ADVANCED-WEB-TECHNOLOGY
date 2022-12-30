import {Component, forwardRef, OnInit, Input} from '@angular/core';
import {FormBuilder, FormGroup, NG_VALUE_ACCESSOR, Validators} from "@angular/forms";
import {CommonService} from "../../../../services/common/common.service";
import {HomeworkService} from "../../../../services/homework/homework.service";
import {NzMessageService} from "ng-zorro-antd/message";
import {EssayProblemToBeDoneDTO} from "../../../../services/do-problem/dto/EssayProblemToBeDoneDTO";
import {log} from "ng-zorro-antd/core/logger";


@Component({
  selector: 'app-add-homework',
  templateUrl: './add-homework.component.html',
  styleUrls: ['./add-homework.component.css'],
})
export class AddHomeworkComponent implements OnInit {
  @Input()
  public courseCode: string = ''
  @Input()
  public sectionID: string = ''

  public validateFrom!: FormGroup

  constructor(public commonService: CommonService, public homeworkService: HomeworkService,
              private message: NzMessageService,
              private fb: FormBuilder) {
    this.validateFrom = this.fb.group({
      newHomeworkTitle: [null, [Validators.required]],
      newHomeworkDetails: [null, [Validators.required]],
      oldMax: [null, [Validators.required]],
      oldTTL: [null, [Validators.required]]
    })
  }

  ngOnInit(): void {
    console.log('Received sectionID:' + this.sectionID)
  }

  public isVisible: boolean = false;

  showModal() {
    this.isVisible = true;
  }

  closeModal() {
    this.isVisible = false;
  }

  public submit() {
    this.commonService.formValidation(this.validateFrom)
    console.log(this.validateFrom)

    this.homeworkService.releaseHomework(
      this.courseCode,
      this.sectionID,
      this.validateFrom.value.newHomeworkTitle,
      this.validateFrom.value.newHomeworkDetails,
      this.validateFrom.value.oldMax,
      this.validateFrom.value.oldTTL * 3600 * 24,
      httpResponse => {
        this.message.success(httpResponse.body?.message as string)
        this.isVisible = false
      },
      httpErrorResponse => {
        this.message.error(JSON.stringify(httpErrorResponse.error))
      }
    )
  }

  public cancel() {
    this.isVisible = false;
  }

}
