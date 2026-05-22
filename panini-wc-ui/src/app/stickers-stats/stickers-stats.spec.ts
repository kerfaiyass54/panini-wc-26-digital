import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StickersStats } from './stickers-stats';

describe('StickersStats', () => {
  let component: StickersStats;
  let fixture: ComponentFixture<StickersStats>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StickersStats],
    }).compileComponents();

    fixture = TestBed.createComponent(StickersStats);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
