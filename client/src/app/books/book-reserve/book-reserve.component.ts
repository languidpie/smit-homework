import {Component, inject, model} from '@angular/core';
import {Book} from "../shared/book";
import {ActivatedRoute, Router} from "@angular/router";
import {BookService} from "../../core/book.service";
import {PopupService} from "../../popup/popup.service";
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
              private popupService: PopupService) {
    this.book = this.bookData();
  }

  onReserve(book: Book) {
    this.bookService.reserve(book).subscribe(result => this.popupService.closePopup());
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
