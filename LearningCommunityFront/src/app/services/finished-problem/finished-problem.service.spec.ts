import { TestBed } from '@angular/core/testing';

import { FinishedProblemService } from './finished-problem.service';

describe('FinishedProblemService', () => {
  let service: FinishedProblemService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FinishedProblemService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
