import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddRecurringIncomeComponent } from './add-recurring-income.component';

describe('AddRecurringIncomeComponent', () => {
  let component: AddRecurringIncomeComponent;
  let fixture: ComponentFixture<AddRecurringIncomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddRecurringIncomeComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddRecurringIncomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
