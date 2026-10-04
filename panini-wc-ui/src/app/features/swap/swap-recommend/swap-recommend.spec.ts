import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SwapRecommend } from './swap-recommend';

describe('SwapRecommend', () => {
  let component: SwapRecommend;
  let fixture: ComponentFixture<SwapRecommend>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SwapRecommend],
    }).compileComponents();

    fixture = TestBed.createComponent(SwapRecommend);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
