import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {BookListComponent} from "./books/book-list/book-list.component";
import {BookFormComponent} from "./books/book-form/book-form.component";
import {BookReserveComponent} from "./books/book-reserve/book-reserve.component";

const routes: Routes = [
  { path: 'books', component: BookListComponent },
  { path: 'addbook', component: BookFormComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
