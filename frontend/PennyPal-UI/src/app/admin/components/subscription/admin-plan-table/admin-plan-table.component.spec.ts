import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminPlanTableComponent } from './admin-plan-table.component';

describe('AdminPlanTableComponent', () => {
  let component: AdminPlanTableComponent;
  let fixture: ComponentFixture<AdminPlanTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminPlanTableComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminPlanTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
