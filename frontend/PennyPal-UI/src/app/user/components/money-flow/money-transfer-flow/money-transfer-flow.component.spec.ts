import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MoneyTransferFlowComponent } from './money-transfer-flow.component';

describe('MoneyTransferFlowComponent', () => {
  let component: MoneyTransferFlowComponent;
  let fixture: ComponentFixture<MoneyTransferFlowComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MoneyTransferFlowComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MoneyTransferFlowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
