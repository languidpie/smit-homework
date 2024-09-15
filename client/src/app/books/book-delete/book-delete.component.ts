import {Component, inject, model} from '@angular/core';
import {Book} from "../shared/book";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {DialogData} from "../book-list/book-list.component";
import {ActivatedRoute, Router} from "@angular/router";
import {BookService} from "../../core/book.service";
import {PopupService} from "../../popup/popup.service";

@Component({
  selector: 'app-book-delete',
  templateUrl: './book-delete.component.html',
  styleUrl: './book-delete.component.css'
})
export class BookDeleteComponent {
  readonly dialogRef = inject(MatDialogRef<BookDeleteComponent>);
  readonly data = inject<DialogData>(MAT_DIALOG_DATA);
  readonly bookData = model(this.data.book);
  book: Book = new Book();

  constructor(private route: ActivatedRoute,
              private router: Router,
              private bookService: BookService,
              private popupService: PopupService) {
    this.book = this.bookData();
  }

  onDelete(book: Book) {
    this.bookService.delete(book).subscribe(result => this.popupService.closePopup());
  }

}
