import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SendMoneyModalComponent } from './send-money-modal.component';

describe('SendMoneyModalComponent', () => {
  let component: SendMoneyModalComponent;
  let fixture: ComponentFixture<SendMoneyModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SendMoneyModalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SendMoneyModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
