import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FeatureCardSectionComponent } from './feature-card-section.component';

describe('FeatureCardSectionComponent', () => {
  let component: FeatureCardSectionComponent;
  let fixture: ComponentFixture<FeatureCardSectionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FeatureCardSectionComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FeatureCardSectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
