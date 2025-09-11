import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminRedemptionStatsComponent } from './admin-redemption-stats.component';

describe('AdminRedemptionStatsComponent', () => {
  let component: AdminRedemptionStatsComponent;
  let fixture: ComponentFixture<AdminRedemptionStatsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminRedemptionStatsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminRedemptionStatsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
