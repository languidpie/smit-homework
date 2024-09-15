import { Component } from '@angular/core';
import {Book} from "../book";
import {ActivatedRoute, Router} from "@angular/router";
import {BookService} from "../book.service";

@Component({
  selector: 'app-book-form',
  templateUrl: './book-form.component.html',
  styleUrl: './book-form.component.css'
})
export class BookFormComponent {
  book: Book;

  genres: string[] = [
    "Action",
    "Adventure",
    "Animated",
    "Biography",
    "Comedy",
    "Crime",
    "Dance",
    "Disaster",
    "Documentary",
    "Drama",
    "Erotic",
    "Family",
    "Fantasy",
    "Found Footage",
    "Historical",
    "Horror",
    "Independent",
    "Legal",
    "Live Action",
    "Martial Arts",
    "Musical",
    "Mystery",
    "Noir",
    "Performance",
    "Political",
    "Romance",
    "Satire",
    "Science Fiction",
    "Short",
    "Silent",
    "Slasher",
    "Sports",
    "Spy",
    "Superhero",
    "Supernatural",
    "Suspense",
    "Teen",
    "Thriller",
    "War",
    "Western"
  ];


  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private bookService: BookService) {
    this.book = new Book();
  }

  onSubmit() {
    this.bookService.save(this.book).subscribe(result => this.gotoBookList());
  }

  gotoBookList() {
    this.router.navigate(['/books']);
  }
}
