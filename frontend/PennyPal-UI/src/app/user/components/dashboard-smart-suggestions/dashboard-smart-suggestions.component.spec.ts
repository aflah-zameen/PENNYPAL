import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardSmartSuggestionsComponent } from './dashboard-smart-suggestions.component';

describe('DashboardSmartSuggestionsComponent', () => {
  let component: DashboardSmartSuggestionsComponent;
  let fixture: ComponentFixture<DashboardSmartSuggestionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DashboardSmartSuggestionsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashboardSmartSuggestionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
