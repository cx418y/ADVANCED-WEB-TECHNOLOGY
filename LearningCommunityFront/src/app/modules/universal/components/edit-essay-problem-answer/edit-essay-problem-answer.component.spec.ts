import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditEssayProblemAnswerComponent } from './edit-essay-problem-answer.component';

describe('EditEssayProblemComponent', () => {
  let component: EditEssayProblemAnswerComponent;
  let fixture: ComponentFixture<EditEssayProblemAnswerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditEssayProblemAnswerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditEssayProblemAnswerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
