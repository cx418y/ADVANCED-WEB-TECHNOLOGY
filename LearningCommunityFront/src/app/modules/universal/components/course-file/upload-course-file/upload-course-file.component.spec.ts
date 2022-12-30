import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UploadCourseFileComponent } from './upload-course-file.component';

describe('UploadCourseFileComponent', () => {
  let component: UploadCourseFileComponent;
  let fixture: ComponentFixture<UploadCourseFileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UploadCourseFileComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UploadCourseFileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
