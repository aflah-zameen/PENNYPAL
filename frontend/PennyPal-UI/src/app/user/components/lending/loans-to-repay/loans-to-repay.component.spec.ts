import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoansToRepayComponent } from './loans-to-repay.component';

describe('LoansToRepayComponent', () => {
  let component: LoansToRepayComponent;
  let fixture: ComponentFixture<LoansToRepayComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LoansToRepayComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LoansToRepayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
