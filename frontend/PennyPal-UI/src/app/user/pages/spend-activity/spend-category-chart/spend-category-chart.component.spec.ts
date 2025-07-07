import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SpendCategoryChartComponent } from './spend-category-chart.component';

describe('SpendCategoryChartComponent', () => {
  let component: SpendCategoryChartComponent;
  let fixture: ComponentFixture<SpendCategoryChartComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SpendCategoryChartComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SpendCategoryChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
