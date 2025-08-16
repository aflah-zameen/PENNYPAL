import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardRecentTransactionsComponent } from './dashboard-recent-transactions.component';

describe('DashboardRecentTransactionsComponent', () => {
  let component: DashboardRecentTransactionsComponent;
  let fixture: ComponentFixture<DashboardRecentTransactionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DashboardRecentTransactionsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashboardRecentTransactionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
