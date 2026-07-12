import { TestBed } from '@angular/core/testing';

import { Tournament } from './tournament';

describe('Tournament', () => {
  let service: Tournament;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Tournament);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
