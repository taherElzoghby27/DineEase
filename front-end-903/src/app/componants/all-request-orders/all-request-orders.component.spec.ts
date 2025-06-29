import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllRequestOrdersComponent } from './all-request-orders.component';

describe('AllRequestOrdersComponent', () => {
  let component: AllRequestOrdersComponent;
  let fixture: ComponentFixture<AllRequestOrdersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AllRequestOrdersComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AllRequestOrdersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
