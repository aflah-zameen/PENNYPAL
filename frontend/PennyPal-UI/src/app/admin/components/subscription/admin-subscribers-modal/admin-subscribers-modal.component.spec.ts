import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminSubscribersModalComponent } from './admin-subscribers-modal.component';

describe('AdminSubscribersModalComponent', () => {
  let component: AdminSubscribersModalComponent;
  let fixture: ComponentFixture<AdminSubscribersModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminSubscribersModalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminSubscribersModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
