import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminRedemptionPaginationComponent } from './admin-redemption-pagination.component';

describe('AdminRedemptionPaginationComponent', () => {
  let component: AdminRedemptionPaginationComponent;
  let fixture: ComponentFixture<AdminRedemptionPaginationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminRedemptionPaginationComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminRedemptionPaginationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
