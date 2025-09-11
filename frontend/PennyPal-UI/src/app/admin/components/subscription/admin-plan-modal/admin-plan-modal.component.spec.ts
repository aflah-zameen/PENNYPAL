import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminPlanModalComponent } from './admin-plan-modal.component';

describe('AdminPlanModalComponent', () => {
  let component: AdminPlanModalComponent;
  let fixture: ComponentFixture<AdminPlanModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminPlanModalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminPlanModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
