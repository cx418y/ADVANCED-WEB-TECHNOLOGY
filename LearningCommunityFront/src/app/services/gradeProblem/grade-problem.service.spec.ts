import { TestBed } from '@angular/core/testing';

import { GradeProblemService } from './grade-problem.service';

describe('GradeProblemService', () => {
  let service: GradeProblemService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GradeProblemService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
