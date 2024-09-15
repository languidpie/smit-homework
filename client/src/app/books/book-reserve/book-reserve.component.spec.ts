import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookReserveComponent } from './book-reserve.component';

describe('BookReserveComponent', () => {
  let component: BookReserveComponent;
  let fixture: ComponentFixture<BookReserveComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BookReserveComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BookReserveComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
