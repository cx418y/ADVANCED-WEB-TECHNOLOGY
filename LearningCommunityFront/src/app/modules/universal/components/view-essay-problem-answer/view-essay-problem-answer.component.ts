import {Component, forwardRef, OnInit} from '@angular/core';
import {NG_VALUE_ACCESSOR} from "@angular/forms";
import {CommonService} from "../../../../services/common/common.service";

@Component({
  selector: 'app-view-essay-problem-answer',
  templateUrl: './view-essay-problem-answer.component.html',
  styleUrls: ['./view-essay-problem-answer.component.css'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => ViewEssayProblemAnswerComponent),
      multi: true
    }
  ]
})
export class ViewEssayProblemAnswerComponent implements OnInit {

  constructor(public commonService: CommonService) {
  }

  ngOnInit(): void {
  }

  public isVisible: boolean = false;
  private innerValue: any = ''


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
