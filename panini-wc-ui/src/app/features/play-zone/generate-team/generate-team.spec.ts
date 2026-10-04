import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GenerateTeam } from './generate-team';

describe('GenerateTeam', () => {
  let component: GenerateTeam;
  let fixture: ComponentFixture<GenerateTeam>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GenerateTeam],
    }).compileComponents();

    fixture = TestBed.createComponent(GenerateTeam);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
