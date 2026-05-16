import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StickersTotal } from './stickers-total';

describe('StickersTotal', () => {
  let component: StickersTotal;
  let fixture: ComponentFixture<StickersTotal>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StickersTotal],
    }).compileComponents();

    fixture = TestBed.createComponent(StickersTotal);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
