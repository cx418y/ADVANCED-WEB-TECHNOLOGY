import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TaGradeProblemComponent } from './ta-grade-problem.component';

describe('TaGradeProblemComponent', () => {
  let component: TaGradeProblemComponent;
  let fixture: ComponentFixture<TaGradeProblemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TaGradeProblemComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TaGradeProblemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
