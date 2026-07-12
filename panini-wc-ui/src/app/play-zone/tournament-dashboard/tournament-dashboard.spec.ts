import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TournamentDashboard } from './tournament-dashboard';

describe('TournamentDashboard', () => {
  let component: TournamentDashboard;
  let fixture: ComponentFixture<TournamentDashboard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TournamentDashboard],
    }).compileComponents();

    fixture = TestBed.createComponent(TournamentDashboard);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
