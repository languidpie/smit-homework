import {AfterViewInit, ChangeDetectionStrategy, Component, inject, OnInit, ViewChild} from '@angular/core';
import {Book} from "../shared/book";
import {BookService} from "../../core/book.service";
import {BookReserveComponent} from "../book-reserve/book-reserve.component";
import {ReservePopupService} from "../../popup/reserve-popup.service";
import {MatDialog, MatDialogModule} from "@angular/material/dialog";
import {MatPaginator} from "@angular/material/paginator";
import {catchError, map, merge, startWith, switchMap, Observable, of as observableOf} from "rxjs";
import {MatSort, MatSortModule, SortDirection} from '@angular/material/sort';

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

  displayedColumns: string[] = ['id', 'title', 'author', 'publisher', 'isbn', 'year', 'genre', 'status', 'recipient', 'bookReturnAt', 'updatedAt', 'reserveColumn', 'loanColumn', 'editColumn', 'deleteColumn'];

  resultsLength = 0;
  isLoadingResults = true;
  isRateLimitReached = false;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort,{static:false}) sort!: MatSort;

  constructor(private bookService: BookService,
              private reservePopupService: ReservePopupService) {
  }

  openReservePopup(book: Book) {
    const dialogRef = this.dialog.open(BookReserveComponent, {
      data: {book: book},
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
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
