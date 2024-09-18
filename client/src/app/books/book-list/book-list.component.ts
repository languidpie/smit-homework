import {AfterViewInit, Component, inject, ViewChild} from '@angular/core';
import {Book} from "../shared/book";
import {BookService} from "../../core/book.service";
import {BookReserveComponent} from "../book-reserve/book-reserve.component";
import {MatDialog} from "@angular/material/dialog";
import {MatPaginator} from "@angular/material/paginator";
import {catchError, map, merge, startWith, switchMap, of as observableOf} from "rxjs";
import {MatSort} from '@angular/material/sort';
import {BookLoanComponent} from "../book-loan/book-loan.component";
import {BookDeleteComponent} from "../book-delete/book-delete.component";
import {BookEditComponent} from "../book-edit/book-edit.component";
import {AuthService} from "../../auth.service";

export interface DialogData {
  book: Book;
}

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrl: './book-list.component.css'
})
export class BookListComponent implements AfterViewInit {
  readonly dialog = inject(MatDialog);
  books: Book[] = [];
  filteredBooks: Book[] = [];

  displayedColumns: string[] = this.getDisplayedColumns();

  resultsLength = 0;
  isLoadingResults = true;
  isRateLimitReached = false;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort, {static: false}) sort!: MatSort;

  constructor(private bookService: BookService) {
  }

  getDisplayedColumns(): string[] {
    let columns = ['id', 'title', 'author', 'publisher', 'isbn', 'year', 'genre', 'status', 'recipient', 'bookReturnAt', 'updatedAt', 'returnColumn'];

    if (this.isRoleAdmin()) {
      columns.push('deleteColumn');
      columns.push('editColumn');
      columns.push('loanColumn');
    }

    if (this.isRoleUser()) {
      columns.push('reserveColumn');
      columns.push('receivedColumn');
    }

    return columns;
  }

  // TODO: figure out why authService is undefined and if fixed, use the methods in the service
  isRoleUser(): boolean {
    const userRole = localStorage.getItem('userRole');
    return userRole === 'ROLE_USER';
  }

  isRoleAdmin(): boolean {
    const userRole = localStorage.getItem('userRole');
    return userRole === 'ROLE_ADMIN';
  }

  openReservePopup(book: Book) {
    const dialogRef = this.dialog.open(BookReserveComponent, {
      data: {book: book},
    });

    dialogRef.afterClosed().subscribe(result => {
    });
  }

  openLoanPopup(book: Book) {
    const dialogRef = this.dialog.open(BookLoanComponent, {
      data: {book: book},
    });

    dialogRef.afterClosed().subscribe(result => {
    });
  }

  openEditPopup(book: Book) {
    const dialogRef = this.dialog.open(BookEditComponent, {
      data: {book: book},
    });

    dialogRef.afterClosed().subscribe(result => {
    });
  }

  openDeletePopup(book: Book) {
    const dialogRef = this.dialog.open(BookDeleteComponent, {
      data: {book: book},
    });

    dialogRef.afterClosed().subscribe(result => {
    });
  }

  returnBook(book: Book) {
    this.bookService.returnBook(book).subscribe(result => window.location.reload())
  }

  receivedBook(book: Book) {
    this.bookService.received(book).subscribe(result => window.location.reload())
  }

  // Method to get the CSS class based on status
  getStatusClass(status: string): string {
    switch (status) {
      case 'AVAILABLE':
        return 'status-available';
      case 'LOANED_OUT':
        return 'status-loaned';
      case 'RESERVED':
        return 'status-reserved';
      case 'RECEIVED':
        return 'status-received';
      default:
        return ''; // Default case (optional)
    }
  }

  ngAfterViewInit() {
    // If the user changes the sort order, reset back to the first page.
    this.sort.sortChange.subscribe(() => (this.paginator.pageIndex = 0));

    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        startWith({}),
        switchMap(() => {
          this.isLoadingResults = true;

          return this.bookService!.findAll(this.sort.active,
            this.sort.direction,
            this.paginator.pageIndex).pipe(catchError(() => observableOf(null)));
        }),
        map(data => {
          // Flip flag to show that loading has finished.
          this.isLoadingResults = false;
          this.isRateLimitReached = data === null;
          this.books = data || [];
          this.filteredBooks = data || []; // Initialize filteredBooks with all books

          if (data === null) {
            return [];
          }

          // Only refresh the result length if there is new data. In case of rate
          // limit errors, we do not want to reset the paginator to zero, as that
          // would prevent users from re-triggering requests.
          this.resultsLength = data.length;
          return data;
        }),
      )
      .subscribe(data => (this.books = data));
  }

  applyFilter(event: Event) {
    const input = event.target as HTMLInputElement;
    console.log(input.value)
    const query = input?.value.trim().toLowerCase() || ''; // Handle null or undefined value

    this.filteredBooks = this.books.filter(book => {
      return book.title.toLowerCase().includes(query) ||
        book.author.toLowerCase().includes(query) ||
        book.publisher?.toLowerCase().includes(query);
    });
  }
}
