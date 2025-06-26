import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SideNavbarItemComponent } from './side-navbar-item.component';

describe('SideNavbarItemComponent', () => {
  let component: SideNavbarItemComponent;
  let fixture: ComponentFixture<SideNavbarItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SideNavbarItemComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SideNavbarItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
