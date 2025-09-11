import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubscriptionPlansHeaderComponent } from './subscription-plans-header.component';

describe('SubscriptionPlansHeaderComponent', () => {
  let component: SubscriptionPlansHeaderComponent;
  let fixture: ComponentFixture<SubscriptionPlansHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SubscriptionPlansHeaderComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SubscriptionPlansHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
