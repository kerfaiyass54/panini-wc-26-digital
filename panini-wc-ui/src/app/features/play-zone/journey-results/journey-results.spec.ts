import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JourneyResults } from './journey-results';

describe('JourneyResults', () => {
  let component: JourneyResults;
  let fixture: ComponentFixture<JourneyResults>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [JourneyResults],
    }).compileComponents();

    fixture = TestBed.createComponent(JourneyResults);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
