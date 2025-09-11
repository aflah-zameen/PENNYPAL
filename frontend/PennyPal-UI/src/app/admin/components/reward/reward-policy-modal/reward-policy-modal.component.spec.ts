import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RewardPolicyModalComponent } from './reward-policy-modal.component';

describe('RewardPolicyModalComponent', () => {
  let component: RewardPolicyModalComponent;
  let fixture: ComponentFixture<RewardPolicyModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RewardPolicyModalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RewardPolicyModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
