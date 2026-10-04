import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TournamentStandings } from './tournament-standings';

describe('TournamentStandings', () => {
  let component: TournamentStandings;
  let fixture: ComponentFixture<TournamentStandings>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TournamentStandings],
    }).compileComponents();

    fixture = TestBed.createComponent(TournamentStandings);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
