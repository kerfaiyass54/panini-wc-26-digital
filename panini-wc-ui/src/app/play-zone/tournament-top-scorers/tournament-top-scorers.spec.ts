import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TournamentTopScorers } from './tournament-top-scorers';

describe('TournamentTopScorers', () => {
  let component: TournamentTopScorers;
  let fixture: ComponentFixture<TournamentTopScorers>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TournamentTopScorers],
    }).compileComponents();

    fixture = TestBed.createComponent(TournamentTopScorers);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
