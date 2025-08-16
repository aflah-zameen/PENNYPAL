import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GoalFiltersComponent } from './goal-filters.component';

describe('GoalFiltersComponent', () => {
  let component: GoalFiltersComponent;
  let fixture: ComponentFixture<GoalFiltersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GoalFiltersComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GoalFiltersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
