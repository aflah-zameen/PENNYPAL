import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddGoalButtonComponent } from './add-goal-button.component';

describe('AddGoalButtonComponent', () => {
  let component: AddGoalButtonComponent;
  let fixture: ComponentFixture<AddGoalButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddGoalButtonComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddGoalButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
