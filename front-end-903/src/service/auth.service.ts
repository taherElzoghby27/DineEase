import {Injectable} from '@angular/core';
import {RequestSignupModel} from '../model/request-signup-model';
import {HttpClient} from '@angular/common/http';
import {map} from 'rxjs/operators';
import {Observable} from 'rxjs';
import {RequestLoginModel} from '../model/request-login-model';
import {Profile} from '../model/profile';
import {RequestChangePassword} from '../model/request-change-password';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  baseUrl = 'http://localhost:6060/auth/';

  constructor(private http: HttpClient) {
  }

  isLogin(): boolean {
    return sessionStorage.getItem('token') !== null && sessionStorage.getItem('token') !== undefined;
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

  changePassword(requestChangePassword: RequestChangePassword): any {
    return this.http.put<any>(`${this.baseUrl}change-password`, requestChangePassword).pipe(
      map(response => response)
    );
  }

  getProfile(): Observable<Profile> {
    return this.http.get<Profile>(`${this.baseUrl}profile`).pipe(
      map(response => response)
    );
  }

  updateProfile(profile: Profile): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}update-profile`, profile).pipe(
      map(response => response)
    );
  }
}
