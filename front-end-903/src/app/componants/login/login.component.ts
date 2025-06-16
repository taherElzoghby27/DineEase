import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor() {
  }

  loginData = {
    username: '',
    password: ''
  };

  showPassword = false;

  ngOnInit(): void {
  }

  onSubmit(): void {
    console.log('Login attempt', this.loginData);
    // TODO: add authentication logic
  }
}
