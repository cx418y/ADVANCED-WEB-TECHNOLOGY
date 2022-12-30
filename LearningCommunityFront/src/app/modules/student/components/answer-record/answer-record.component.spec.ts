import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnswerRecordComponent } from './answer-record.component';

describe('AnswerRecordComponent', () => {
  let component: AnswerRecordComponent;
  let fixture: ComponentFixture<AnswerRecordComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AnswerRecordComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AnswerRecordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
