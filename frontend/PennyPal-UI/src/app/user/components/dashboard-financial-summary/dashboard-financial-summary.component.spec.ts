import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardFinancialSummaryComponent } from './dashboard-financial-summary.component';

describe('DashboardFinancialSummaryComponent', () => {
  let component: DashboardFinancialSummaryComponent;
  let fixture: ComponentFixture<DashboardFinancialSummaryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DashboardFinancialSummaryComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashboardFinancialSummaryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
