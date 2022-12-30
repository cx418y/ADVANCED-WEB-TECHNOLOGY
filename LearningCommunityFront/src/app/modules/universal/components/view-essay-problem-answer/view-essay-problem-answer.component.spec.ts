import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewEssayProblemAnswerComponent } from './view-essay-problem-answer.component';

describe('ViewEssayProblemAnswerComponent', () => {
  let component: ViewEssayProblemAnswerComponent;
  let fixture: ComponentFixture<ViewEssayProblemAnswerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewEssayProblemAnswerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewEssayProblemAnswerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
