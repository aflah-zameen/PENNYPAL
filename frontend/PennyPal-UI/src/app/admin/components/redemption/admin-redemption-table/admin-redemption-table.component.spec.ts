import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminRedemptionTableComponent } from './admin-redemption-table.component';

describe('AdminRedemptionTableComponent', () => {
  let component: AdminRedemptionTableComponent;
  let fixture: ComponentFixture<AdminRedemptionTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminRedemptionTableComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminRedemptionTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
