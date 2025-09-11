import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FileCaseModalTsComponent } from './file-case-modal.ts.component';

describe('FileCaseModalTsComponent', () => {
  let component: FileCaseModalTsComponent;
  let fixture: ComponentFixture<FileCaseModalTsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FileCaseModalTsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FileCaseModalTsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
