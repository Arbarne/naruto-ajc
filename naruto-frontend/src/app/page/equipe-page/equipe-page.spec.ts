import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EquipePage } from './equipe-page';

describe('EquipePage', () => {
  let component: EquipePage;
  let fixture: ComponentFixture<EquipePage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EquipePage],
    }).compileComponents();

    fixture = TestBed.createComponent(EquipePage);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
