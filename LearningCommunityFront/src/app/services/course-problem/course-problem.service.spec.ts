import { TestBed } from '@angular/core/testing';

import { CourseProblemService } from './course-problem.service';

describe('CourseProblemService', () => {
  let service: CourseProblemService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CourseProblemService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
