import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CoinTransactionHistoryComponent } from './coin-transaction-history.component';

describe('CoinTransactionHistoryComponent', () => {
  let component: CoinTransactionHistoryComponent;
  let fixture: ComponentFixture<CoinTransactionHistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CoinTransactionHistoryComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CoinTransactionHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
