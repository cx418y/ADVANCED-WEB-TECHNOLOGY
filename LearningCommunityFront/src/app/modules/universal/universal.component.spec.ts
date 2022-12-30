import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UniversalComponent } from './universal.component';

describe('UniversalComponent', () => {
  let component: UniversalComponent;
  let fixture: ComponentFixture<UniversalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UniversalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UniversalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
