import {Component, OnInit} from '@angular/core';
import {Book} from "../shared/book";
import {BookService} from "../../core/book.service";

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrl: './book-list.component.css'
})
export class BookListComponent implements OnInit {

  books!: Book[];

  constructor(private bookService: BookService) {
  }

  ngOnInit() {
    this.bookService.findAll().subscribe(data => {
      this.books = data;
    });
}
}
