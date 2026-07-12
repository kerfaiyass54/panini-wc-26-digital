import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TournamentMatches } from './tournament-matches';

describe('TournamentMatches', () => {
  let component: TournamentMatches;
  let fixture: ComponentFixture<TournamentMatches>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TournamentMatches],
    }).compileComponents();

    fixture = TestBed.createComponent(TournamentMatches);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
