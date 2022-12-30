import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DoProblemComponent } from './do-problem.component';

describe('DoProblemComponent', () => {
  let component: DoProblemComponent;
  let fixture: ComponentFixture<DoProblemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DoProblemComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DoProblemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
