import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalOverlayComponent } from './modal-overlay.component';

describe('ModalOverlayComponent', () => {
  let component: ModalOverlayComponent;
  let fixture: ComponentFixture<ModalOverlayComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ModalOverlayComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModalOverlayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
