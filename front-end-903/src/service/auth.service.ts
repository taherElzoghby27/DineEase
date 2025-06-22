import {Injectable} from '@angular/core';
import {RequestSignupModel} from '../model/request-signup-model';
import {HttpClient} from '@angular/common/http';
import {map} from 'rxjs/operators';
import {Observable} from 'rxjs';
import {RequestLoginModel} from '../model/request-login-model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  baseUrl = 'http://localhost:6060/auth/';

  constructor(private http: HttpClient) {
  }

  isLogin(): boolean {
    return sessionStorage.getItem('token') !== null || sessionStorage.getItem('token') !== undefined;
  }

  isAdmin(): boolean {
    const roles = sessionStorage.getItem('roles');
    return roles && roles.includes('ADMIN');
  }

  logout(): void {
    sessionStorage.removeItem('token');
  }

  signUp(requestSignUpModel: RequestSignupModel): Observable<any> {
    const username = requestSignUpModel.username;
    const password = requestSignUpModel.password;
    return this.http.post<any>(`${this.baseUrl}sign-up`, {username, password}).pipe(
      map(response => {
        console.log(`ss  ${response}`);
        return response;
      })
    );
  }

  login(requestLoginModel: RequestLoginModel): Observable<any> {
    const username = requestLoginModel.username;
    const password = requestLoginModel.password;
    return this.http.post<any>(`${this.baseUrl}login`, {username, password}).pipe(
      map(response => response)
    );
  }
}
