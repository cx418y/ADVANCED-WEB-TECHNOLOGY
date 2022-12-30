import { TestBed } from '@angular/core/testing';

import { DoProblemService } from './do-problem.service';

describe('DoProblemServiceService', () => {
  let service: DoProblemService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DoProblemService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
