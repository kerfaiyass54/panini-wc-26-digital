import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlayJourney } from './play-journey';

describe('PlayJourney', () => {
  let component: PlayJourney;
  let fixture: ComponentFixture<PlayJourney>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PlayJourney],
    }).compileComponents();

    fixture = TestBed.createComponent(PlayJourney);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
