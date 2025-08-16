import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardMessagePreviewComponent } from './dashboard-message-preview.component';

describe('DashboardMessagePreviewComponent', () => {
  let component: DashboardMessagePreviewComponent;
  let fixture: ComponentFixture<DashboardMessagePreviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DashboardMessagePreviewComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashboardMessagePreviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
