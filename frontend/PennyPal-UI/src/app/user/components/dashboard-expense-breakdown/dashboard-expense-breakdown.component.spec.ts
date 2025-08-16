import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardExpenseBreakdownComponent } from './dashboard-expense-breakdown.component';

describe('DashboardExpenseBreakdownComponent', () => {
  let component: DashboardExpenseBreakdownComponent;
  let fixture: ComponentFixture<DashboardExpenseBreakdownComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DashboardExpenseBreakdownComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashboardExpenseBreakdownComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
