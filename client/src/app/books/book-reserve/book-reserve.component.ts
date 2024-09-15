import {Component, inject, model} from '@angular/core';
import {Book} from "../shared/book";
import {ActivatedRoute, Router} from "@angular/router";
import {BookService} from "../../core/book.service";
import {ReservePopupService} from "../../popup/reserve-popup.service";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {DialogData} from "../book-list/book-list.component";
import {DatePipe} from "@angular/common";

@Component({
  selector: 'app-book-reserve',
  templateUrl: './book-reserve.component.html',
  styleUrl: './book-reserve.component.css',
})
export class BookReserveComponent {
  readonly dialogRef = inject(MatDialogRef<BookReserveComponent>);
  readonly data = inject<DialogData>(MAT_DIALOG_DATA);
  readonly bookData = model(this.data.book);
  book: Book = new Book();

  constructor(private route: ActivatedRoute,
              private router: Router,
              private bookService: BookService,
              private reservePopupService: ReservePopupService,
              private datePipe: DatePipe) {
    this.book = this.bookData();
  }

  onReserve(book: Book) {
    console.log('Reserving book:', book);
    book.bookReturnAt = this.datePipe.transform(this.book.bookReturnAt, 'yyyy-MM-ddTHH:mm:ss');
    this.bookService.reserve(book).subscribe(result => this.reservePopupService.closePopup());
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
