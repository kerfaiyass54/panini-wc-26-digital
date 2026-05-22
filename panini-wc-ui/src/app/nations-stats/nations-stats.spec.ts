import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NationsStats } from './nations-stats';

describe('NationsStats', () => {
  let component: NationsStats;
  let fixture: ComponentFixture<NationsStats>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NationsStats],
    }).compileComponents();

    fixture = TestBed.createComponent(NationsStats);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
