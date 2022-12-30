import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StudentCourseNavComponent } from './student-course-nav.component';

describe('StudentCourseNavComponent', () => {
  let component: StudentCourseNavComponent;
  let fixture: ComponentFixture<StudentCourseNavComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StudentCourseNavComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StudentCourseNavComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
