import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TournamentStatistics } from './tournament-statistics';

describe('TournamentStatistics', () => {
  let component: TournamentStatistics;
  let fixture: ComponentFixture<TournamentStatistics>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TournamentStatistics],
    }).compileComponents();

    fixture = TestBed.createComponent(TournamentStatistics);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
