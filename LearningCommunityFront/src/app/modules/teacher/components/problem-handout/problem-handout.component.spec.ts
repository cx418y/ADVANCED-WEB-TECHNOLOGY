import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProblemHandoutComponent } from './problem-handout.component';

describe('ProblemHandoutComponent', () => {
  let component: ProblemHandoutComponent;
  let fixture: ComponentFixture<ProblemHandoutComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProblemHandoutComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProblemHandoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
