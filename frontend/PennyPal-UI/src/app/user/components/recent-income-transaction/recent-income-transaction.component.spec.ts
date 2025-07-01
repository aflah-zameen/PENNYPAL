import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecentIncomeTransactionComponent } from './recent-income-transaction.component';

describe('RecentIncomeTransactionComponent', () => {
  let component: RecentIncomeTransactionComponent;
  let fixture: ComponentFixture<RecentIncomeTransactionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RecentIncomeTransactionComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RecentIncomeTransactionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
