import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Book} from "../books/shared/book";
import {SortDirection} from "@angular/material/sort";

//TODO: refactor
@Injectable({
  providedIn: 'root'
})
export class BookService {

  private booksUrl: string;

  constructor(private http: HttpClient) {
    this.booksUrl = 'http://localhost:8080/api/books';
  }

  private createAuthHeaders(): HttpHeaders {
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Basic ' + localStorage.getItem('authToken')
    });
  }

  public findAll(sort: string, order: SortDirection, page: number): Observable<Book[]> {
    const headers = this.createAuthHeaders();
    return this.http.get<Book[]>(this.booksUrl + '?sort='+ sort +'&order='+ order +'&page=' + page, { headers });
  }

  public save(book: Book) {
    const headers = this.createAuthHeaders();
    return this.http.post<Book>(this.booksUrl, book, { headers });
  }

  public editBook(book: Book) {
    const headers = this.createAuthHeaders();
    return this.http.put<Book>(this.booksUrl + '/' + book.id + '/edit', book, { headers });
  }

  public reserve(book: Book) {
    const headers = this.createAuthHeaders();
    return this.http.put<Book>(this.booksUrl + '/' + book.id + '/reserve', book, { headers });
  }

  public loan(book: Book) {
    const headers = this.createAuthHeaders();
    return this.http.put<Book>(this.booksUrl + '/' + book.id + '/loan', book, { headers });
  }

  public delete(book: Book) {
    const headers = this.createAuthHeaders();
    return this.http.delete<Book>(this.booksUrl + '/' + book.id, { headers });
  }

  public returnBook(book: Book) {
    const headers = this.createAuthHeaders();
    return this.http.put<Book>(this.booksUrl + '/' + book.id + '/return', book, { headers });
  }

  public received(book: Book) {
    const headers = this.createAuthHeaders();
    return this.http.put<Book>(this.booksUrl + '/' + book.id + '/received', book, { headers });
  }
}
