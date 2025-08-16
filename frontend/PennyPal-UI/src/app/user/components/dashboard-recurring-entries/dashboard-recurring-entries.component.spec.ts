import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardRecurringEntriesComponent } from './dashboard-recurring-entries.component';

describe('DashboardRecurringEntriesComponent', () => {
  let component: DashboardRecurringEntriesComponent;
  let fixture: ComponentFixture<DashboardRecurringEntriesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DashboardRecurringEntriesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashboardRecurringEntriesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
