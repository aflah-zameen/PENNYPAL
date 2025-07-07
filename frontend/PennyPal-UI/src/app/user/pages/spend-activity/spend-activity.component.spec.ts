import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SpendActivityComponent } from './spend-activity.component';

describe('SpendActivityComponent', () => {
  let component: SpendActivityComponent;
  let fixture: ComponentFixture<SpendActivityComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SpendActivityComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SpendActivityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
