import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TAHomeComponent } from './ta-home.component';

describe('TAHomeComponent', () => {
  let component: TAHomeComponent;
  let fixture: ComponentFixture<TAHomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TAHomeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TAHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
