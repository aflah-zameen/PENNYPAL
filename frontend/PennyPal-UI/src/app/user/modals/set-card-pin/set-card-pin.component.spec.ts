import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SetCardPinComponent } from './set-card-pin.component';

describe('SetCardPinComponent', () => {
  let component: SetCardPinComponent;
  let fixture: ComponentFixture<SetCardPinComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SetCardPinComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SetCardPinComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
