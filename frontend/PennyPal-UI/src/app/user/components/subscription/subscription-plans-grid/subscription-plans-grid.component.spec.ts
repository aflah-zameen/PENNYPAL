import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubscriptionPlansGridComponent } from './subscription-plans-grid.component';

describe('SubscriptionPlansGridComponent', () => {
  let component: SubscriptionPlansGridComponent;
  let fixture: ComponentFixture<SubscriptionPlansGridComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SubscriptionPlansGridComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SubscriptionPlansGridComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
