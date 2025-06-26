import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SideNavbarHeaderComponent } from './side-navbar-header.component';

describe('SideNavbarHeaderComponent', () => {
  let component: SideNavbarHeaderComponent;
  let fixture: ComponentFixture<SideNavbarHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SideNavbarHeaderComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SideNavbarHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
