import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TACourseMemberViewComponent } from './ta-course-member-view.component';

describe('TACourseMemberViewComponent', () => {
  let component: TACourseMemberViewComponent;
  let fixture: ComponentFixture<TACourseMemberViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TACourseMemberViewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TACourseMemberViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
