import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TeacherCourseNavComponent } from './teacher-course-nav.component';

describe('TeacherCourseNavComponent', () => {
  let component: TeacherCourseNavComponent;
  let fixture: ComponentFixture<TeacherCourseNavComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TeacherCourseNavComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TeacherCourseNavComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
