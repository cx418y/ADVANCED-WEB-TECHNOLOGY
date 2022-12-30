import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyHomeworkComponent } from './my-homework.component';

describe('MyHomeworkComponent', () => {
  let component: MyHomeworkComponent;
  let fixture: ComponentFixture<MyHomeworkComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MyHomeworkComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MyHomeworkComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
