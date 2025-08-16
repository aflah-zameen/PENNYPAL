import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardSavingGoalsComponent } from './dashboard-saving-goals.component';

describe('DashboardSavingGoalsComponent', () => {
  let component: DashboardSavingGoalsComponent;
  let fixture: ComponentFixture<DashboardSavingGoalsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DashboardSavingGoalsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashboardSavingGoalsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
