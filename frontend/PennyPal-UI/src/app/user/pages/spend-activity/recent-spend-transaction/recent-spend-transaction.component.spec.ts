import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecentSpendTransactionComponent } from './recent-spend-transaction.component';

describe('RecentSpendTransactionComponent', () => {
  let component: RecentSpendTransactionComponent;
  let fixture: ComponentFixture<RecentSpendTransactionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RecentSpendTransactionComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RecentSpendTransactionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
