import {Component} from '@angular/core';
import {AuthService} from '../../../service/auth.service';
import {RequestSignupModel} from '../../../model/request-signup-model';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent {

  constructor(private authService: AuthService) {
  }

  signupData = {
    username: '',
    password: ''
  };

  onSubmit(): void {
    console.log(`username  ${this.signupData.username} password  ${this.signupData.password}`);
    const requestSignupModel = new RequestSignupModel(
      this.signupData.username, this.signupData.password
    );
    this.authService.signUp(requestSignupModel).subscribe(
      response => {
        console.log(`success  ${response}`);
      }, errors => {
        console.log(`error  ${errors}`);
      });
  }
}
