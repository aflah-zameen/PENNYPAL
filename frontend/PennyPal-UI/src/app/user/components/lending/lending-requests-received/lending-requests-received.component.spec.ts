import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LendingRequestsReceivedComponent } from './lending-requests-received.component';

describe('LendingRequestsReceivedComponent', () => {
  let component: LendingRequestsReceivedComponent;
  let fixture: ComponentFixture<LendingRequestsReceivedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LendingRequestsReceivedComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LendingRequestsReceivedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
