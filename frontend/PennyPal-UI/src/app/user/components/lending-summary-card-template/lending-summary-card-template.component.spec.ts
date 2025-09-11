import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LendingSummaryCardTemplateComponent } from './lending-summary-card-template.component';

describe('LendingSummaryCardTemplateComponent', () => {
  let component: LendingSummaryCardTemplateComponent;
  let fixture: ComponentFixture<LendingSummaryCardTemplateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LendingSummaryCardTemplateComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LendingSummaryCardTemplateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
