import {Component, OnInit} from '@angular/core';
import {AuthService} from "./auth.service";
import {NavigationEnd, Router} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  authenticated: boolean;
  userName: string;
  title: string;

  constructor(private authService: AuthService,
              private router: Router) {
    this.authenticated = authService.isAuthenticated();
    this.userName = authService.getUserName();
    this.title = 'Book Loan Management';
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

}
