import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TournamentResults } from './tournament-results';

describe('TournamentResults', () => {
  let component: TournamentResults;
  let fixture: ComponentFixture<TournamentResults>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TournamentResults],
    }).compileComponents();

    fixture = TestBed.createComponent(TournamentResults);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
