import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HallOfFame } from './hall-of-fame';

describe('HallOfFame', () => {
  let component: HallOfFame;
  let fixture: ComponentFixture<HallOfFame>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HallOfFame],
    }).compileComponents();

    fixture = TestBed.createComponent(HallOfFame);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
