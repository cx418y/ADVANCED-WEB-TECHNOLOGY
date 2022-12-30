import {Component, forwardRef, OnInit} from '@angular/core';
import {NG_VALUE_ACCESSOR} from "@angular/forms";
import {CommonService} from "../../../../services/common/common.service";

@Component({
  selector: 'app-edit-essay-problem-answer',
  templateUrl: './edit-essay-problem-answer.component.html',
  styleUrls: ['./edit-essay-problem-answer.component.css'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => EditEssayProblemAnswerComponent),
      multi: true
    }
  ]
})
export class EditEssayProblemAnswerComponent implements OnInit {

  constructor(public commonService: CommonService) {
  }

  public isVisible: boolean = false;
  private innerValue: string = ''

  ngOnInit(): void {
  }

  showModal() {
    this.isVisible = true;
  }

  closeModal() {
    this.isVisible = false;
  }

  private onTouchedCallback = () => {
  };
  private onChangeCallback = (_: any) => {
  };

  writeValue(value: any) {

    if (value !== this.innerValue) {
      this.innerValue = value;
    }
  }

  registerOnChange(fn: any) {
    this.onChangeCallback = fn;
  }

  registerOnTouched(fn: any) {
    this.onTouchedCallback = fn;
  }

  get value(): any {
    return this.innerValue;
  }

  set value(v: any) {
    if (v !== this.innerValue) {
      this.innerValue = v;
      this.onChangeCallback(v);
    }
  }


}
