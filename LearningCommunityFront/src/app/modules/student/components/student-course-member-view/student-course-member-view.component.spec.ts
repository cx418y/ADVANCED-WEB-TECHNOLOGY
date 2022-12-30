import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StudentCourseMemberViewComponent } from './student-course-member-view.component';

describe('StudentCourseMemberViewComponent', () => {
  let component: StudentCourseMemberViewComponent;
  let fixture: ComponentFixture<StudentCourseMemberViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StudentCourseMemberViewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StudentCourseMemberViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
