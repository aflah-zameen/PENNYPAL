import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SpendSummaryCardComponent } from './spend-summary-card.component';

describe('SpendSummaryCardComponent', () => {
  let component: SpendSummaryCardComponent;
  let fixture: ComponentFixture<SpendSummaryCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SpendSummaryCardComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SpendSummaryCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
