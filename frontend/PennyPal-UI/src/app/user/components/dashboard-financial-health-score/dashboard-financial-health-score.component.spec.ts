import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardFinancialHealthScoreComponent } from './dashboard-financial-health-score.component';

describe('DashboardFinancialHealthScoreComponent', () => {
  let component: DashboardFinancialHealthScoreComponent;
  let fixture: ComponentFixture<DashboardFinancialHealthScoreComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DashboardFinancialHealthScoreComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashboardFinancialHealthScoreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
