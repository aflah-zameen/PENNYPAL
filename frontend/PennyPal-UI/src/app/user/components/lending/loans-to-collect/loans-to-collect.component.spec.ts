import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoansToCollectComponent } from './loans-to-collect.component';

describe('LoansToCollectComponent', () => {
  let component: LoansToCollectComponent;
  let fixture: ComponentFixture<LoansToCollectComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LoansToCollectComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LoansToCollectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
