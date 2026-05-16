import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StickersDetails } from './stickers-details';

describe('StickersDetails', () => {
  let component: StickersDetails;
  let fixture: ComponentFixture<StickersDetails>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StickersDetails],
    }).compileComponents();

    fixture = TestBed.createComponent(StickersDetails);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
