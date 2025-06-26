import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecentTransactionCardComponent } from './recent-transaction-card.component';

describe('RecentTransactionCardComponent', () => {
  let component: RecentTransactionCardComponent;
  let fixture: ComponentFixture<RecentTransactionCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RecentTransactionCardComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RecentTransactionCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
