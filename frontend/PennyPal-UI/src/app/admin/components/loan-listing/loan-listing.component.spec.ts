import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoanListingComponent } from './loan-listing.component';

describe('LoanListingComponent', () => {
  let component: LoanListingComponent;
  let fixture: ComponentFixture<LoanListingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LoanListingComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LoanListingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
