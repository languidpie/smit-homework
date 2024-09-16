import {Component, OnInit} from '@angular/core';
import {AuthService} from "../auth.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  username: string = '';
  password: string = '';
  errorMessage: string = '';

  constructor(private route: ActivatedRoute,
              private router: Router,
              private authService: AuthService) {}

  login(): void {
    this.authService.login(this.username, this.password).subscribe({
      next: response => {
        // Handle successful login
        console.log('Login successful', response);

        localStorage.setItem('authToken', btoa(this.username + ':' + this.password));
        localStorage.setItem('userRole', response.body.authority);
        localStorage.setItem('userName', this.username);
        this.router.navigate(['/books']).then(() => {
          window.location.reload();
        });
      },
      error: err => {
        // Handle login error
        this.errorMessage = 'Login failed. Please check your credentials and try again.';
        console.error('Login error', err);
      }
    });
  }

  ngOnInit(): void {
    if (this.authService.isAuthenticated()) {
      // Redirect to dashboard if already authenticated
      this.router.navigate(['/books']);
    }
  }
}
