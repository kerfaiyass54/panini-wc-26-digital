import { TestBed } from '@angular/core/testing';

import { UserRelations } from './user-relations';

describe('UserRelations', () => {
  let service: UserRelations;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UserRelations);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
