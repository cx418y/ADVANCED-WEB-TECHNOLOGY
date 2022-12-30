import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CourseConfigComponent } from './course-config.component';

describe('CourseConfigComponent', () => {
  let component: CourseConfigComponent;
  let fixture: ComponentFixture<CourseConfigComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CourseConfigComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CourseConfigComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
