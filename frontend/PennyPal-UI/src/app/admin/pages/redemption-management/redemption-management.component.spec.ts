import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RedemptionManagementComponent } from './redemption-management.component';

describe('RedemptionManagementComponent', () => {
  let component: RedemptionManagementComponent;
  let fixture: ComponentFixture<RedemptionManagementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RedemptionManagementComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RedemptionManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
