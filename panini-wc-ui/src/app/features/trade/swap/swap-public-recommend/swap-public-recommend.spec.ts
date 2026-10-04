import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SwapPublicRecommend } from './swap-public-recommend';

describe('SwapPublicRecommend', () => {
  let component: SwapPublicRecommend;
  let fixture: ComponentFixture<SwapPublicRecommend>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SwapPublicRecommend],
    }).compileComponents();

    fixture = TestBed.createComponent(SwapPublicRecommend);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
