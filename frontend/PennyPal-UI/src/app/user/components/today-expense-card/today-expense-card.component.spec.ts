import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TodayExpenseCardComponent } from './today-expense-card.component';

describe('TodayExpenseCardComponent', () => {
  let component: TodayExpenseCardComponent;
  let fixture: ComponentFixture<TodayExpenseCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TodayExpenseCardComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TodayExpenseCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
