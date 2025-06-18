import {Component} from '@angular/core';
import {AuthService} from '../../../service/auth.service';
import {RequestSignupModel} from '../../../model/request-signup-model';
import {Router} from '@angular/router';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent {

  constructor(private authService: AuthService, private router: Router) {
  }

  signupData = {
    username: '',
    password: '',
    confirmPassword: ''
  };
  usernameError = '';
  passwordError = '';
  confirmPasswordError = '';
  errorMessageAr = '';
  errorMessageEn = '';
  errorBackend = false;

  resetErrorFields(): void {
    this.usernameError = '';
    this.passwordError = '';
    this.confirmPasswordError = '';
  }

  onSubmit(): void {
    if (!this.validation(this.signupData.username, this.signupData.password, this.signupData.confirmPassword)) {
      return;
    }
    const requestSignupModel = new RequestSignupModel(
      this.signupData.username, this.signupData.password
    );
    this.authService.signUp(requestSignupModel).subscribe(
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

  validation(username: string, password: string, confirmPassword: string): boolean {
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
    if (confirmPassword === '') {
      this.errorBackend = false;
      this.resetErrorFields();
      this.confirmPasswordError = 'Confirm Password is required';
      return false;
    }
    if (confirmPassword !== password) {
      this.errorBackend = false;
      this.resetErrorFields();
      this.confirmPasswordError = 'Confirm Password not match password';
      return false;
    }
    return true;
  }

  clear(): void {
    this.usernameError = '';
    this.passwordError = '';
    this.confirmPasswordError = '';
    this.errorMessageAr = '';
    this.errorMessageEn = '';
    this.errorBackend = false;
  }
}
