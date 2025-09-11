import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubscriptionAnalyticsDashboardComponent } from './subscription-analytics-dashboard.component';

describe('SubscriptionAnalyticsDashboardComponent', () => {
  let component: SubscriptionAnalyticsDashboardComponent;
  let fixture: ComponentFixture<SubscriptionAnalyticsDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SubscriptionAnalyticsDashboardComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SubscriptionAnalyticsDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
