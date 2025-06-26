import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OtpSectionComponent } from './otp-section.component';

describe('OtpSectionComponent', () => {
  let component: OtpSectionComponent;
  let fixture: ComponentFixture<OtpSectionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OtpSectionComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OtpSectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
