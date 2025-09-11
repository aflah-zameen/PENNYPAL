import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SendLentMoneyComponent } from './send-lent-money.component';

describe('SendLentMoneyComponent', () => {
  let component: SendLentMoneyComponent;
  let fixture: ComponentFixture<SendLentMoneyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SendLentMoneyComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SendLentMoneyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
