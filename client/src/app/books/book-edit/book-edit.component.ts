import {Component, inject, model} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {BookService} from "../../core/book.service";
import {BookConstants} from "../shared/book.constants";
import {Book} from "../shared/book";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {DialogData} from "../book-list/book-list.component";
import {PopupService} from "../../popup/popup.service";

@Component({
  selector: 'app-book-edit',
  templateUrl: './book-edit.component.html',
  styleUrl: './book-edit.component.css'
})
export class BookEditComponent {
  readonly dialogRef = inject(MatDialogRef<BookEditComponent>);
  readonly data = inject<DialogData>(MAT_DIALOG_DATA);
  readonly bookData = model(this.data.book);

  book: Book;
  genres: string[];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private bookService: BookService,
    private constants: BookConstants,
    private popupService: PopupService) {
    this.book = this.bookData();
    this.genres = constants.GENRES;
  }

  onEdit(book: Book) {
    this.bookService.editBook(this.book).subscribe(result => this.popupService.closePopup());
  }
}
