import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TeacherCourseMemberViewComponent } from './teacher-course-member-view.component';

describe('TeacherCourseMemberViewComponent', () => {
  let component: TeacherCourseMemberViewComponent;
  let fixture: ComponentFixture<TeacherCourseMemberViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TeacherCourseMemberViewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TeacherCourseMemberViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
