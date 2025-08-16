import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransactionStatusModalComponent } from './transaction-status-modal.component';

describe('TransactionStatusModalComponent', () => {
  let component: TransactionStatusModalComponent;
  let fixture: ComponentFixture<TransactionStatusModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TransactionStatusModalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TransactionStatusModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
