import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HistoryVersionDrawerComponent } from './history-version-drawer.component';

describe('HistoryVersionDrawerComponent', () => {
  let component: HistoryVersionDrawerComponent;
  let fixture: ComponentFixture<HistoryVersionDrawerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HistoryVersionDrawerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HistoryVersionDrawerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
