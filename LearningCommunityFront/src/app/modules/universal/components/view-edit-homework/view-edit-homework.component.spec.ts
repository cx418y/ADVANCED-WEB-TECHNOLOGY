import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewEditHomeworkComponent } from './view-edit-homework.component';

describe('ViewEditHomeworkComponent', () => {
  let component: ViewEditHomeworkComponent;
  let fixture: ComponentFixture<ViewEditHomeworkComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewEditHomeworkComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewEditHomeworkComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
