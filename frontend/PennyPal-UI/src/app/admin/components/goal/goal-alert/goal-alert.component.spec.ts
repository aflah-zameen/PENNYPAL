import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GoalAlertComponent } from './goal-alert.component';

describe('GoalAlertComponent', () => {
  let component: GoalAlertComponent;
  let fixture: ComponentFixture<GoalAlertComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GoalAlertComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GoalAlertComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
