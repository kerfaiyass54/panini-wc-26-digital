import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlayZone } from './play-zone';

describe('PlayZone', () => {
  let component: PlayZone;
  let fixture: ComponentFixture<PlayZone>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PlayZone],
    }).compileComponents();

    fixture = TestBed.createComponent(PlayZone);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
