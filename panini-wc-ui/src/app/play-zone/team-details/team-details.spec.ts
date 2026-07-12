import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TeamDetails } from './team-details';

describe('TeamDetails', () => {
  let component: TeamDetails;
  let fixture: ComponentFixture<TeamDetails>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TeamDetails],
    }).compileComponents();

    fixture = TestBed.createComponent(TeamDetails);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
