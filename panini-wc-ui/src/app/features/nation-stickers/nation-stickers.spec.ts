import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NationStickers } from './nation-stickers';

describe('NationStickers', () => {
  let component: NationStickers;
  let fixture: ComponentFixture<NationStickers>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NationStickers],
    }).compileComponents();

    fixture = TestBed.createComponent(NationStickers);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
