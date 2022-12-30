import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewCourseFileComponent } from './view-course-file.component';

describe('ViewCourseFileComponent', () => {
  let component: ViewCourseFileComponent;
  let fixture: ComponentFixture<ViewCourseFileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewCourseFileComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewCourseFileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
