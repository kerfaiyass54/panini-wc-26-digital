import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DuplicatesManagement } from './duplicates-management';

describe('DuplicatesManagement', () => {
  let component: DuplicatesManagement;
  let fixture: ComponentFixture<DuplicatesManagement>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DuplicatesManagement],
    }).compileComponents();

    fixture = TestBed.createComponent(DuplicatesManagement);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
