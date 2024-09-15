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

  displayedColumns: string[] = ['id', 'title', 'author', 'publisher', 'isbn', 'year', 'genre', 'status', 'recipient', 'bookReturnAt', 'updatedAt', 'returnColumn', 'reserveColumn', 'loanColumn', 'editColumn', 'deleteColumn'];

  resultsLength = 0;
  isLoadingResults = true;
  isRateLimitReached = false;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort,{static:false}) sort!: MatSort;

  constructor(private bookService: BookService) {
  }

  openReservePopup(book: Book) {
    const dialogRef = this.dialog.open(BookReserveComponent, {
      data: {book: book},
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  openLoanPopup(book: Book) {
    const dialogRef = this.dialog.open(BookLoanComponent, {
      data: {book: book},
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  openEditPopup(book: Book) {
    const dialogRef = this.dialog.open(BookEditComponent, {
      data: {book: book},
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  openDeletePopup(book: Book) {
    const dialogRef = this.dialog.open(BookDeleteComponent, {
      data: {book: book},
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  returnBook(book: Book) {
    this.bookService.returnBook(book).subscribe(result => window.location.reload())
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
}
