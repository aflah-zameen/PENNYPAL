import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminLendingManagementComponent } from './admin-lending-management.component';

describe('AdminLendingManagementComponent', () => {
  let component: AdminLendingManagementComponent;
  let fixture: ComponentFixture<AdminLendingManagementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminLendingManagementComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminLendingManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
