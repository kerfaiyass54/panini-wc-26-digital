import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AutoTeam } from './auto-team';

describe('AutoTeam', () => {
  let component: AutoTeam;
  let fixture: ComponentFixture<AutoTeam>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AutoTeam],
    }).compileComponents();

    fixture = TestBed.createComponent(AutoTeam);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
