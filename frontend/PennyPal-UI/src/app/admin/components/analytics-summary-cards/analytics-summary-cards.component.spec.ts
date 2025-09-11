import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnalyticsSummaryCardsComponent } from './analytics-summary-cards.component';

describe('AnalyticsSummaryCardsComponent', () => {
  let component: AnalyticsSummaryCardsComponent;
  let fixture: ComponentFixture<AnalyticsSummaryCardsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AnalyticsSummaryCardsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AnalyticsSummaryCardsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
