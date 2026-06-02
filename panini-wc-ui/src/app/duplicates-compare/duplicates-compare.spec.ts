import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DuplicatesCompare } from './duplicates-compare';

describe('DuplicatesCompare', () => {
  let component: DuplicatesCompare;
  let fixture: ComponentFixture<DuplicatesCompare>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DuplicatesCompare],
    }).compileComponents();

    fixture = TestBed.createComponent(DuplicatesCompare);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
