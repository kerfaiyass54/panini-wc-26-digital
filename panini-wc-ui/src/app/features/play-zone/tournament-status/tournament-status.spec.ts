import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TournamentStatus } from './tournament-status';

describe('TournamentStatus', () => {
  let component: TournamentStatus;
  let fixture: ComponentFixture<TournamentStatus>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TournamentStatus],
    }).compileComponents();

    fixture = TestBed.createComponent(TournamentStatus);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
