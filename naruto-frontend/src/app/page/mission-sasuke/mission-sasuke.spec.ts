import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MissionSasuke } from './mission-sasuke';

describe('MissionSasuke', () => {
  let component: MissionSasuke;
  let fixture: ComponentFixture<MissionSasuke>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MissionSasuke],
    }).compileComponents();

    fixture = TestBed.createComponent(MissionSasuke);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
