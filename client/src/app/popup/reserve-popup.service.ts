import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import {BookReserveComponent} from "../books/book-reserve/book-reserve.component";

@Injectable({
  providedIn: 'root'
})
export class ReservePopupService {

  constructor(private dialog: MatDialog) { }

  openPopup() {
    this.dialog.open(BookReserveComponent);
  }

  closePopup() {
    this.dialog.closeAll();
  }
}
