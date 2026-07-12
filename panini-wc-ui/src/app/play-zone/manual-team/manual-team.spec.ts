import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManualTeam } from './manual-team';

describe('ManualTeam', () => {
  let component: ManualTeam;
  let fixture: ComponentFixture<ManualTeam>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ManualTeam],
    }).compileComponents();

    fixture = TestBed.createComponent(ManualTeam);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
