import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecurringIncomeComponent } from './recurring-income.component';

describe('RecurringIncomeComponent', () => {
  let component: RecurringIncomeComponent;
  let fixture: ComponentFixture<RecurringIncomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RecurringIncomeComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RecurringIncomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
