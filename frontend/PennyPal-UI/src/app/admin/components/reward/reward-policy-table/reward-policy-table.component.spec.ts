import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RewardPolicyTableComponent } from './reward-policy-table.component';

describe('RewardPolicyTableComponent', () => {
  let component: RewardPolicyTableComponent;
  let fixture: ComponentFixture<RewardPolicyTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RewardPolicyTableComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RewardPolicyTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
