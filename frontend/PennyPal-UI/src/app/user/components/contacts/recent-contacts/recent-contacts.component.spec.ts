import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecentContactsComponent } from './recent-contacts.component';

describe('RecentContactsComponent', () => {
  let component: RecentContactsComponent;
  let fixture: ComponentFixture<RecentContactsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RecentContactsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RecentContactsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
