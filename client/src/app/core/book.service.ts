import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Book} from "../books/shared/book";
import {SortDirection} from "@angular/material/sort";

@Injectable({
  providedIn: 'root'
})
export class BookService {

  private booksUrl: string;

  constructor(private http: HttpClient) {
    this.booksUrl = 'http://localhost:8080/api/books';
  }

  public findAll(sort: string, order: SortDirection, page: number): Observable<Book[]> {
    return this.http.get<Book[]>(this.booksUrl + '?sort='+ sort +'&order='+ order +'&page=' + page);
  }

  public save(book: Book) {
    return this.http.post<Book>(this.booksUrl, book);
  }

  public reserve(book: Book) {
    return this.http.put<Book>(this.booksUrl + '/' + book.id + '/reserve', book);
  }

  public loan(book: Book) {
    return this.http.put<Book>(this.booksUrl + '/' + book.id + '/loan', book);
  }

  public delete(book: Book) {
    return this.http.delete<Book>(this.booksUrl + '/' + book.id);
  }
}
