import {Component} from '@angular/core';
import {AuthService} from '../../../service/auth.service';
import {Router} from '@angular/router';
import {RequestLoginModel} from "../../../model/request-login-model";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  constructor(private authService: AuthService, private router: Router) {
  }

  signInData = {
    username: '',
    password: ''
  };
  usernameError = '';
  passwordError = '';
  errorMessageAr = '';
  errorMessageEn = '';
  errorBackend = false;

  resetErrorFields(): void {
    this.usernameError = '';
    this.passwordError = '';
  }

  onSubmit(): void {
    if (!this.validation(this.signInData.username, this.signInData.password)) {
      return;
    }
    const requestLoginModel = new RequestLoginModel(
      this.signInData.username, this.signInData.password
    );
    this.authService.login(requestLoginModel).subscribe(
      response => {
        this.errorBackend = false;
        this.resetErrorFields();
        sessionStorage.setItem('roles', response.roles);
        sessionStorage.setItem('token', response.token);
        sessionStorage.setItem('userName', response.username);
        this.router.navigateByUrl('products');
      }, errors => {
        this.errorBackend = true;
        this.resetErrorFields();
        this.errorMessageAr = errors.error.bundleMessage.message_ar;
        this.errorMessageEn = errors.error.bundleMessage.message_en;
      });
  }

  validation(username: string, password: string): boolean {
    if (username === '') {
      this.errorBackend = false;
      this.usernameError = 'Username is required';
      return false;
    }
    if (password === '') {
      this.errorBackend = false;
      this.resetErrorFields();
      this.passwordError = 'Password is required';
      return false;
    }
    return true;
  }

  clear(): void {
    this.usernameError = '';
    this.passwordError = '';
    this.errorMessageAr = '';
    this.errorMessageEn = '';
    this.errorBackend = false;
  }
}
