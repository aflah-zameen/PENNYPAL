import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PendingIncomesComponent } from './pending-incomes.component';

describe('PendingIncomesComponent', () => {
  let component: PendingIncomesComponent;
  let fixture: ComponentFixture<PendingIncomesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PendingIncomesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PendingIncomesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
