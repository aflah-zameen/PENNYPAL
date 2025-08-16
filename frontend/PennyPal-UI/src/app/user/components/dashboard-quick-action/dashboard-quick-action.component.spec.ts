import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardQuickActionComponent } from './dashboard-quick-action.component';

describe('DashboardQuickActionComponent', () => {
  let component: DashboardQuickActionComponent;
  let fixture: ComponentFixture<DashboardQuickActionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DashboardQuickActionComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashboardQuickActionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
