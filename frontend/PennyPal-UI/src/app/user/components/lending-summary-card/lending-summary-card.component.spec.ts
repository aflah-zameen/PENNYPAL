import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LendingSummaryCardComponent } from './lending-summary-card.component';

describe('LendingSummaryCardComponent', () => {
  let component: LendingSummaryCardComponent;
  let fixture: ComponentFixture<LendingSummaryCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LendingSummaryCardComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LendingSummaryCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
