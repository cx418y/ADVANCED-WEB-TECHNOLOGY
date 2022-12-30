import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddTeacherTaStudentComponent } from './add-teacher-ta-student.component';

describe('AddStudentComponent', () => {
  let component: AddTeacherTaStudentComponent;
  let fixture: ComponentFixture<AddTeacherTaStudentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddTeacherTaStudentComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddTeacherTaStudentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
