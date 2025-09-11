import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubscriptionBreakdownChartComponent } from './subscription-breakdown-chart.component';

describe('SubscriptionBreakdownChartComponent', () => {
  let component: SubscriptionBreakdownChartComponent;
  let fixture: ComponentFixture<SubscriptionBreakdownChartComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SubscriptionBreakdownChartComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SubscriptionBreakdownChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
