import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddEssayProblemComponent } from './add-essay-problem.component';

describe('AddEssayProblemComponent', () => {
  let component: AddEssayProblemComponent;
  let fixture: ComponentFixture<AddEssayProblemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddEssayProblemComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddEssayProblemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
