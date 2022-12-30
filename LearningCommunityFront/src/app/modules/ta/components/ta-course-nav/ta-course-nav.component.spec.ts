import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TACourseNavComponent } from './ta-course-nav.component';

describe('TACourseNavComponent', () => {
  let component: TACourseNavComponent;
  let fixture: ComponentFixture<TACourseNavComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TACourseNavComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TACourseNavComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
