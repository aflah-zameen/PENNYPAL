import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminRejectionModalComponent } from './admin-rejection-modal.component';

describe('AdminRejectionModalComponent', () => {
  let component: AdminRejectionModalComponent;
  let fixture: ComponentFixture<AdminRejectionModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminRejectionModalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminRejectionModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
