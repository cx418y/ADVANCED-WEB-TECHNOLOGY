import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TeacherGradeProblemComponent } from './teacher-grade-problem.component';

describe('GradeProblemComponent', () => {
  let component: TeacherGradeProblemComponent;
  let fixture: ComponentFixture<TeacherGradeProblemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TeacherGradeProblemComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TeacherGradeProblemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
