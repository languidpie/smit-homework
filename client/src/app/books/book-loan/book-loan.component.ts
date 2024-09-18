import {Component, inject, model} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {DialogData} from "../book-list/book-list.component";
import {Book} from "../shared/book";
import {ActivatedRoute, Router} from "@angular/router";
import {BookService} from "../../core/book.service";
import {PopupService} from "../../popup/popup.service";
import {DatePipe} from "@angular/common";
import moment from "moment";

@Component({
  selector: 'app-book-loan',
  templateUrl: './book-loan.component.html',
  styleUrl: './book-loan.component.css'
})
export class BookLoanComponent {
  readonly dialogRef = inject(MatDialogRef<BookLoanComponent>);
  readonly data = inject<DialogData>(MAT_DIALOG_DATA);
  readonly bookData = model(this.data.book);
  book: Book = new Book();

  constructor(private route: ActivatedRoute,
              private router: Router,
              private bookService: BookService,
              private reservePopupService: PopupService,
              private datePipe: DatePipe) {
    this.book = this.bookData();
  }

  onLoan(book: Book) {
    if (this.isReturnDateInvalid()) {
      return;
    }
    book.bookReturnAt = this.datePipe.transform(this.book.bookReturnAt, 'yyyy-MM-ddTHH:mm:ss');
    this.bookService.loan(book).subscribe(result => {
      this.reservePopupService.closePopup()
    });
  }

  isReturnDateInvalid(): boolean {
    const today = moment();
    const maxDate = moment().add(4, 'weeks'); // 4 weeks from today

    const returnDate = moment(this.book.bookReturnAt);

    return returnDate.isBefore(today, 'day') || returnDate.isAfter(maxDate, 'day');
  }
}
