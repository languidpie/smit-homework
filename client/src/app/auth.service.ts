import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = 'http://localhost:8080/login'; // Adjust as needed

  constructor(private http: HttpClient) { }

  login(username: string, password: string): Observable<any> {
    return this.http.post<any>(this.apiUrl, { username, password }, { observe: 'response' });
  }

  logout() {/*
    return this.http.post(`${this.apiUrl}/logout`, {}).subscribe({
      next: () => {
        console.log('Logged out successfully')
        localStorage.clear();
      },
      error: err => console.error('Logout error', err)
    });*/
    localStorage.clear();
    window.location.reload();
  }

  isAuthenticated() {
    const authToken = localStorage.getItem('authToken');
    return authToken !== null && authToken !== undefined;
  }

  isRoleUser() {
    const userRole = localStorage.getItem('userRole');
    return userRole !== null && userRole !== undefined && userRole === 'ROLE_USER';
  }

  isRoleAdmin() {
    const userRole = localStorage.getItem('userRole');
    return userRole !== null && userRole !== undefined && userRole === 'ROLE_ADMIN';
  }

  getUserName() {
    return localStorage.getItem('userName') || '';
  }
}
