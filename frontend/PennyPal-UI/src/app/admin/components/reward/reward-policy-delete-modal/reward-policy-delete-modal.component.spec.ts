import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RewardPolicyDeleteModalComponent } from './reward-policy-delete-modal.component';

describe('RewardPolicyDeleteModalComponent', () => {
  let component: RewardPolicyDeleteModalComponent;
  let fixture: ComponentFixture<RewardPolicyDeleteModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RewardPolicyDeleteModalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RewardPolicyDeleteModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
