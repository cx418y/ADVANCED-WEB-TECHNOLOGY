import { TestBed } from '@angular/core/testing';

import { CourseConfigService } from './course-config.service';

describe('CourseConfigService', () => {
  let service: CourseConfigService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CourseConfigService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
