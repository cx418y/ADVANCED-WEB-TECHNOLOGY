import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-course-file',
  templateUrl: './course-file.component.html',
  styleUrls: ['./course-file.component.css']
})
export class CourseFileComponent implements OnInit {

  @Input()
  public allowFileUpload: boolean = false

  @Input()
  public courseCode: string = ''

  constructor() {
  }

  ngOnInit(): void {
  }

}
