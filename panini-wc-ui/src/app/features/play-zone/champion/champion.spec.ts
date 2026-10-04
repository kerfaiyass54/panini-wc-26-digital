import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Champion } from './champion';

describe('Champion', () => {
  let component: Champion;
  let fixture: ComponentFixture<Champion>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Champion],
    }).compileComponents();

    fixture = TestBed.createComponent(Champion);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
