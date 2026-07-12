import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyTeam } from './my-team';

describe('MyTeam', () => {
  let component: MyTeam;
  let fixture: ComponentFixture<MyTeam>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MyTeam],
    }).compileComponents();

    fixture = TestBed.createComponent(MyTeam);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
