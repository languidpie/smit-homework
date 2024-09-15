import { Component } from '@angular/core';
import {Book} from "../shared/book";
import {ActivatedRoute, Router} from "@angular/router";
import {BookService} from "../../core/book.service";
import {BookConstants} from "../shared/book.constants";

@Component({
  selector: 'app-book-form',
  templateUrl: './book-form.component.html',
  styleUrl: './book-form.component.css'
})
export class BookFormComponent {
  book: Book;
  genres: string[];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private bookService: BookService,
    private constants: BookConstants) {
    this.book = new Book();
    this.genres = constants.GENRES;
  }

  onSubmit() {
    this.bookService.save(this.book).subscribe(result => this.gotoBookList());
  }

  gotoBookList() {
    this.router.navigate(['/books']);
  }
}
