import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FlaggedTransactionComponent } from './flagged-transaction.component';

describe('FlaggedTransactionComponent', () => {
  let component: FlaggedTransactionComponent;
  let fixture: ComponentFixture<FlaggedTransactionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FlaggedTransactionComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FlaggedTransactionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
