import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CoinIconComponent } from './coin-icon.component';

describe('CoinIconComponent', () => {
  let component: CoinIconComponent;
  let fixture: ComponentFixture<CoinIconComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CoinIconComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CoinIconComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
