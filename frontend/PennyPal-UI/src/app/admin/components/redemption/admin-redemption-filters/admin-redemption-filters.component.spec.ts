import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminRedemptionFiltersComponent } from './admin-redemption-filters.component';

describe('AdminRedemptionFiltersComponent', () => {
  let component: AdminRedemptionFiltersComponent;
  let fixture: ComponentFixture<AdminRedemptionFiltersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminRedemptionFiltersComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminRedemptionFiltersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
