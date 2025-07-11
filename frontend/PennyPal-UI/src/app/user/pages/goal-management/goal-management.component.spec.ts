import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GoalManagementComponent } from './goal-management.component';

describe('GoalManagementComponent', () => {
  let component: GoalManagementComponent;
  let fixture: ComponentFixture<GoalManagementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GoalManagementComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GoalManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
