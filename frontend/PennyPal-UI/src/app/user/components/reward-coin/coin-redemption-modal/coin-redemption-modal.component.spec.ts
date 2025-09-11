import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CoinRedemptionModalComponent } from './coin-redemption-modal.component';

describe('CoinRedemptionModalComponent', () => {
  let component: CoinRedemptionModalComponent;
  let fixture: ComponentFixture<CoinRedemptionModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CoinRedemptionModalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CoinRedemptionModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
