import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddRecurringExpenseModalComponent } from './add-recurring-expense-modal.component';

describe('AddRecurringExpenseModalComponent', () => {
  let component: AddRecurringExpenseModalComponent;
  let fixture: ComponentFixture<AddRecurringExpenseModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddRecurringExpenseModalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddRecurringExpenseModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
