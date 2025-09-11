import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CoinBalanceCardComponent } from './coin-balance-card.component';

describe('CoinBalanceCardComponent', () => {
  let component: CoinBalanceCardComponent;
  let fixture: ComponentFixture<CoinBalanceCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CoinBalanceCardComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CoinBalanceCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
