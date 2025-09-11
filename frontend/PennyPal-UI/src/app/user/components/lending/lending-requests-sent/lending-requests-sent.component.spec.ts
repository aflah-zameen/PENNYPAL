import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LendingRequestsSentComponent } from './lending-requests-sent.component';

describe('LendingRequestsSentComponent', () => {
  let component: LendingRequestsSentComponent;
  let fixture: ComponentFixture<LendingRequestsSentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LendingRequestsSentComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LendingRequestsSentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
