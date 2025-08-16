import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PinConfirmationModalComponent } from './pin-confirmation-modal.component';

describe('PinConfirmationModalComponent', () => {
  let component: PinConfirmationModalComponent;
  let fixture: ComponentFixture<PinConfirmationModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PinConfirmationModalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PinConfirmationModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
